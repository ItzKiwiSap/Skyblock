package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandCreateEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandLockEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandMemberDemoteEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandMemberKickEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandMemberPromoteEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandRoleAddEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandRoleRemoveEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandSendHomeEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandSetHomeEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.IslandUnlockEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.PlayerAddAsCoopEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.PlayerBanEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.PlayerExpelEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.PlayerRemoveAsCoopEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock.PlayerUnbanEvent;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.Role;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.RolePermission;

public class CustomListeners implements Listener {

	private SkyblockCore plugin;
	
	public CustomListeners(SkyblockCore plugin) {
		this.plugin = plugin;
		
		this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin) this.plugin);
	}
	
	@EventHandler
	public void onIslandCreate(IslandCreateEvent event) {
		Player player = event.getOwner().getPlayer();
		
		player.sendMessage(this.plugin.getResources().format("Create.Created"));
	}
	
	@EventHandler
	public void onIslandLock(IslandLockEvent event) {
		Player by = event.getLockedBy().getPlayer();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			member.getPlayer().sendMessage(this.plugin.getResources().format("Lock.Locked", by.getName()));
		}
	}
	
	@EventHandler
	public void onIslandUnlock(IslandUnlockEvent event) {
		Player by = event.getUnlockedBy().getPlayer();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			member.getPlayer().sendMessage(this.plugin.getResources().format("Lock.Locked", by.getName()));
		}
	}
	
	@EventHandler
	public void onIslandMemberDemote(IslandMemberDemoteEvent event) {
		Player player = event.getMember().getPlayer();
		Player by = event.getDemotedBy().getPlayer();
		Role role = event.getRole();
		Role old = event.getOldRole();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getRole().hasPermission(RolePermission.DEMOTEPLAYER) && member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString()) && !member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("Demote.Demoted", player.getName(), by.getName(), old.getName(), role.getName()));
			}
		}
		
		player.sendMessage(this.plugin.getResources().format("Demote.YouHaveBeenDemoted", by.getName(), old.getName(), role.getName()));
		by.sendMessage(this.plugin.getResources().format("Demote.YouSuccessfullyDemoted", player.getName(), old.getName(), role.getName()));
	}
	
	@EventHandler
	public void onIslandMemberPromote(IslandMemberPromoteEvent event) {
		Player player = event.getMember().getPlayer();
		Player by = event.getPromotedBy().getPlayer();
		Role role = event.getRole();
		Role old = event.getOldRole();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getRole().hasPermission(RolePermission.DEMOTEPLAYER) && member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString()) && !member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("Promote.Promoted", player.getName(), by.getName(), old.getName(), role.getName()));
			}
		}
		
		player.sendMessage(this.plugin.getResources().format("Promote.YouHaveBeenPromoted", by.getName(), old.getName(), role.getName()));
		by.sendMessage(this.plugin.getResources().format("Promote.YouSuccessfullyPromoted", player.getName(), old.getName(), role.getName()));
	}
	
	@EventHandler
	public void onIslandMemberKick(IslandMemberKickEvent event) {
		Player player = event.getMember().getPlayer();
		Player by = event.getKickedBy().getPlayer();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getRole().hasPermission(RolePermission.KICK) && member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString()) && !member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("Kick.Kicked", player.getName(), by.getName()));
			}
		}
		
		player.sendMessage(this.plugin.getResources().format("Kick.YouHaveBeenKicked", by.getName()));
		by.sendMessage(this.plugin.getResources().format("Kick.YouSuccessfullyKicked", player.getName()));
	}
	
	@EventHandler
	public void onIslandRoleAdd(IslandRoleAddEvent event) {
		Player by = event.getAddedBy().getPlayer();
		Role role = event.getRole();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getRole().hasPermission(RolePermission.ADDROLE) && member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("AddRole.Added", by.getName(), role.getName(), role.getPriority()));
			}
		}
		
		by.sendMessage(this.plugin.getResources().format("AddRole.YouSuccessfullyAdded", role.getName(), role.getPriority()));
	}
	
	@EventHandler
	public void onIslandRoleRemove(IslandRoleRemoveEvent event) {
		Player by = event.getRemovedBy().getPlayer();
		Role role = event.getRole();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getRole().hasPermission(RolePermission.REMOVEROLE) && member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("RemoveRole.Removed", by.getName(), role.getName(), role.getPriority()));
			}
		}
		
		by.sendMessage(this.plugin.getResources().format("RemoveRole.YouSuccessfullyRemoved", role.getName(), role.getPriority()));
	}
	
	@EventHandler
	public void onPlayerAddAsCoop(PlayerAddAsCoopEvent event) {
		Player player = event.getPlayer();
		Player by = event.getAddedBy().getPlayer();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString()) && !member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("AddCoop.Added", player.getName(), by.getName()));
			}
		}
		
		player.sendMessage(this.plugin.getResources().format("AddCoop.YouHaveBeenAdded", by.getName()));
		by.sendMessage(this.plugin.getResources().format("AddCoop.YouSuccessfullyAdded", player.getName()));
	}
	
	@EventHandler
	public void onPlayerRemoveAsCoop(PlayerRemoveAsCoopEvent event) {
		Player player = event.getPlayer();
		Player by = event.getRemovedBy().getPlayer();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString()) && !member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("RemoveCoop.Removed", player.getName(), by.getName()));
			}
		}
		
		player.sendMessage(this.plugin.getResources().format("RemoveCoop.YouHaveBeenRemoved", by.getName()));
		by.sendMessage(this.plugin.getResources().format("RemoveCoop.YouSuccessfullyRemoved", player.getName()));
	}
	
	@EventHandler
	public void onPlayerBan(PlayerBanEvent event) {
		Player player = event.getPlayer();
		Player by = event.getBannedBy().getPlayer();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getRole().hasPermission(RolePermission.BAN) && member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString()) && !member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("Ban.Banned", player.getName(), by.getName()));
			}
		}
		
		player.sendMessage(this.plugin.getResources().format("Ban.YouHaveBeenBanned", by.getName()));
		by.sendMessage(this.plugin.getResources().format("Ban.YouSuccessfullyBanned", player.getName()));
	}
	
	@EventHandler
	public void onPlayerBan(PlayerUnbanEvent event) {
		Player player = event.getPlayer();
		Player by = event.getUnbannedBy().getPlayer();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getRole().hasPermission(RolePermission.UNBAN) && member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString()) && !member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("Unban.Unbanned", player.getName(), by.getName()));
			}
		}
		
		player.sendMessage(this.plugin.getResources().format("Unban.YouHaveBeenUnbanned", by.getName()));
		by.sendMessage(this.plugin.getResources().format("Unban.YouSuccessfullyUnbanned", player.getName()));
	}
	
	@EventHandler
	public void onPlayerExpel(PlayerExpelEvent event) {
		Player player = event.getPlayer();
		Player by = event.getExpelledBy().getPlayer();
		
		for(Member member : event.getIsland().getIslandMemberRole().keySet()) {
			if(member.getRole().hasPermission(RolePermission.EXPEL) && member.getPlayer().isOnline() && !member.getUniqueId().toString().equals(by.getUniqueId().toString()) && !member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
				member.getPlayer().sendMessage(this.plugin.getResources().format("Expel.Expelled", player.getName(), by.getName()));
			}
		}
		
		player.sendMessage(this.plugin.getResources().format("Expel.YouHaveBeenExpelled", by.getName()));
		by.sendMessage(this.plugin.getResources().format("Expel.YouSuccessfullyExpelled", player.getName()));
	}
	
	@EventHandler
	public void onIslandSendHome(IslandSendHomeEvent event) {
		Player player = event.getMember().getPlayer();
		
		player.sendMessage(this.plugin.getResources().format("SendHome.YouHaveBeenTeleported"));
	}
	
	@EventHandler
	public void onIslandSetHome(IslandSetHomeEvent event) {
		Player player = event.getMember().getPlayer();
		Location loc = event.getLocation();
		String x = String.valueOf(loc.getX());
		String y = String.valueOf(loc.getY());
		String z = String.valueOf(loc.getZ());
		
		player.sendMessage(this.plugin.getResources().format("SetHome.YouSuccessfullySetHome", x, y, z));
	}
	
	// IMPORTANT - INVITE
}
