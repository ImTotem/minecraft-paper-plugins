package io.github.imtotem.corpse.corpse;

import io.github.imtotem.corpse.Main;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.List;

public class PacketReader {
    private final Player player;
    private int count = 0;

    public PacketReader(Player player) {
        this.player = player;
    }

    public void inject() {
        CraftPlayer nmsPlayer = (CraftPlayer) player;
        Channel channel = nmsPlayer.getHandle().connection.connection.channel;

        final String PACKET_NAME = "PacketInjector";
        if ( channel.pipeline().get(PACKET_NAME) != null ) {
            channel.pipeline().remove(PACKET_NAME);
        }

        channel.pipeline().addAfter("decoder", PACKET_NAME, new MessageToMessageDecoder<ServerboundInteractPacket>() {
            @Override
            protected void decode(ChannelHandlerContext ctx, ServerboundInteractPacket msg, List<Object> out) {
                out.add(msg);
                read(msg);
            }
        });
    }

    private void read(ServerboundInteractPacket packet) {
        count++;
        if ( count == 4 ) {
            count = 0;
            int entityId = (int) getValue(packet);

            if ( !CorpseList.containsKey(entityId) )
                return;

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getPluginManager().callEvent(new RightClickCorpse(player, CorpseList.get(entityId)));
                }
            }.runTask(Main.getPlugin(Main.class));
        }
    }

    private Object getValue(Object instance) {
        Object result = null;
        try {
            Field field = instance.getClass().getDeclaredField("a");
            field.setAccessible(true);
            result = field.get(instance);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
