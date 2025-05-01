package org.mimja.waystones.Storage;

import org.bukkit.Location;

public class WaystoneDataModel {
    String world;
    String name;

    double x;
    double y;
    double z;

    public WaystoneDataModel(Location location, String world) {
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
        this.world = world;
    }

    public String getWorld() {
        return world;
    }

    public String getName() {
        if(name == null) {
            return x + ", " + y + ", " + z;
        }

        return name;
    }

    public void setWorldId(String world) {
        this.world = world;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
