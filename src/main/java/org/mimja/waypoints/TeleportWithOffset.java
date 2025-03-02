package org.mimja.waypoints;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class TeleportWithOffset {
    private static final Random RANDOM = new Random();

    public static void teleportWithRandomOffset(Player player, Location destination) {
        int offsetX = RANDOM.nextInt(3) - 1;
        int offsetZ = RANDOM.nextInt(3) - 1;

        while (offsetX == 0 && offsetZ == 0) {
            offsetX = RANDOM.nextInt(3) - 1;
            offsetZ = RANDOM.nextInt(3) - 1;
        }

        Location loc = new Location(
                destination.getWorld(),
                destination.getX() + offsetX + 0.5f,
                destination.getY(),
                destination.getZ() + offsetZ + 0.5f
        );
        player.teleport(loc);
    }
}