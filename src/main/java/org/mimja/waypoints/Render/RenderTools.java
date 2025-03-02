package org.mimja.waypoints.Render;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import org.mimja.waypoints.Storage.WaypointDataModel;

import java.util.Collection;

public class RenderTools {
    public static void renderWaypoint(WaypointDataModel waypointData) {
        World world = Bukkit.getWorld(waypointData.getWorld());
        Location loc = new Location(world, waypointData.getX(), waypointData.getY(), waypointData.getZ());

        assert world != null;
        Collection<Entity> entities = world.getNearbyEntities(loc, 1, 2, 1);
        for (Entity entity : entities) {
            if(entity instanceof BlockDisplay || entity instanceof AreaEffectCloud) {
                entity.remove();
            }
        }

        BlockDisplay pillarBase = (BlockDisplay) world.spawnEntity(loc, EntityType.BLOCK_DISPLAY);
        pillarBase.setBlock(Material.CHISELED_STONE_BRICKS.createBlockData());
        Transformation pillarBaseTransformation = new Transformation(
                new Vector3f(0, 0, 0), // translation
                new AxisAngle4f(), // left rotation
                new Vector3f(1.0f, 0.2f, 1.0f), // scale
                new AxisAngle4f() // right rotation
        );
        pillarBase.setTransformation(pillarBaseTransformation);

        BlockDisplay pillarTop = (BlockDisplay) world.spawnEntity(loc, EntityType.BLOCK_DISPLAY);
        pillarTop.setBlock(Material.CHISELED_STONE_BRICKS.createBlockData());
        Transformation pillarTopTransformation = new Transformation(
                new Vector3f(0, 1.8f, 0), // translation
                new AxisAngle4f(), // left rotation
                new Vector3f(1.0f, 0.2f, 1.0f), // scale
                new AxisAngle4f() // right rotation
        );
        pillarTop.setTransformation(pillarTopTransformation);

        float scale = 0.3f;
        BlockDisplay pillarCrystal = (BlockDisplay) world.spawnEntity(loc, EntityType.BLOCK_DISPLAY);
        pillarCrystal.setBlock(Material.MOSSY_STONE_BRICKS.createBlockData());
        Transformation pillarCrystalTransformation = new Transformation(
                new Vector3f(scale, 0,  scale), // translation
                new AxisAngle4f(), // left rotation
                new Vector3f(1f - (scale * 2), 1.8f, 1f- (scale * 2)), // scale
                new AxisAngle4f() // right rotation
        );
        pillarCrystal.setTransformation(pillarCrystalTransformation);

        world.getBlockAt(loc).setType(Material.BARRIER);
        world.getBlockAt(loc.add(0, 1, 0)).setType(Material.BARRIER);
    }
}