package io.github.a5h73y.parkour.type;

import java.io.Serializable;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationLite implements Serializable {

	private final String worldName;
	private final double x;
	private final double y;
	private final double z;
	private final float yaw;
	private final float pitch;

	public Location getLocation() {
		return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
	}

	public LocationLite(final String worldName, final double x, final double y, final double z, final float yaw, final float pitch) {
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public LocationLite(final String worldName, final double x, final double y, final double z) {
		this(worldName, x, y, z, 0, 0);
	}

	public String getWorldName() {
		return worldName;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}
}
