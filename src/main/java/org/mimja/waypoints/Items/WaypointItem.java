package org.mimja.waypoints.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.mimja.waypoints.PluginNamespace;

import java.util.ArrayList;

public class WaypointItem {
    public static ItemStack getWaypointItem() {
        ItemStack waypointResult = new ItemStack(Material.QUARTZ_BLOCK, 1);
        ItemMeta itemMeta = waypointResult.getItemMeta();

        assert itemMeta != null;

        itemMeta.getPersistentDataContainer().set(PluginNamespace.get().getItemKey(), PersistentDataType.STRING, "waypoint");
        itemMeta.setDisplayName("Waypoint");
        itemMeta.setEnchantmentGlintOverride(true);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Place to add a new waypoint location.");
        itemMeta.setLore(lore);

        waypointResult.setItemMeta(itemMeta);
        return waypointResult;
    }
}
