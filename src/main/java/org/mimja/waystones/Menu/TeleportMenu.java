package org.mimja.waystones.Menu;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.*;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.mimja.waystones.Items.WaystoneItem;
import org.mimja.waystones.PluginNamespace;
import org.mimja.waystones.Storage.StorageTools;
import org.mimja.waystones.Storage.WaystoneDataModel;
import org.mimja.waystones.TeleportWithOffset;
import org.mimja.waystones.Waystones;

import java.io.IOException;
import java.util.*;

public class TeleportMenu implements InventoryHolder {
    private static final String leftArrowText = "back";
    private static final String rightArrowText = "forward";

    private static int target;
    private final Inventory inventory;
    private static int page;

    public TeleportMenu(int target) {
        this(1, target);
    }

    public TeleportMenu(int page, int target) {
        TeleportMenu.target = target;
        TeleportMenu.page = page;
        this.inventory = Bukkit.createInventory(this, 9 * 2, "Waypoint Menu");
        initItems();
    }

    public void initItems() {
        int waypointCount = StorageTools.WaypointData.getWaypointDataModels().size();

        ItemStack leftArrow = getItemStack(Material.ARROW, (page > 1 ? ChatColor.GOLD : ChatColor.GRAY) + leftArrowText);
        ItemStack rightArrow = getItemStack(Material.ARROW, (page * 9 < waypointCount ? ChatColor.GOLD : ChatColor.GRAY) + rightArrowText);
        ItemStack lavaBucket = getItemStack(Material.LAVA_BUCKET, ChatColor.GOLD + "delete");
        ItemStack nameTag = getItemStack(Material.NAME_TAG, ChatColor.GOLD + "rename");
        ItemStack closeBarrier = getItemStack(Material.BARRIER, ChatColor.DARK_RED + "close");
        ItemStack fillerPane = getItemStack(Material.GRAY_STAINED_GLASS_PANE, " ");

        inventory.setItem(9, closeBarrier);
        inventory.setItem(10, fillerPane);
        inventory.setItem(11, nameTag);
        inventory.setItem(12, lavaBucket);
        inventory.setItem(13, fillerPane);
        inventory.setItem(14, fillerPane);
        inventory.setItem(15, fillerPane);
        inventory.setItem(16, leftArrow);
        inventory.setItem(17, rightArrow);

        int offset = (page - 1) * 9;
        for (int i = 0; i < 9; i++) {
            ArrayList<WaystoneDataModel> waystoneDataModels = StorageTools.WaypointData.getWaypointDataModels();
            if (waystoneDataModels.size() > offset + i) {
                ItemStack teleportWaypoint = new ItemStack(Material.ENDER_EYE);
                ItemMeta meta = teleportWaypoint.getItemMeta();
                assert meta != null;

                String chat = waystoneDataModels.get(offset + i).getName();
                meta.setDisplayName(chat);
                meta.getPersistentDataContainer().set(PluginNamespace.get().getMenuItem(), PersistentDataType.INTEGER, offset + i);
                teleportWaypoint.setItemMeta(meta);

                inventory.setItem(i, teleportWaypoint);
            } else {
                inventory.setItem(i, fillerPane);
            }
        }
    }

    private static ItemStack getItemStack(Material item, String name) {
        ItemStack teleportWaypoint = new ItemStack(item);
        ItemMeta meta = teleportWaypoint.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        teleportWaypoint.setItemMeta(meta);
        return teleportWaypoint;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static void handleClick(InventoryClickEvent event) throws IOException {
        ItemStack clicked = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (clicked == null) return;
        event.setCancelled(true);

        if (clicked.getType() == Material.BARRIER) {
            player.closeInventory();
        } else if (clicked.getType() == Material.ARROW) {
            ItemMeta meta = clicked.getItemMeta();
            if (meta == null) return;

            if (meta.getDisplayName().equals(ChatColor.GOLD + leftArrowText)) {
                player.openInventory(new TeleportMenu(page - 1, target).getInventory());
            } else if (meta.getDisplayName().equals(ChatColor.GOLD + rightArrowText)) {
                player.openInventory(new TeleportMenu(page + 1, target).getInventory());
            }
        } else if (clicked.getType() == Material.ENDER_EYE) {
            ItemMeta meta = clicked.getItemMeta();
            if (meta == null) return;

            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (container.has(PluginNamespace.get().getMenuItem(), PersistentDataType.INTEGER)) {
                int targetIndex = container.get(PluginNamespace.get().getMenuItem(), PersistentDataType.INTEGER);
                WaystoneDataModel waypoint = StorageTools.WaypointData.getWaypointDataModel(targetIndex);
                if (waypoint == null) return;

                Location loc = new Location(
                        Waystones.getPlugin().getServer().getWorld(waypoint.getWorld()),
                        waypoint.getX(),
                        waypoint.getY(),
                        waypoint.getZ()
                );
                TeleportWithOffset.teleportWithRandomOffset(player, loc);
            }
        } else if (clicked.getType() == Material.LAVA_BUCKET) {
            WaystoneDataModel model = StorageTools.WaypointData.getWaypointDataModel(target);
            if (model == null) return;

            World world = Waystones.getPlugin().getServer().getWorld(model.getWorld());
            if (world == null) return;

            Location loc = new Location(world, model.getX(), model.getY(), model.getZ());
            Location loc2 = new Location(world, model.getX(), model.getY() + 1, model.getZ());

            loc.getBlock().setType(Material.AIR);
            loc2.getBlock().setType(Material.AIR);

            Collection<Entity> entities = world.getNearbyEntities(loc, 1, 2, 1);
            for (Entity entity : entities) {
                if(entity instanceof BlockDisplay) {
                    entity.remove();
                }
            }

            player.getWorld().dropItem(loc2, WaystoneItem.getWaypointItem());
            StorageTools.WaypointData.deleteWaypointDataModelIndex(target);
            player.closeInventory();
        } else if (clicked.getType() == Material.NAME_TAG) {
            WaystoneDataModel model = StorageTools.WaypointData.getWaypointDataModel(target);
            if(model == null) return;
            player.closeInventory();

            new AnvilGUI.Builder()
                    .onClick((slot, stateSnapshot) -> {
                        if(slot != AnvilGUI.Slot.OUTPUT) {
                            return Collections.emptyList();
                        }

                        String newName = stateSnapshot.getText();
                        model.setName(newName);
                        try {
                            StorageTools.WaypointData.updateWaypointDataModel(target, model);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return Arrays.asList(
                                AnvilGUI.ResponseAction.close(),
                                AnvilGUI.ResponseAction.run(() -> {
                                    stateSnapshot.getPlayer().openInventory(new TeleportMenu(page, target).getInventory());
                                })
                        );
                    })
                    .text(model.getName())
                    .title("Name Waypoint")
                    .plugin(Waystones.getPlugin())
                    .open(player);
        }
    }
}