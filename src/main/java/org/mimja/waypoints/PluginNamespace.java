package org.mimja.waypoints;

import org.bukkit.NamespacedKey;

public class PluginNamespace {
    private static final PluginNamespace instance = new PluginNamespace();
    private PluginNamespace() {}

    public static PluginNamespace get() {
        return instance;
    }

    public NamespacedKey getRecipeKey() {
        return new NamespacedKey(Waypoints.getPlugin(), "waypoint_recipe");
    }

    public NamespacedKey getItemKey() {
        return new NamespacedKey(Waypoints.getPlugin(), "waypoint_item");
    }

    public NamespacedKey getMenuItem() {
        return new NamespacedKey(Waypoints.getPlugin(), "waypoint_menu_item");
    }
}