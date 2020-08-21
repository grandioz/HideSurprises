package fr.grandoz.hidesurprise.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Surprise {

	private String name, world;
	
	private double x;
	
	private double y;
	
	private double z;
	
	
	public Surprise(double x , double y , double z , String world , String name) {

		this.x =x;
		this.z =z;
		this.y =y;
		this.name =name;
		this.world =world;
	
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
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
	
	public Location getLocation() {
		return new Location(Bukkit.getWorld(world), x, y, z);
	}
	
	public void setlocation(Location loc) {
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.world = loc.getWorld().getName();
	}
	
	
	
	
}
