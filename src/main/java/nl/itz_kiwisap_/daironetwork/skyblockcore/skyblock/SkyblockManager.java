package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Owner;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.world.SkyblockWorld;

public class SkyblockManager {

	public SkyblockCore plugin;
	private SkyblockWorld world;
	private List<Island> islands;
	private List<Member> members;
	private InviteManager manager;
	
	private double x = plugin.getResources().getConfig().getDouble("Skyblock.StartX"),
			y = plugin.getResources().getConfig().getDouble("Skyblock.StartY"),
			z = plugin.getResources().getConfig().getDouble("Skyblock.StartZ"),
			offset = plugin.getResources().getConfig().getDouble("Skyblock.Offset");
	private Location nextlocation = new Location(plugin.getWorld().getWorld(), x, y, z);
	
	public SkyblockManager(SkyblockCore plugin) {
		this.plugin = plugin;
		this.islands = new ArrayList<>();
		this.members = new ArrayList<>();
	}
	
	public void enable() {
		this.world.createWorld();
		this.calculateIslands();
		
		this.manager = new InviteManager(this.plugin);
	}
	
	public void disable() {
		
	}
	
	public Island getIsland(Location loc) {
		if(this.islands == null || this.islands.isEmpty()) return null;
		else {
			for(Island island : this.islands) {
				if(island.getLocation().toString().equals(loc.toString())) {
					return island;
				}
			}
		}
		return null;
	}
	
	public Island getIsland(Owner owner) {
		if(this.islands == null || this.islands.isEmpty()) return null;
		else {
			for(Island island : this.islands) {
				if(island.getOwner().getUniqueId().toString().equals(owner.getUniqueId().toString())) {
					return island;
				}
			}
		}
		return null;
	}
	
	public Island getIsland(Member member) {
		if(this.islands == null || this.islands.isEmpty()) return null;
		else {
			for(Island island : this.islands) {
				if(island.getIslandMemberRole() != null && island.getIslandMemberRole().containsKey(member)) {
					return island;
				}
			}
		}
		return null;
	}
	
	public Island getIsland(Player player) {
		if(this.islands == null || this.islands.isEmpty() || this.members == null || this.members.isEmpty()) return null;
		else {
			for(Island island : this.islands) {
				for(Member member : island.getIslandMemberRole().keySet()) {
					if(member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
						return island;
					}
				}
			}
		}
		return null;
	}
	
	public Island getIslandOfPlayersLocation(Player player) {
		if(this.islands == null || this.islands.isEmpty()) return null;
		else {
			for(Island island : this.islands) {
				if(this.isOverProtectionRange(island, player.getLocation())) {
					return island;
				}
			}
		}
		return null;
	}
	
	public boolean hasIsland(Owner owner) {
		if(this.islands == null || this.islands.isEmpty()) return false;
		else {
			for(Island island : this.islands) {
				if(island.getOwner().getUniqueId().toString().equals(owner.getUniqueId().toString())) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean hasIsland(Member member) {
		if(this.islands == null || this.islands.isEmpty()) return false;
		else {
			for(Island island : this.islands) {
				if(island.getIslandMemberRole().containsKey(member)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean hasIsland(Player player) {
		if(this.islands == null || this.islands.isEmpty() || this.members == null || this.members.isEmpty()) return false;
		else {
			for(Island island : this.islands) {
				Member member = this.getMember(player);
				
				if(island.getIslandMemberRole().containsKey(member)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean createIsland(Owner owner) {
		if(this.hasIsland(owner)) return false;
		else {
			double x,z;
			x = nextlocation.getX() + offset;
			z = nextlocation.getZ();
			
			if(x > Math.abs(this.x)) {
				z += offset;
				x = nextlocation.getX() + offset;
			}
			
			Location loc = new Location(plugin.getWorld().getWorld(), x, this.y, z);
			Island island = new Island(this.plugin, owner, loc);
			nextlocation = island.getLocation();
			
			this.islands.add(island);
			return true;
		}
	}
	
	public boolean isOnIsland(Island island, Location location) {
		if(location == null) return false;
		else {
			int x = (int) Math.abs(location.getX() - island.getLocation().getX());
			int z = (int) Math.abs(location.getZ() - island.getLocation().getZ());
			return x < island.getSize() && z < island.getSize();
		}
	}
	
	public boolean isOverProtectionRange(Island island, Location location) {
		if(location == null) return false;
		else {
			int x = (int) Math.abs(location.getX() - island.getLocation().getX());
			int z = (int) Math.abs(location.getZ() - island.getLocation().getZ());
			return x < (island.getSize() + 10) && z < (island.getSize() + 10);
		}
	}
	
	public Member getMember(Player player) {
		if(this.members == null || this.members.isEmpty()) return null;
		else {
			for(Member member : this.members) {
				if(member.getUniqueId().toString().equals(player.getUniqueId().toString())) {
					return member;
				}
			}
		}
		return null;
	}
	
	public boolean calculateIslandValue(Island island) {
		if(this.islands == null || this.islands.isEmpty()) return false;
		else {
			new ValueCalculationByChunk(this.plugin, island);
			return true;
		}
	}
	
	private void calculateIslands() {
		if(this.islands == null || this.islands.isEmpty()) return;
		
		for(Island island : this.islands) {
			this.calculateIslandValue(island);
		}
		
		this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, () -> calculateIslands(), 0L, 20L * 60L * 20L);
	}
	
	public List<Island> getIslands() { return this.islands; }
	public List<Member> getMembers() { return this.members; }
	public InviteManager getInviteManager() { return this.manager; }
}