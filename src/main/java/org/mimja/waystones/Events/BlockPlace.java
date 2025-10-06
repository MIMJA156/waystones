package org.mimja.waystones.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.mimja.waystones.PluginNamespace;
import org.mimja.waystones.Render.RenderTools;
import org.mimja.waystones.Storage.WaystoneDataModel;
import org.mimja.waystones.Storage.StorageTools;
import org.mimja.waystones.Waystones;

import java.io.IOException;

public class BlockPlace implements Listener {
    public BlockPlace(Waystones waystones) {}

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) throws IOException {
        Block block = event.getBlock();
        ItemStack item = event.getItemInHand();
        ItemMeta data = item.getItemMeta();

        if(data == null) return;
        PersistentDataContainer dc = data.getPersistentDataContainer();

        String storedItem = dc.get(PluginNamespace.get().getItemKey(), PersistentDataType.STRING);
        if(storedItem == null) return;

        if(storedItem.equals("mimja-waystone")) {
            block.setType(Material.AIR);

            Location location = block.getLocation();
            World world = location.getWorld();

            WaystoneDataModel newWaypoint = new WaystoneDataModel(location, world.getName());
            if(StorageTools.WaypointData.waypointExists(newWaypoint)) return;

            StorageTools.WaypointData.insertWaypointDataModel(newWaypoint);
            RenderTools.renderWaypoint(newWaypoint);
        }
    }
}