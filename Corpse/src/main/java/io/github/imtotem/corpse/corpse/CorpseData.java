package io.github.imtotem.corpse.corpse;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.Component.text;

public class CorpseData implements Cloneable, ConfigurationSerializable {
    private final UUID uuid;
    private final String name;
    private final String texturesValue;
    private final String texturesSignature;
    private final Location location;
    private ItemStack[] itemStacks;

    public CorpseData(UUID uuid, String name, String texturesValue, String texturesSignature, Location location, ItemStack... itemStacks) {
        this.uuid = uuid;
        this.name = name;
        this.texturesValue = texturesValue;
        this.texturesSignature = texturesSignature;
        this.location = location;
        this.itemStacks = itemStacks;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getTexturesValue() {
        return texturesValue;
    }

    public String getTexturesSignature() {
        return texturesSignature;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack[] getItems() {
        return itemStacks;
    }

    public void setItems(ItemStack... itemStack) {
        this.itemStacks = itemStack;
    }

    public static CorpseData fromConfig(String id, FileConfiguration config) {
        UUID uuid = UUID.fromString(Objects.requireNonNull(config.getString(id + ".uuid")));
        String name = config.getString(id+".name");
        String texturesValue = config.getString(id+".textures.value");
        String texturesSignature = config.getString(id+".textures.signature");
        Location location = config.getLocation(id+".location");
        List<ItemStack> itemList = new ArrayList<>();

        for ( Object obj : Objects.requireNonNull(config.getList(id + ".items"))) {
            itemList.add(obj instanceof ItemStack ? (ItemStack) obj : null);
        }
        ItemStack[] items = itemList.toArray(ItemStack[]::new);

        return new CorpseData(uuid, name, texturesValue, texturesSignature, location, items);
    }

    @Override
    public String toString() {
        return "CorpseData{"
                + "uuid:" + uuid
                + ",name:" + name
                + ",textures.value:" + texturesValue
                + ",textures.signature:" + texturesSignature
                + ",location:" + location
                + ",items:" + Arrays.toString(itemStacks)
                + " }";
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("uuid", this.uuid.toString());
        data.put("name", this.name);
        data.put("textures.value", this.texturesValue);
        data.put("textures.signature", this.texturesSignature);
        data.put("location", this.location);
        data.put("items", this.itemStacks);

        return data;
    }

    @Override
    @NotNull
    public CorpseData clone() {
        try {
            return (CorpseData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
}
