package org.mimja.waypoints.Storage;

import com.google.gson.Gson;
import org.mimja.waypoints.Waypoints;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StorageTools {
    public static class WaypointData {
        private static ArrayList<WaypointDataModel> waypointDataModels = new ArrayList<>();

        public static void insertWaypointDataModel(WaypointDataModel waypointDataModel) throws IOException {
            waypointDataModels.add(waypointDataModel);
            save();
        }

        public static boolean waypointExists(WaypointDataModel model) {
            return waypointExists(model.getX(), model.getY(), model.getZ());
        }

        public static boolean waypointExists(double x, double y, double z) {
            boolean found = false;
            for (WaypointDataModel waypoint : waypointDataModels) {
                if (
                        waypoint.getX() == x && waypoint.getY() == y && waypoint.getZ() == z
                        || waypoint.getX() == x && waypoint.getY() + 1 == y && waypoint.getZ() == z
                ) {
                    found = true;
                    break;
                }
            }
            return found;
        }

        public static int getWaypointDataModelIndexFromCords(double x, double y, double z) {
            int index = 0;
            for (WaypointDataModel waypoint : waypointDataModels) {
                if (
                        waypoint.getX() == x && waypoint.getY() == y && waypoint.getZ() == z
                                || waypoint.getX() == x && waypoint.getY() + 1 == y && waypoint.getZ() == z
                ) {
                    return index;
                }
                index++;
            }
            return index;
        }

        public static WaypointDataModel getWaypointDataModel(int index) {
            if(index >= 0 && index < waypointDataModels.size()) {
                return waypointDataModels.get(index);
            } else {
                return null;
            }
        }

        public static ArrayList<WaypointDataModel> getWaypointDataModels() {
            return waypointDataModels;
        }

        public static void deleteWaypointDataModelIndex(int index) throws IOException {
            waypointDataModels.remove(index);
            save();
        }

        public static void updateWaypointDataModel(int index, WaypointDataModel waypointDataModel) throws IOException {
            waypointDataModels.set(index, waypointDataModel);
            save();
        }

        public static void load() throws IOException {
            Gson gson = new Gson();
            File file = new File(Waypoints.getPlugin().getDataFolder().getAbsolutePath() + "/waypoints.json");

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return;
            }

            Reader reader = new FileReader(file);
            WaypointDataModel[] n = gson.fromJson(reader, WaypointDataModel[].class);
            if(n != null) waypointDataModels = new ArrayList<>(Arrays.asList(n));
            reader.close();
        }

        private static void save() throws IOException {
            Gson gson = new Gson();
            File file = new File(Waypoints.getPlugin().getDataFolder().getAbsolutePath() + "/waypoints.json");

            Writer writer = new FileWriter(file, false);
            gson.toJson(waypointDataModels, writer);

            writer.flush();
            writer.close();
        }
    }
}
