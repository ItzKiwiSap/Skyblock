package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;

public class IslandSetHomeEvent extends Event {

	private Island island;
	private Member member;
	private Location location;
	
	private static HandlerList handlers = new HandlerList();
	
	public IslandSetHomeEvent(Island island, Member member, Location location) {
		this.island = island;
		this.member = member;
		this.location = location;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	public Island getIsland() { return this.island; }
	public Member getMember() { return this.member; }
	public Location getLocation() { return this.location; }
}
