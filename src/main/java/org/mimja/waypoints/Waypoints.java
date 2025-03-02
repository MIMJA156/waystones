package org.mimja.waypoints;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mimja.waypoints.Events.BlockPlace;
import org.mimja.waypoints.Events.InventoryClick;
import org.mimja.waypoints.Events.PlayerInteract;
import org.mimja.waypoints.Render.RenderTools;
import org.mimja.waypoints.Storage.StorageTools;

import java.io.IOException;
import java.util.logging.Logger;

public final class Waypoints extends JavaPlugin {
    private static Plugin plugin;
    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        Logger.getLogger("Minecraft").info("Waypoints plugin enabled!");

        plugin = this;

        try {
            StorageTools.WaypointData.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getServer().addRecipe(Crafting.getWaypointRecipe());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BlockPlace(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new InventoryClick(this),this);

        StorageTools.WaypointData.getWaypointDataModels().forEach(RenderTools::renderWaypoint);
    }

    @Override
    public void onDisable() {}
}