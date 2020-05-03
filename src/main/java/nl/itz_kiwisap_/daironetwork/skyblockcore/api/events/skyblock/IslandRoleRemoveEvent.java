package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.Role;

public class IslandRoleRemoveEvent extends Event {

	private Island island;
	private Role role;
	private Member by;
	
	private static HandlerList handlers = new HandlerList();
	
	public IslandRoleRemoveEvent(Island island, Role role, Member by) {
		this.island = island;
		this.role = role;
		this.by = by;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	public Island getIsland() { return this.island; }
	public Role getRole() { return this.role; }
	public Member getRemovedBy() { return this.by; }
}
