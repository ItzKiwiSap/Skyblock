package nl.itz_kiwisap_.daironetwork.skyblockcore.api.events.skyblock;

import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.Role;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.RolePermission;

public class IslandRoleAddEvent extends Event {

	private Island island;
	private Role role;
	private List<RolePermission> permissions;
	private Member by;
	private int priority;
	
	private static HandlerList handlers = new HandlerList();
	
	public IslandRoleAddEvent(Island island, Role role, List<RolePermission> permissions, int priority, Member by) {
		this.island = island;
		this.role = role;
		this.permissions = permissions;
		this.by = by;
		this.priority = priority;
	}
	
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	public Island getIsland() { return this.island; }
	public Role getRole() { return this.role; }
	public List<RolePermission> getPermissions() { return this.permissions; }
	public Member getAddedBy() { return this.by; }
	public int getPriority() { return this.priority; }
}
