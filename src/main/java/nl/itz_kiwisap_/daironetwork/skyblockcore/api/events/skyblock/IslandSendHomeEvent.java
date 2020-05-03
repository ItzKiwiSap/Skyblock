package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;

public class IslandSendHomeEvent extends Event {

	private Island island;
	private Member member;
	
	private static HandlerList handlers = new HandlerList();
	
	public IslandSendHomeEvent(Island island, Member member) {
		this.island = island;
		this.member = member;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	public Island getIsland() { return this.island; }
	public Member getMember() { return this.member; }
}
