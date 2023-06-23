package io.github.imtotem.corpse.corpse;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.imtotem.corpse.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.scoreboard.CraftScoreboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import static net.kyori.adventure.text.Component.text;

public class Corpse extends ServerPlayer {
    private final CorpseData corpseData;
    private final Inventory inventory;

    public Corpse(CorpseData corpseData, MinecraftServer minecraftServer, ServerLevel worldserver, GameProfile gameprofile) {
        super(minecraftServer, worldserver, gameprofile, null);

        this.corpseData = corpseData;
        this.inventory = Bukkit.createInventory(this.getInventory().getOwner(), 45, text(corpseData.getName()+"의 시체"));

        this.inventory.setContents(corpseData.getItems());

        Location loc = corpseData.getLocation().clone();
//        this.setPos(loc.getX(), loc.getY(), loc.getZ());
//        this.setXRot(loc.getPitch());
//        this.yBodyRotO = loc.getYaw();
//        this.setYBodyRot(loc.getYaw());
//        this.setYHeadRot(loc.getYaw());
//        this.setYRot(loc.getYaw());
        /*
        * spawn
        * this.getBukkitEntity().teleport(loc.clone())
        * setHeadYaw
        * setBodyYaw
        * check Living Entity -> cast to Living Entity -> yaw=Util.clamp(yaw) -> handle.yBodyRotO
        * handle.setYBodyRot(yaw)
        * handle.setYHeadRot(yaw)
        * */

    }

    public static Corpse create(CorpseData corpseData) {
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), corpseData.getName());
        gameProfile.getProperties().put("textures", new Property("textures", corpseData.getTexturesValue(), corpseData.getTexturesSignature()));

        return new Corpse(
                corpseData,
                ((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) corpseData.getLocation().getWorld()).getHandle(),
                gameProfile
        );
    }

    public static Corpse create(Player player) {
        ServerPlayer craftPlayer = ((CraftPlayer) player).getHandle();
        Property textures = craftPlayer.getGameProfile().getProperties().get("textures").iterator().next();

        return create(new CorpseData(player.getUniqueId(), player.getName(), textures.getValue(), textures.getSignature(), player.getLocation(), getItems(player)));
    }

    public static Corpse create(int id, CorpseData corpseData) {
        Corpse corpse = create(corpseData);
        corpse.setId(id);
        return corpse;
    }

    public void show(Player player) {
        // Remove NameTag
        PlayerTeam team = new PlayerTeam(((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(),
                corpseData.getName());
        team.setNameTagVisibility(Team.Visibility.NEVER);
        this.setCustomNameVisible(false);

        // Make Body Skin Packet
        // https://wiki.vg -> Player
        SynchedEntityData watcher = this.getEntityData();

        byte skinParts = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
        watcher.set(EntityDataSerializers.BYTE.createAccessor(17), skinParts);
        ClientboundSetEntityDataPacket bodyPacket = new ClientboundSetEntityDataPacket(this.getId(), watcher, true);

        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;

        // set Pose
        // TODO Pose.SLEEPING 일때 히트박스 작아지는거 수정
//        this.setPose(Pose.SLEEPING);

        // Set Position and Make Position Packet
        // loc.getY()+0.14
//        this.setPos(loc.getX(), loc.getY(), loc.getZ());
//        this.setRot(loc.getPitch(), loc.getYaw());
        Location loc = corpseData.getLocation();
        ((Entity)this.getBukkitEntity()).teleport(loc);
//        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.yBodyRotO = loc.getYaw();
        this.setYBodyRot(loc.getYaw());
        this.setYHeadRot(loc.getYaw());
        this.setYRot(loc.getYaw());
        this.setXRot(loc.getPitch());

        // TODO 얘가 대가리 돌려서 몸도 돌아가는듯
//        this.setSleepingPos(new BlockPos(loc.getX(), loc.getY(), loc.getZ()));
        ClientboundMoveEntityPacket positionPacket = new ClientboundMoveEntityPacket.PosRot(
                this.getId(),
                (short) loc.getX(),
                (short) loc.getY(),
                (short) loc.getZ(),
                (byte) loc.getYaw(),
                (byte) loc.getPitch(),
                true
        );

        connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, this));
        connection.send(new ClientboundAddPlayerPacket(this));
        connection.send(bodyPacket);
        connection.send(positionPacket);
//        connection.send(new ClientboundRotateHeadPacket(this, (byte) corpseData.getLocation().getYaw()));

        new BukkitRunnable() {
            @Override
            public void run() {
                connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, Corpse.this));
            }
        }.runTaskLaterAsynchronously(Main.getPlugin(Main.class), 20);
    }

    public void showAll() {
        Bukkit.getOnlinePlayers().forEach(this::show);
    }

    public void remove() {
        removePacket();
        CorpseList.remove(this);
    }

    public void removePacket() {
        for ( Player on : Bukkit.getOnlinePlayers() ) {
            ServerGamePacketListenerImpl p = ((CraftPlayer) on).getHandle().connection;
            p.send(new ClientboundRemoveEntitiesPacket(this.getId()));
        }
    }

    public static ItemStack[] getItems(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, text(""));
        inventory.setContents(player.getInventory().getContents());
        if ( player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING )
            for ( int i = 1; i < 5; i ++ )
                if ( player.getOpenInventory().getTopInventory().getContents()[i] != null )
                    inventory.setItem(40+i, player.getOpenInventory().getTopInventory().getContents()[i]);

        return inventory.getContents();
    }

    public Inventory getCorpseInventory() {
        return this.inventory;
    }

    public UUID whoseCorpse() {
        return corpseData.getUuid();
    }

    public CorpseData getCorpseData() {
        corpseData.setItems(inventory.getContents());
        return corpseData;
    }
}
