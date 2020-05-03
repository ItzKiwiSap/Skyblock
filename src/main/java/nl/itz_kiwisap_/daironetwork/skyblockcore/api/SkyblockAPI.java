package nl.itz_kiwisap_.daironetwork.skyblockcore.api;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.InviteManager;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.SkyblockManager;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.island.Generator;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Member;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player.Owner;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.Role;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.team.RolePermission;

/**
 * The API for the skyblock server of DairoNetwork.
 * In this class you'll find every method from getting
 * an island to create and delete an island.
 * 
 * Every method will be given with a explantion to make
 * it easier to understand the method and to know what you should use.
 * 
 * <tt>Important<tt> There are no permissions checked, so if you want
 * to check for a permission, use
 * <tt>{@link Member#getRole()} and then {@link Role#hasPermission(RolePermission)}<tt>
 * to check if a member has a permission.
 * 
 * @author Jesse Jutten | Itz_KiwiSap_
 * @see EconomyAPI
 */

public class SkyblockAPI {

	private SkyblockManager manager;
	private InviteManager invitemanager;
	
	public SkyblockAPI() {
		this.manager = SkyblockCore.getInstance().getSkyblockManager();
		this.invitemanager = this.manager.getInviteManager();
	}
	
	/**
	 * Returns the island of the given location.
	 * 
	 * @param loc location that will be checked.
	 * @return a island where the location in exists.
	 * @throws NullPointerException if the specified location
	 *         does not exists in a island.
	 */
	public Island getIsland(Location loc) { return this.manager.getIsland(loc); }
	
	/**
	 * Returns the island of the given owner.
	 * 
	 * @param owner owner that will be checked.
	 * @return the island of the owner.
	 * @throws NullPointerException if the specified owner
	 *         does not has an island.
	 */
	public Island getIsland(Owner owner) { return this.manager.getIsland(owner); }
	
	/**
	 * Returns the island of the given member.
	 * 
	 * @param member member that will be checked.
	 * @return the island of the member.
	 * @throws NullPointerException if the specified member
	 *         is not a member on an island.
	 */
	public Island getIsland(Member member) { return this.manager.getIsland(member); }
	
	/**
	 * Returns the island of the given player.
	 * 
	 * @param player player that will be checked.
	 * @return the island of the player.
	 * @throws NullPointerException if there are no islands
	 *         or if there are no members.
	 */
	public Island getIsland(Player player) { return this.manager.getIsland(player); }
	
	/**
	 * Get a member.
	 * 
	 * @param player the player that will be checked.
	 * @return <tt>true<tt> if the member exists and
	 *         the member's and player's uuid's are equals.
	 * @return <tt>false<tt> if the member doesn't exists or if
	 *         there are no member's with the same uuid as the
	 *         player.
	 */
	public Member getMember(Player player) { return this.manager.getMember(player); }
	
	/**
	 * Checks if the given owner has an island.
	 * 
	 * @param owner owner that will be checked.
	 * @return <tt>true<tt> if owner has an island.
	 * @return <tt>false<tt> if owner doesn't has an island
	 *         or if there are no islands.
	 */
	public boolean hasIsland(Owner owner) { return this.manager.hasIsland(owner); }
	
	/**
	 * Checks if the given member is member on an island.
	 * 
	 * @param member member that will be checked.
	 * @return <tt>true<tt> if member is member on an island.
	 * @return <tt>false<tt> if member is not member on an island
	 *         or if there are no islands.
	 */
	public boolean hasIsland(Member member) { return this.manager.hasIsland(member); }
	
	/**
	 * Create a new <tt>island<tt> for the given owner with the
	 * schematic and the size of the owner's rank. 
	 * 
	 * @param owner will be the owner of the island.
	 * @return <tt>true<tt> if the island is created.
	 * @return <tt>false<tt> if the owner already has an island
	 */
	public boolean createIsland(Owner owner) { return this.manager.createIsland(owner); }
	
	/**
	 * Checks if the player is on the given island.
	 * 
	 * @param island the island that will be checked.
	 * @param location the location that will be checked.
	 * @return <tt>true<tt> if the location is on the given island.
	 * @return <tt>false<tt> if the location is null or if the
	 *         location is not on the island.
	 */
	public boolean isOnIsland(Island island, Location location) { return this.manager.isOnIsland(island, location); }
	
	/**
	 * Returns all the islands that are exists.
	 * 
	 * @return a list of all islands.
	 * @throws NullPointerException if there are no islands.
	 */
	public List<Island> getIslands() { return this.manager.getIslands(); }
	
