package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.SkyblockRanks;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.Role;

public class Member {

	private Player player;
	private Role role;
	private SkyblockRanks rank;
	
	public Member(Player player, Role role) {
		this.player = player;
		this.role = role;
		
		User user = SkyblockCore.getInstance().getLuckPerms().getUserManager().getUser(player.getUniqueId());		
    	Group group = SkyblockCore.getInstance().getLuckPerms().getGroupManager().getGroup(user.getPrimaryGroup());
    	
    	if(group.getName().equalsIgnoreCase("cloud")) this.rank = SkyblockRanks.CLOUD;
    	else if(group.getName().equalsIgnoreCase("snow")) this.rank = SkyblockRanks.SNOW;
    	else if(group.getName().equalsIgnoreCase("rain")) this.rank = SkyblockRanks.RAIN;
    	else if(group.getName().equalsIgnoreCase("hail")) this.rank = SkyblockRanks.HAIL;
    	else if(group.getName().equalsIgnoreCase("thunder")) this.rank = SkyblockRanks.THUNDER;
    	else if(group.getName().equalsIgnoreCase("lightning")) this.rank = SkyblockRanks.LIGHTNING;
    	else if(group.getName().equalsIgnoreCase("player")) this.rank = SkyblockRanks.PLAYER;
    	else this.rank = SkyblockRanks.STAFF;
	}
	
	public void setRole(Role role) { this.role = role; }
	
	public String getName() { return this.player.getName(); }
	public UUID getUniqueId() { return this.player.getUniqueId(); }
	public Player getPlayer() { return this.player; }
	public Role getRole() { return this.role; }
	public SkyblockRanks getRank() { return this.rank; }
}
