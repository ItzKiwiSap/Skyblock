package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

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
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.InviteManager;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.island.Generator;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.island.Schematica;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.island.Size;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Owner;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.Role;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.RolePermission;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.world.SkyblockWorld;
import nl.itz_kiwisap_.daironetwork.skyblockcore.utils.Utils;

public class Island {

	private SkyblockCore plugin;
	private Owner owner;
	private Location location;
	private Map<String, Role> roles;
	private Map<Role, Integer> rolePriority;
	private Map<Member, Role> islandMemberRole;
	private Generator generator;
	private Location home;
	
	private int size;
	private boolean locked;
	private long value;
	
	private int minX;
	private int minZ;
	private int minprotectedX;
	private int minprotectedZ;
	
	private List<Player> coopplayers;
	private List<Player> bannedplayers;
	
	public Island(SkyblockCore plugin, Owner owner, Location loc) {
		this.plugin = plugin;
		this.owner = owner;
		this.location = loc;
		this.roles = new HashMap<>();
		this.rolePriority = new HashMap<>();
		this.islandMemberRole = new HashMap<>();
		
		this.locked = false;
		
		this.coopplayers = new ArrayList<>();
		this.bannedplayers = new ArrayList<>();
		
		this.home = loc;
		this.addRole("Owner", Arrays.asList(RolePermission.ALL), 0, null);
		this.islandMemberRole.put(new Member(this.owner.getPlayer(), this.getRole("Owner")), this.getRole("Owner"));
		
		this.create(loc, new Size(owner.getRank(), SkyblockCore.getInstance()), new Schematica(owner.getRank()));
		this.minX = (int) (this.location.getX() - this.size / 2);
		this.minZ = (int) (this.location.getZ() - this.size / 2);
		this.minprotectedX = (int) (this.location.getX() - (this.size + 10) / 2);
		this.minprotectedZ = (int) (this.location.getZ() - (this.size + 10) / 2);
	}
	
