package org.mimja.waystones;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class TeleportWithOffset {
    private static final Random RANDOM = new Random();

    private static Location getRandomLocation(Location location) {
        int offsetX = 0;
        int offsetZ = 0;

        while (offsetX == 0 && offsetZ == 0) {
            offsetX = RANDOM.nextInt(2);
            offsetZ = RANDOM.nextInt(2);
        }

        return new Location(
                location.getWorld(),
                location.getX() + offsetX + 0.5f,
                location.getY(),
                location.getZ() + offsetZ + 0.5f
        );
    }

    public static void teleportWithRandomOffset(Player player, Location destination) {
        Location loc = getRandomLocation(destination);
        Location upLoc = loc.clone().add(0, 1, 0);

        int limit = 50;
        while (limit > 0 && (loc.getBlock().getType().isSolid() || upLoc.getBlock().getType().isSolid())) {
            loc = getRandomLocation(destination);
            upLoc = loc.clone().add(0, 1, 0);
            limit -= 1;
        }
        player.teleport(loc);
    }
}