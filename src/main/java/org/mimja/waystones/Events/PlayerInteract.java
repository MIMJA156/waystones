package org.mimja.waystones.Events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.mimja.waystones.Menu.TeleportMenu;
import org.mimja.waystones.Storage.StorageTools;
import org.mimja.waystones.Waystones;

public class PlayerInteract implements Listener {
    public PlayerInteract(Waystones waystones) {}

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();

            Location loc = null;
            if (block != null) loc = block.getLocation();

            if(
                    loc != null && (
                            StorageTools.WaypointData.waypointExists(loc.getX(), loc.getY(), loc.getZ())
                            || StorageTools.WaypointData.waypointExists(loc.getX(), loc.getY() - 1, loc.getZ()
                            )
                    )
            ) {
                event.setCancelled(true);

                int index = StorageTools.WaypointData.getWaypointDataModelIndexFromCords(loc.getX(), loc.getY(), loc.getZ());
                Inventory inventory = new TeleportMenu(index).getInventory();

                player.openInventory(inventory);
            }
        }
    }
}