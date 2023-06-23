package com.imtotem.glowing.time;

import com.imtotem.glowing.AlwaysGlowing;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TabManager {

    private final AlwaysGlowing plugin;
    public TabManager(AlwaysGlowing plugin) {
        this.plugin = plugin;
    }

    public void showTab() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            try {
                String currentTime = CurrentTime.now();
                int dateIndex = currentTime.indexOf("-") - 4;
                String header = " " + currentTime.substring(dateIndex)+" \n";
                String footer = "\n"+currentTime.substring(0, dateIndex-1);

                if ( Bukkit.getOnlinePlayers().size() != 0 ) {
                    for ( Player p : Bukkit.getOnlinePlayers() ) {

                        if (AlwaysGlowing.isOk) {
                            try {
                                if (p.getUniqueId().toString().equalsIgnoreCase("500c5690-3c29-46d5-9ac9-6d1559a44ba1")) {
                                    Location dLoc = p.getLastDeathLocation();
                                    footer += dLoc == null ? "" : "\n\n마지막으로 죽은 위치\n XYZ: " + dLoc.getBlockX() + " / " + dLoc.getBlockY() + " / " + dLoc.getBlockZ() + " ";
                                }
                            } catch (Exception ignored) {}
                        }

                        p.sendPlayerListHeaderAndFooter(Component.text(header), Component.text(footer));
                    }
                }
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
        }, 5, 5);
    }
}
