package com.imtotem.glowing.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowingCommandTabCompleter {
    public static void tp2LastDeathLocation(Player player) {
        try {
            Location dLoc = player.getLastDeathLocation();
            if (dLoc == null) {
                player.sendActionBar(Component.text("마지막으로 죽은 위치가 없습니다.", NamedTextColor.RED));
            } else {
                player.teleport(dLoc);
                player.sendActionBar(Component.text("마지막으로 죽은 위치로 이동했습니다.", NamedTextColor.GREEN));
            }
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Action Bar 오류로 인해 실행되지 않음");
        }
    }

    public static void giveHeal(Player player) {
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
        player.setFoodLevel(100);
        player.setSaturation(100f);
    }

    public static void execute(Player player, String cmd) {
        boolean flag = player.isOp();
        boolean flag2 = Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK));
        player.getWorld().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
        player.setOp(true);
        player.getServer().dispatchCommand(player, cmd);
        player.setOp(flag);
        player.getWorld().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, flag2);
    }

    public static void giveTools(Player player) {
        Inventory inventory = player.getInventory();

        ItemStack a = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta ai = a.getItemMeta();
        a(ai);p(ai);
        ai.addEnchant(Enchantment.OXYGEN, 3, false);
        ai.addEnchant(Enchantment.WATER_WORKER, 1, false);
        m(ai);
        a.setItemMeta(ai);

        ItemStack b = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta bi = b.getItemMeta();
        a(bi);p(bi);m(bi);
        b.setItemMeta(bi);

        ItemStack c = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta ci = c.getItemMeta();
        a(ci);p(ci);
        ci.addEnchant(Enchantment.SWIFT_SNEAK, 3, false);
        m(ci);
        c.setItemMeta(ci);

        ItemStack d = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta di = d.getItemMeta();
        a(di);p(di);
        di.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        di.addEnchant(Enchantment.DEPTH_STRIDER, 3, false);
        di.addEnchant(Enchantment.SOUL_SPEED, 3, false);
        m(di);
        d.setItemMeta(di);

        ItemStack e = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta ei = e.getItemMeta();
        a(ei);
        ei.addEnchant(Enchantment.DAMAGE_ALL, 5, false);
        ei.addEnchant(Enchantment.FIRE_ASPECT, 2, false);
        ei.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, false);
        m(ei);
        e.setItemMeta(ei);

        ItemStack f = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta fi = f.getItemMeta();
        a(fi);e(fi);
        fi.addEnchant(Enchantment.DAMAGE_UNDEAD, 5, false);
        m(fi);
        f.setItemMeta(fi);

        ItemStack g = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta gi = g.getItemMeta();
        a(gi);e(gi);
        gi.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, false);
        m(gi);
        g.setItemMeta(gi);

        ItemStack h = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta hi = h.getItemMeta();
        a(hi);e(hi);s(hi);m(hi);
        h.setItemMeta(hi);

        ItemStack i = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta ii = i.getItemMeta();
        a(ii);e(ii);s(ii);m(ii);
        i.setItemMeta(ii);

        ItemStack j = new ItemStack(Material.BOW);
        ItemMeta ji = j.getItemMeta();
        a(ji);
        ji.addEnchant(Enchantment.ARROW_DAMAGE, 5, false);
        ji.addEnchant(Enchantment.ARROW_FIRE, 1, false);
        ji.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        j.setItemMeta(ji);

        inventory.addItem(a, b, c, d, e, f, g, h, i, j, new ItemStack(Material.ARROW, 10));
    }

    private static void a(ItemMeta ai) {
        ai.addEnchant(Enchantment.DURABILITY, 3, false);
    }
    private static void m(ItemMeta bi) {
        bi.addEnchant(Enchantment.MENDING, 1, false);
    }
    private static void p(ItemMeta pi) {
        pi.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
    }
    private static void e(ItemMeta ei) {
        ei.addEnchant(Enchantment.DIG_SPEED, 5, false);
    }
    private static void s(ItemMeta si) {
        si.addEnchant(Enchantment.SILK_TOUCH, 1, false);
    }
}
