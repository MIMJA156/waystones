package org.mimja.waystones.Storage;

import com.google.gson.Gson;
import org.mimja.waystones.Waystones;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StorageTools {
    public static class WaypointData {
        private static ArrayList<WaystoneDataModel> waystoneDataModels = new ArrayList<>();

        public static void insertWaypointDataModel(WaystoneDataModel waystoneDataModel) throws IOException {
            waystoneDataModels.add(waystoneDataModel);
            save();
        }

        public static boolean waypointExists(WaystoneDataModel model) {
            return waypointExists(model.getX(), model.getY(), model.getZ());
        }

        public static boolean waypointExists(double x, double y, double z) {
            boolean found = false;
            for (WaystoneDataModel waypoint : waystoneDataModels) {
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
            for (WaystoneDataModel waypoint : waystoneDataModels) {
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

        public static WaystoneDataModel getWaypointDataModel(int index) {
            if(index >= 0 && index < waystoneDataModels.size()) {
                return waystoneDataModels.get(index);
            } else {
                return null;
            }
        }

        public static ArrayList<WaystoneDataModel> getWaypointDataModels() {
            return waystoneDataModels;
        }

        public static void deleteWaypointDataModelIndex(int index) throws IOException {
            waystoneDataModels.remove(index);
            save();
        }

        public static void updateWaypointDataModel(int index, WaystoneDataModel waystoneDataModel) throws IOException {
            waystoneDataModels.set(index, waystoneDataModel);
            save();
        }

        public static void load() throws IOException {
            Gson gson = new Gson();
            File file = new File(Waystones.getPlugin().getDataFolder().getAbsolutePath() + "/waypoints.json");

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return;
            }

            Reader reader = new FileReader(file);
            WaystoneDataModel[] n = gson.fromJson(reader, WaystoneDataModel[].class);
            if(n != null) waystoneDataModels = new ArrayList<>(Arrays.asList(n));
            reader.close();
        }

        private static void save() throws IOException {
            Gson gson = new Gson();
            File file = new File(Waystones.getPlugin().getDataFolder().getAbsolutePath() + "/waypoints.json");

            Writer writer = new FileWriter(file, false);
            gson.toJson(waystoneDataModels, writer);

            writer.flush();
            writer.close();
        }
    }
}
