package io.github.imtotem.tracker.module;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

public class CalculateDistance {
    public static int[] getDistance(Player p1, Location loc2) {
        Location loc1 = p1.getLocation();

        int distance = 0;
        int height = 0;

        if ( loc1.getWorld() == loc2.getWorld() ) {

            double length = length(loc1.getBlockX() - loc2.getBlockX(), loc1.getBlockZ() - loc2.getBlockZ());

            distance = (int) Math.round(length);
            height = loc2.getBlockY() - loc1.getBlockY();
        }

        return new int[]{distance, height};
    }

    public static double length(double x, double z) {
        return Math.sqrt(NumberConversions.square(x) + NumberConversions.square(z));
    }
}