	public void create(Location loc, Size size, Schematica schem) {	
		ClipboardFormat format = ClipboardFormats.findByFile(schem.getSchematicFile());
		try (ClipboardReader reader = format.getReader(new FileInputStream(schem.getSchematicFile()))) {
		   Clipboard clipboard = reader.read();
		   
		   try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(new SkyblockWorld(SkyblockCore.getInstance()).getWorld())	, -1)) {
			   	Operation operation = new ClipboardHolder(clipboard)
			            .createPaste(editSession)
			            .to(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()))
			            .ignoreAirBlocks(false)
			            .build();
			    try {
					Operations.complete(operation);
					Location maxpoint = new Location(loc.getWorld(), editSession.getMaximumPoint().getX(),
							editSession.getMaximumPoint().getY(),
							editSession.getMaximumPoint().getZ());
					Location minpoint = new Location(loc.getWorld(), editSession.getMinimumPoint().getX(),
							editSession.getMinimumPoint().getY(),
							editSession.getMinimumPoint().getZ());
					
					List<Block> blocks = Utils.getAllBlocksBetweenTwoLocations(maxpoint, minpoint);
					
					this.size = size.getSize();
					
					for(Block block : blocks) {
						if(block.getType() == Material.END_PORTAL_FRAME || block instanceof EndPortalFrame) {
							this.generator = new Generator(this, block.getLocation());
							
							IslandCreateEvent event = new IslandCreateEvent(this, loc, this.owner, this.generator);
							Bukkit.getPluginManager().callEvent(event);
							break;
						}
					}
				} catch (WorldEditException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reset() {
		// TODO
	}
	
	public void delete() {
		// TODO
	}
	
	public boolean addRole(String name, List<RolePermission> permissions, int priority, Member by) {
		if(this.roles.containsKey(name.toLowerCase())) return false;
		else {
			if(priority == 0 || priority > 10) return false;
			if(this.rolePriority.containsValue(priority)) return false;
			Role role = new Role(name.toLowerCase(), permissions, priority);
			this.roles.put(name, role);
			this.rolePriority.put(role, priority);
			
			IslandRoleAddEvent event = new IslandRoleAddEvent(this, role, permissions, priority, by);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}

	public boolean removeRole(String name, Member by) {
		if(!this.roles.containsKey(name.toLowerCase())) return false;
		else {
			Role role = this.roles.get(name.toLowerCase());
			
			IslandRoleRemoveEvent event = new IslandRoleRemoveEvent(this, role, by);
			Bukkit.getPluginManager().callEvent(event);
			this.roles.remove(name.toLowerCase());
			this.rolePriority.remove(role);
			return true;
		}
	}
	
	public Role getRole(String name) {
		if(this.roles == null || this.roles.isEmpty()) return null;
		else {
			if(this.roles.containsKey(name.toLowerCase())) {
				return this.roles.get(name.toLowerCase());
			}
		}
		return null;
	}
	
	public boolean promoteMember(Member p, Role role, Member by) {
		if(this.islandMemberRole == null || this.islandMemberRole.isEmpty() || this.islandMemberRole.get(p).getName().equals(role.getName())) return false;
		else {
			int prio = this.rolePriority.get(p.getRole());
			Role old = p.getRole();
			
			if(prio > this.rolePriority.get(role)) {
				this.islandMemberRole.remove(p);
				this.islandMemberRole.put(p, role);
				p.setRole(role);
				
				IslandMemberPromoteEvent event = new IslandMemberPromoteEvent(this, p, role, old, by);
				Bukkit.getPluginManager().callEvent(event);
				return true;
			}			
			return false;
		}
	}
	
	public boolean demoteMember(Member p, Role role, Member by) {
		if(this.islandMemberRole == null || this.islandMemberRole.isEmpty() || this.islandMemberRole.get(p).getName().equals(role.getName())) return false;
		else {
			int prio = this.rolePriority.get(p.getRole());
			Role old = p.getRole();
			
			if(prio < this.rolePriority.get(role)) {
				this.islandMemberRole.remove(p);
				this.islandMemberRole.put(p, role);
				p.setRole(role);
				
				IslandMemberDemoteEvent event = new IslandMemberDemoteEvent(this, p, role, old, by);
				Bukkit.getPluginManager().callEvent(event);
				return true;
			}			
			return false;
		}
	}
	
	public boolean sendHome(Member member) {		
		if(this.home != null) {
			if(this.home.clone().add(0, -1, 0).getBlock().getType() != Material.AIR) {
				if(this.islandMemberRole.containsKey(member)) {
					member.getPlayer().teleport(this.home);
					
					IslandSendHomeEvent event = new IslandSendHomeEvent(this, member);
					Bukkit.getPluginManager().callEvent(event);
					return true;
				}
			} 
		}
		return false;
	}
	
	public boolean setHome(Location loc, Member member) {
		if(!this.islandMemberRole.containsKey(member)) return false;
		else {
			if(loc.clone().add(0, -1, 0).getBlock().getType() != Material.AIR) {
				this.home = loc;
				
				IslandSetHomeEvent event = new IslandSetHomeEvent(this, member, loc);
				Bukkit.getPluginManager().callEvent(event);
			}
			return false;
		}
	}
	
	public boolean giveCoop(Player player, Member by) {
		if(this.coopplayers.contains(player)) return false;
		else {
			this.coopplayers.add(player);
			
			PlayerAddAsCoopEvent event = new PlayerAddAsCoopEvent(this, player, by);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}
	
	public boolean unCoop(Player player, Member by) {
		if(!this.coopplayers.contains(player)) return false;
		else {
			this.coopplayers.remove(player);
			
			PlayerRemoveAsCoopEvent event = new PlayerRemoveAsCoopEvent(this, player, by);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}
	
	public boolean expel(Player player, Member by) {
		if(!this.plugin.getSkyblockManager().isOnIsland(this, player.getLocation())) return false;
		else {
			this.unCoop(player, by);
			Island island = this.plugin.getSkyblockManager().getIsland(player);
			
			if(island != null) {
				island.sendHome(this.plugin.getSkyblockManager().getMember(player));
				PlayerExpelEvent event = new PlayerExpelEvent(this, player, by);
				Bukkit.getPluginManager().callEvent(event);
				return true;
			} else {
				PlayerExpelEvent event = new PlayerExpelEvent(null, player, by);
				Bukkit.getPluginManager().callEvent(event);
				// TODO Send to spawn
				return true;
			}
		}
	}
	
	public boolean lock(Member by) {
		if(this.locked) return false;
		else {
			this.locked = true;
			IslandLockEvent event = new IslandLockEvent(this, by);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}
	
	public boolean unlock(Member by) {
		if(!this.locked) return false;
		else {
			this.locked = false;
			IslandUnlockEvent event = new IslandUnlockEvent(this, by);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}
	
	public boolean kick(Member member, Member by) {
		if(this.islandMemberRole == null || this.islandMemberRole.isEmpty()) return false;
		else {
			if(this.islandMemberRole.containsKey(member)) {
				this.islandMemberRole.remove(member);
				this.plugin.getSkyblockManager().getMembers().remove(member);
				IslandMemberKickEvent event = new IslandMemberKickEvent(this, member, by);
				Bukkit.getPluginManager().callEvent(event);
				// TODO send to spawn
				return true;
			}
			return false;
		}
	}
	
	public boolean ban(Player player, Member by) {
		if(this.bannedplayers.contains(player)) return false;
		else {
			Member member = this.plugin.getSkyblockManager().getMember(player);
			
			if(this.islandMemberRole.containsKey(member)) {
				this.islandMemberRole.remove(member);
				this.bannedplayers.add(player);
				this.plugin.getSkyblockManager().getMembers().remove(member);
				PlayerBanEvent event = new PlayerBanEvent(this, player, by);
				Bukkit.getPluginManager().callEvent(event);
				return true;
			}
			this.bannedplayers.add(player);
			PlayerBanEvent event = new PlayerBanEvent(this, player, by);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}
	
	public boolean unban(Player player, Member by) {
		if(!this.bannedplayers.contains(player)) return false;
		else {
			this.bannedplayers.remove(player);
			PlayerUnbanEvent event = new PlayerUnbanEvent(this, player, by);
			Bukkit.getPluginManager().callEvent(event);
			return true;
		}
	}
	
	public boolean invite(Player player, Member by) {
		if(this.plugin.getSkyblockManager().getMember(player) != null) return false;
		else {
			InviteManager manager = this.plugin.getSkyblockManager().getInviteManager();
			
			manager.request(by, player);
			return true;
		}
	}
	
	public void setValue(long value) { this.value = value; }
	
	public Owner getOwner() { return this.owner; }
	public Location getLocation() { return this.location; }
	public Map<String, Role> getRoles() { return this.roles; }
	public Map<Role, Integer> getRolePriority() { return this.rolePriority; }
	public Map<Member, Role> getIslandMemberRole() { return this.islandMemberRole; }
	public Generator getGenerator() { return this.generator; }
	public Location getHomeLocation() { return this.home; }
	public List<Player> getCoopPlayers() { return this.coopplayers; }
	public int getSize() { return this.size; }
	public boolean isLocked() { return this.locked; }
	public List<Player> getBannedPlayers() { return this.bannedplayers; }
	public long getValue() { return this.value; }
	public int getMinX() { return this.minX; }
	public int getMinZ() { return this.minZ; }
	public int getMinProtectedX() { return this.minprotectedX; }
	public int getMinProtectedZ() { return this.minprotectedZ; }
}