	/**
	 * Add a new role to an island, this role can be added
	 * to a player so they have more permissions on the island.
	 * 
	 * @param island the island where the role needs to be added.
	 * @param name the name of the role.
	 * @param permissions all the permissions of the role.
	 * @param priority the priority of the role. 1 is highest, 10 is lowest.
	 * @param by the member who added the role.
	 * @return <tt>true<tt> if role is successfully added.
	 * @return <tt>false<tt> if role already exists or if the priority is 0 or higher than 10. Or if the priority already exists.
	 */
	public boolean addRole(Island island, String name, List<RolePermission> permissions, int priority, Member by) { return island.addRole(name, permissions, priority, by); }
	
	/**
	 * Remove a role from an island, all members of the island
	 * with this role will be set to a role 1 lower than this role.
	 * 
	 * @param island the island where the role needs to be removed.
	 * @param name the name of the role.
	 * @param by the member who removed the role.
	 * @return <tt>true<tt> if role is successfully removed.
	 * @return <tt>false<tt> if role does not exists.
	 */
	public boolean removeRole(Island island, String name, Member by) { return island.removeRole(name, by); }
	
	/**
	 * Returns the role you specified if it exists on the island.
	 * 
	 * @param island the island where the role exists.
	 * @param name the name of the role you want to get.
	 * @return the role you specified by name
	 * @throws NullPointerException if there are no islands or if
	 *         the roles doesn't exists.
	 */
	public Role getRole(Island island, String name) { return island.getRole(name); }
	
	/**
	 * Returns all the roles of the given island.
	 * 
	 * @param island the island that will be checked.
	 * @return a map with all the roles of the given island.
	 */
	public Map<String, Role> getRoles(Island island) { return island.getRoles(); }
	
	/**
	 * Promote a member of an island to a higher role to give them
	 * more permissions.
	 * 
	 * @param island the island where the member is going to be promoted.
	 * @param member the member whose going to be promoted
	 * @param role the role the member get.
	 * @param by the member who promoted the promoted member.
	 * @return <tt>true<tt> if the member is successfully promoted.
	 * @return <tt>false<tt> if the role is lower than the current role
	 *         of the member.
	 */
	public boolean promoteMember(Island island, Member member, Role role, Member by) { return island.promoteMember(member, role, by); }
	
	/**
	 * Demote a member of an island to a lower role to give them
	 * less permissions.
	 * 
	 * @param island the island where the member is going to be demoted.
	 * @param member the member whose going to be demoted.
	 * @param role the role the member get
	 * @param by the member who demoted the demoted member.
	 * @return <tt>true<tt> if the member is successfully demoted.
	 * @return <tt>false<tt> if the role is higher than the current role
	 *         of the member.
	 */
	public boolean demoteMember(Island island, Member member, Role role, Member by) { return island.demoteMember(member, role, by); }
	
	/**
	 * Send the given member to the island home.
	 * 
	 * @param island the island that the player is going to.
	 * @param member the member that wants to go home.
	 * @return <tt>true<tt> if the member is successfully teleported to home.
	 * @return <tt>false<tt> if the home location is null or not save or if the
	 * 		   member is not a member of the given island.
	 */
	public boolean sendHome(Island island, Member member) { return island.sendHome(member); }
	
	/**
	 * Set the home of an island to the specified location.
	 * 
	 * @param island the island where the home will be set.
	 * @param member the member that set the home.
	 * @param location the location where the home will be.
	 * @return <tt>true<tt> if the home is successfully set.
	 * @return <tt>false<tt> if the member doesn't have the permission or if the
	 *         new location is not save.
	 */
	public boolean setHome(Island island, Member member, Location location) { return island.setHome(location, member); }
	
	/**
	 * Returns the home location of the island.
	 * 
	 * @param island the island that will be checked.
	 * @return the locatioon of the given island.
	 */
	public Location getHome(Island island) { return island.getHomeLocation(); }
	
	/**
	 * Give a player coop on your island to give the player temporary permissions.
	 * 
	 * @param island the island where the player is getting coop.
	 * @param player the player that is getting coop.
	 * @param by the member who added the player as coop.
	 * @return <tt>true<tt> if the player is given coop successfully.
	 * @return <tt>false<tt> if the player is already cooped on the island.
	 */
	public boolean giveCoop(Island island, Player player, Member by) { return island.giveCoop(player, by); }
	
	/**
	 * Remove the coop of a player on the given island.
	 * 
	 * @param island the island where the player has been uncooped.
	 * @param player the player who is getting uncooped.
	 * @param by the member who removed coop of the player.
	 * @return <tt>true<tt> if the coop has been successfully removed.
	 * @return <tt>false<tt> if the player is not cooped on the island.
	 */
	public boolean unCoop(Island island, Player player, Member by) { return island.unCoop(player, by); }
	
