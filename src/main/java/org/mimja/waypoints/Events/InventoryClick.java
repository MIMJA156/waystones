package org.mimja.waypoints.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.mimja.waypoints.Menu.TeleportMenu;
import org.mimja.waypoints.Waypoints;

import java.io.IOException;

public class InventoryClick implements Listener {
    public InventoryClick(Waypoints waypoints) {}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;

        if(inventory.getHolder() instanceof TeleportMenu) {
            TeleportMenu.handleClick(event);
        }
    }
}