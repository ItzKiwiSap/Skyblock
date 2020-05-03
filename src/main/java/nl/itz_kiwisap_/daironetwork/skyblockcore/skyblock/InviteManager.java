package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.PlayerAcceptIslandInviteEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.PlayerDenyIslandInviteEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.PlayerInviteToIslandEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.tasks.InviteTask;

public class InviteManager {

	private SkyblockCore plugin;

	private HashMap<UUID, UUID> invitePlayers;
	private HashMap<UUID, BukkitTask> timeleft;	
	private int time = (this.plugin.getResources().getConfig().getInt("Invite.Time") < 30) ? 30 : this.plugin.getResources().getConfig().getInt("Invite.Time");

	public InviteManager(SkyblockCore plugin) {
		this.plugin = plugin;
		this.invitePlayers = new HashMap<>();
		this.timeleft = new HashMap<>();
	}

	public void request(Member inviter, Player invited) {		
		if(this.invitePlayers.containsKey(invited.getUniqueId())) {
			UUID uuid = this.invitePlayers.get(invited.getUniqueId());
			Player inviterPlayer = Bukkit.getPlayer(uuid);

			if(inviterPlayer.getUniqueId().equals(inviter.getUniqueId())) {
				inviter.getPlayer().sendMessage(this.plugin.getResources().format("Invite.AlreadyInvited", invited.getName()));
				return;
			} else {
				this.invitePlayers.remove(inviter.getUniqueId());
			}
		}

		this.invitePlayers.put(invited.getUniqueId(), inviter.getUniqueId());

		inviter.getPlayer().sendMessage(this.plugin.getResources().format("Invite.YouSuccessfullyInvited", invited.getName(), time));

		TextComponent accept = new TextComponent(this.plugin.getResources().getConfig().getString("Invite.AcceptButton").replace('&', '§'));
		accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(this.plugin.getResources().format(false, "Invite.AcceptHover"))));
		accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/is accept"));

		TextComponent deny = new TextComponent(this.plugin.getResources().getConfig().getString("Invite.DenyButton").replace('&', '§'));
		deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(this.plugin.getResources().format(false, "Invite.DenyHover"))));
		deny.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/is deny"));

		for(String s : this.plugin.getResources().getMessages().getStringList("Invite.YouHaveBeenInvited")) {
			if(s.contains("{0}")) invited.sendMessage(this.plugin.getResources().format(false, s, accept));
			else if(s.contains("{1}")) invited.sendMessage(this.plugin.getResources().format(false, s, deny));
			else invited.sendMessage(this.plugin.getResources().format(false, s));
		}

		this.timeleft.put(invited.getUniqueId(), new InviteTask(this.plugin, this, this.time, inviter, invited).runTaskTimerAsynchronously(this.plugin, 0L, 20L));

		PlayerInviteToIslandEvent event = new PlayerInviteToIslandEvent(this.plugin.getSkyblockManager().getIsland(inviter), inviter, invited);
		Bukkit.getPluginManager().callEvent(event);
	}

	public boolean acceptRequest(Player invited) {
		if(!this.invitePlayers.containsKey(invited.getUniqueId())) return false;
		else {
			UUID uuid = this.invitePlayers.get(invited.getUniqueId());
			Player inviter = Bukkit.getPlayer(uuid);

			Member member = this.plugin.getSkyblockManager().getMember(inviter);
			Island island = this.plugin.getSkyblockManager().getIsland(member);

			this.plugin.getSkyblockManager().getMembers().add(new Member(invited, island.getRole("member")));
			Member invitedPlayer = this.plugin.getSkyblockManager().getMember(invited);

			island.getIslandMemberRole().put(invitedPlayer, invitedPlayer.getRole());

			this.timeleft.get(invited.getUniqueId()).cancel();
			this.timeleft.remove(invited.getUniqueId());

			this.invitePlayers.remove(invited.getUniqueId());

			invited.sendMessage(this.plugin.getResources().format("Invite.Accept.YouHaveAccepted", inviter.getName()));
			inviter.sendMessage(this.plugin.getResources().format("Invite.Accept.PlayerAccepted", invited.getName()));

			island.sendHome(invitedPlayer);
			
			PlayerAcceptIslandInviteEvent event = new PlayerAcceptIslandInviteEvent(island, member, invited);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}
	
	public boolean denyRequest(Player invited) {
		if(!this.invitePlayers.containsKey(invited.getUniqueId())) return false;
		else {
			UUID uuid = this.invitePlayers.get(invited.getUniqueId());
			Player inviter = Bukkit.getPlayer(uuid);
			
			this.timeleft.get(invited.getUniqueId()).cancel();
			this.timeleft.remove(invited.getUniqueId());
			
			this.invitePlayers.remove(invited.getUniqueId());
			
			invited.sendMessage(this.plugin.getResources().format("Invite.Deny.YouHaveDenied", inviter.getName()));
			inviter.sendMessage(this.plugin.getResources().format("Invite.Deny.PlayerDenied", invited.getName()));
			
			PlayerDenyIslandInviteEvent event = new PlayerDenyIslandInviteEvent(this.plugin.getSkyblockManager().getMember(inviter), invited);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}

	public HashMap<UUID, UUID> getInvitePlayers() { return this.invitePlayers; }
	public HashMap<UUID, BukkitTask> getTimeLeft() { return this.timeleft; }
}