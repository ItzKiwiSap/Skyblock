package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.InviteManager;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;

public class InviteTask extends BukkitRunnable {

	private SkyblockCore plugin;
	private InviteManager manager;
	
	int time = 0;
	private Member inviter;
	private Player invited;
	
	public InviteTask(SkyblockCore plugin, InviteManager manager, int time, Member inviter, Player invited) {
		this.plugin = plugin;
		this.manager = manager;
		this.time = time;
		this.inviter = inviter;
		this.invited = invited;
	}
	
	@Override
	public void run() {
		time--;
		
		if(time == 0) {
			inviter.getPlayer().sendMessage(this.plugin.getResources().format("Invite.YourInviteHasExpired", invited.getName()));
			invited.sendMessage(this.plugin.getResources().format("Invite.InviteHasExpired", inviter.getName()));
			this.manager.getInvitePlayers().remove(invited.getUniqueId());
			this.manager.getTimeLeft().remove(invited.getUniqueId());
			this.cancel();
			return;
		}
	}
}
