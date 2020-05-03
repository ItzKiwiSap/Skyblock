package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;

public class PlayerAcceptIslandInviteEvent extends Event {

	private Island island;
	private Member inviter;
	private Player invited;
	
	private static HandlerList handlers = new HandlerList();
	
	public PlayerAcceptIslandInviteEvent(Island island, Member inviter, Player invited) {
		this.island = island;
		this.inviter = inviter;
		this.invited = invited;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	@Override
	public HandlerList getHandlers() { return handlers; }
	
	public Island getIsland() { return this.island; }
	public Member getInviter() { return this.inviter; }
	public Player getInvited() { return this.invited; }
}
