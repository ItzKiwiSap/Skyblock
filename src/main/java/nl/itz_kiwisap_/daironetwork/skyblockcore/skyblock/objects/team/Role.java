package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team;

import java.util.ArrayList;
import java.util.List;

public class Role {

	private String name;
	private List<RolePermission> permissions;
	private int priority;
	
	public Role(String name, List<RolePermission> permissions, int priority) {
		this.name = name;
		this.permissions = new ArrayList<>(permissions);
		this.priority = priority;
	}
	
	public boolean hasPermission(RolePermission permission) {
		if(this.permissions == null || this.permissions.isEmpty()) return false;
		else {
			if(this.getPermissions().contains(permission) || this.getPermissions().contains(RolePermission.ALL)) {
				return true;
			}
			return false;
		}
	}
	
	public String getName() { return this.name; }
	public List<RolePermission> getPermissions() { return this.permissions; }
	public int getPriority() { return this.priority; }
}
