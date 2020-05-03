package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.island;

import org.bukkit.Location;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;

public class Generator {

	private Island island;
	private Location location;
	
	public Generator(Island island, Location location) {
		this.island = island;
		this.location = location;
	}
	
	public Island getIsland() { return this.island; }
	public Location getLocation() { return this.location; }
}
