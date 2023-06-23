package io.github.imtotem.corpse.corpse;

import io.github.imtotem.corpse.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;


public class CorpseList {
    private static final Map<Integer, Corpse> corpses = new HashMap<>();
    private static final Map<InventoryHolder, Integer> holders = new HashMap<>();
    private static final Plugin instance = Main.getPlugin(Main.class);

    public static void add(Corpse corpse) {
        corpses.put(corpse.getId(), corpse);
        holders.put(corpse.getCorpseInventory().getHolder(), corpse.getId());
        save(corpse);
    }

    public static void remove(Corpse corpse) {
        holders.remove(corpse.getCorpseInventory().getHolder());
        corpses.replace(corpse.getId(), null);
        corpses.remove(corpse.getId());
        instance.getConfig().set(String.valueOf(corpse.getId()), null);
        instance.saveConfig();
    }

    public static boolean containsKey(int id) {
        return corpses.containsKey(id);
    }

    public static Corpse get(int id) {
        return corpses.get(id);
    }

    public static Corpse get(InventoryHolder holder) {
        return corpses.get(holders.get(holder));
    }

    public static List<Corpse> getByName(String name) {
        return corpses.values().stream().filter(corpse -> corpse.getCorpseData().getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public static boolean contains(InventoryHolder holder) {
        return holders.containsKey(holder);
    }

    public static void load() {
        Map<String, Object> loaded = instance.getConfig().getValues(true);
        loaded.forEach((s, o) -> {
            String sid = s.indexOf('.') != -1 ? s.substring(0, s.indexOf('.')) : s;
            int id = Integer.parseInt(sid);
            Corpse corpse  = Corpse.create(id, CorpseData.fromConfig(sid, instance.getConfig()));
            corpses.put(id, corpse);
            holders.put(corpse.getCorpseInventory().getHolder(), id);
        });
    }

    public static void save() {
        corpses.values().forEach(CorpseList::save);
    }

    public static void save(Corpse corpse) {
        corpse.getCorpseData().serialize().forEach((key, value) -> instance.getConfig().set(corpse.getId()+"."+key, value));
        instance.saveConfig();
    }

    public static void loadCorpseTo(Player player) {
        corpses.values().forEach(corpse -> corpse.show(player));
    }

    public static void clear() {
        corpses.values().forEach(Corpse::removePacket);
    }

    public static List<String> getNameList() {
        Set<String> set = new HashSet<>();
        corpses.values().forEach(corpse -> set.add(corpse.getCorpseData().getName()));
        return set.stream().sorted().collect(Collectors.toList());
    }
}
