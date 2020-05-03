package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;

public class IslandMemberKickEvent extends Event {

	private Island island;
	private Member member;
	private Member by;
	
	private static HandlerList handlers = new HandlerList();
	
	public IslandMemberKickEvent(Island island, Member member, Member by) {
		this.island = island;
		this.member = member;
		this.by = by;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	public Island getIsland() { return this.island; }
	public Member getMember() { return this.member; }
	public Member getKickedBy() { return this.by; }
}
