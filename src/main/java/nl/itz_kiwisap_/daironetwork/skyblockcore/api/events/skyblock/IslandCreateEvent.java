package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.island.Generator;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Owner;

public class IslandCreateEvent extends Event {

	private Location location;
	private Island island;
	private Owner owner;
	private Generator generator;
	
	private static HandlerList handlers = new HandlerList();
	
	public IslandCreateEvent(Island island, Location location, Owner owner, Generator generator) {
		this.location = location;
		this.island = island;
		this.owner = owner;
		this.generator = generator;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	public Location getLocation() { return this.location; }
	public Island getIsland() { return this.island; }
	public Owner getOwner() { return this.owner; }
	public Generator getGenerator() { return this.generator; }
}
