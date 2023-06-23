package io.github.imtotem.tracker.module;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CalculateDirection {

    static String N = "↑";
    static String NE = "⬉";
    static String E = "←";
    static String SE = "⬋";
    static String S = "↓";
    static String SW = "⬊";
    static String W = "→";
    static String NW = "⬈";

    public static double angle;

    public static boolean isAimingNorth() {
        return angle <= 22.5 || 337.5 < angle;
    }
    public static boolean isAimingNE() {
        return 22.5 < angle && angle <= 67.5;
    }
    public static boolean isAimingEast() {
        return 67.5 < angle && angle <= 112.5;
    }
    public static boolean isAimingSE() {
        return 112.5 < angle && angle <= 157.5;
    }
    public static boolean isAimingSouth() {
        return 157.5 < angle && angle <= 202.5;
    }
    public static boolean isAimingSW() {
        return 202.5 < angle && angle <= 247.5;
    }
    public static boolean isAimingWest() {
        return 247 < angle && angle <= 292.5;
    }
    public static boolean isAimingNW() {
        return 292.5 < angle && angle <= 337.5;
    }

    public static String getDirection(Player player, Location targetLoc) {
        String direction = "•";

        if ( player.getWorld() != targetLoc.getWorld() ) {
            return direction;
        }

        Vector a = (targetLoc).toVector().subtract(player.getLocation().toVector()).setY(0);
        Vector b = player.getEyeLocation().getDirection().setY(0);
        angle = a.angle(b);
        angle = angle * 180 / Math.PI;

        if ( moreThanHalf(a, b) ) {
            angle = 360 - angle;
        }

        if(isAimingNorth()) {
            direction = N;
        }else
        if(isAimingNE()) {
            direction = NE;
        }else
        if(isAimingEast()) {
            direction = E;
        }else
        if(isAimingSE()) {
            direction = SE;
        }else
        if(isAimingSouth()) {
            direction = S;
        }else
        if(isAimingSW()) {
            direction = SW;
        }else
        if(isAimingWest()) {
            direction = W;
        }else
        if(isAimingNW()) {
            direction = NW;
        }

        return direction;
    }

    public static String getDirection(Location player, Location targetLoc) {
        player.setX(player.getBlockX());
        player.setY(player.getBlockY());
        player.setZ(player.getBlockZ());
        targetLoc.setX(targetLoc.getBlockX());
        targetLoc.setY(targetLoc.getBlockY());
        targetLoc.setZ(targetLoc.getBlockZ());

        String direction = "•";

        if ( player.getWorld() != targetLoc.getWorld() ) {
            return direction;
        }

        Vector a = (targetLoc).toVector().subtract(player.toVector()).setY(0);
        Vector b = player.getDirection().setY(0);
        angle = a.angle(b);
        angle = angle * 180 / Math.PI;

        if ( moreThanHalf(a, b) ) {
            angle = 360 - angle;
        }

        if(isAimingNorth()) {
            direction = N;
        }else
        if(isAimingNE()) {
            direction = NE;
        }else
        if(isAimingEast()) {
            direction = E;
        }else
        if(isAimingSE()) {
            direction = SE;
        }else
        if(isAimingSouth()) {
            direction = S;
        }else
        if(isAimingSW()) {
            direction = SW;
        }else
        if(isAimingWest()) {
            direction = W;
        }else
        if(isAimingNW()) {
            direction = NW;
        }

        return direction;
    }

    private static boolean moreThanHalf(Vector a, Vector b) {
        Vector rot90vector = new Vector();
        rot90vector = rot90vector.setX(-a.getZ()).setY(0).setZ(a.getX());

        double ang = Math.acos( (rot90vector.dot(b)) / (rot90vector.length() * b.length() ) ) * 360 / ( 2 * Math.PI );

        return ang > 90;
    }
}