	/**
	 * Send a player back to their own island or to spawn if the player
	 * doesn't has a island. It will also uncoop the player if the player
	 * is cooped on the given island.
	 * 
	 * @param island the island where the player will be expelled from.
	 * @param player the player that is getting expelled.
	 * @param by the member who expelled the player.
	 * @return <tt>true<tt> if the player is successfully expelled.
	 * @return <tt>false<tt> if the player is not on the island.
	 */
	public boolean expel(Island island, Player player, Member by) { return island.expel(player, by); }
	
	/**
	 * Lock the given island.
	 * 
	 * @param island the island that will be locked.
	 * @param by the member who locked the island.
	 * @return <tt>true<tt> if the island is successfully locked.
	 * @return <tt>false<tt> if the island is already locked.
	 */
	public boolean lock(Island island, Member by) { return island.lock(by); }
	
	/**
	 * Unlock the given island.
	 * 
	 * @param island the island that will be unlocked.
	 * @param by the member who unlocked the island.
	 * @return <tt>true<tt> if the island is successfully unlocked.
	 * @return <tt>false<tt> if the island is already unlocked.
	 */
	public boolean unlock(Island island, Member by) { return island.unlock(by); }
	
	/**
	 * Kick the given member from the island. If you do this, the given
	 * member doesn't has any permissions anymore.
	 * 
	 * @param island the island where the member will be kicked from.
	 * @param member the member that will be kicked.
	 * @param by the member who kicked the kicked member.
	 * @return <tt>true<tt> if the member is successfully kicked.
	 * @return <tt>false<tt> if there are no members on the island or
	 *         if the member is not a member of the given island.
	 */
	public boolean kick(Island island, Member member, Member by) { return island.kick(member, by); }
	
	/**
	 * Ban a member or a player from the given island.
	 * 
	 * @param island the island where the player will be banned from.
	 * @param player the player that will be banned.
	 * @param by the member who banned the player from the island.
	 * @return <tt>true<tt> if the player is successfully banned.
	 * @return <tt>false<tt> if the player is already banned.
	 */
	public boolean ban(Island island, Player player, Member by) { return island.ban(player, by); }
	
	/**
	 * Unban a member or a player from the given island.
	 * 
	 * @param island the island where the player will be unbanned from.
	 * @param player the player that will be unbanned.
	 * @param by the member who unbanned the player from the island.
	 * @return <tt>true<tt> if the player is successfully unbanned.
	 * @return <tt>false<tt> if the player is not banned.
	 */
	public boolean unban(Island island, Player player, Member by) { return island.unban(player, by); }
	
	/**
	 * Invite a player to your island.
	 * 
	 * @param island the island where the player will be invited to.
	 * @param player the player that is invited.
	 * @param by the member that invited the player.
	 * @return <tt>true<tt> if the player is successfully invited.
	 * @return <tt>false<tt> if the member is already on an island.
	 */
	public boolean invite(Island island, Player player, Member by) { return island.invite(player, by); }
	
	/**
	 * Accept an island invite.
	 * 
	 * @param player the player that has been invited.
	 * @return <tt>true<tt> if the player has successfully accepted the invite.
	 * @return <tt>false<tt> if the player is not invited by anyone.
	 */
	public boolean acceptInvite(Player player) { return this.invitemanager.acceptRequest(player); }
	
	/**
	 * Deny an island invite.
	 * 
	 * @param player the player that has been invited.
	 * @return <tt>true<tt> if the player has successfully denied the invited.
	 * @return <tt>false<tt> if the player is not invited by anyone.
	 */
	public boolean denyInvite(Player player) { return this.invitemanager.denyRequest(player); }
	
	/**
	 * Get the size of the given island.
	 * 
	 * @param island the island that will be checked.
	 * @return the size of the island.
	 */
	public int getSize(Island island) { return island.getSize(); }
	
	/**
	 * Returns the generator of the island.
	 * 
	 * @param island the island that will be checked.
	 * @return the generator of the island.
	 */
	public Generator getGenerator(Island island) { return island.getGenerator(); }
	
	/**
	 * Calculate the island value of the given island.
	 * 
	 * @param island the island that whill be calculated.
	 * @return <tt>true<tt> if the island value is successfully calculated.
	 * @return <tt>false<tt> if there are no islands.
	 */
	public boolean calculateIslandValue(Island island) { return this.manager.calculateIslandValue(island); }
}