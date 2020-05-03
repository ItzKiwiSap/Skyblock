package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.Role;

public class IslandMemberPromoteEvent extends Event {

	private Island island;
	private Member member;
	private Role role;
	private Role old;
	private Member by;
	
	private static HandlerList handlers = new HandlerList();
	
	public IslandMemberPromoteEvent(Island island, Member member, Role role, Role old, Member by) {
		this.island = island;
		this.member = member;
		this.role = role;
		this.old = old;
		this.by = by;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	public Island getIsland() { return this.island; }
	public Member getMember() { return this.member; }
	public Role getRole() { return this.role; }
	public Role getOldRole() { return this.old; }
	public Member getPromotedBy() { return this.by; }
}
