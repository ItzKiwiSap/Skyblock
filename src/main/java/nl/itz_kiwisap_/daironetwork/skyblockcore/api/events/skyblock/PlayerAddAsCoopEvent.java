package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;

public class PlayerAddAsCoopEvent extends Event {

	private Island island;
	private Player player;
	private Member by;
	
	private static HandlerList handlers = new HandlerList();
	
	public PlayerAddAsCoopEvent(Island island, Player player, Member by) {
		this.island = island;
		this.player = player;
		this.by = by;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	public Island getIsland() { return this.island; }
	public Player getPlayer() { return this.player; }
	public Member getAddedBy() { return this.by; }
}
