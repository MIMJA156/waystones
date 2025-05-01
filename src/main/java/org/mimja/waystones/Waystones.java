package org.mimja.waystones;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mimja.waystones.Events.BlockPlace;
import org.mimja.waystones.Events.InventoryClick;
import org.mimja.waystones.Events.PlayerInteract;
import org.mimja.waystones.Render.RenderTools;
import org.mimja.waystones.Storage.StorageTools;

import java.io.IOException;
import java.util.logging.Logger;

public final class Waystones extends JavaPlugin {
    private static Plugin plugin;
    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        Logger log = Logger.getLogger("Minecraft");
        log.info("Waystones plugin enabled!");

        plugin = this;

        try {
            StorageTools.WaypointData.load();
        } catch (IOException e) {
            log.warning("failed to load saved Waystones");
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