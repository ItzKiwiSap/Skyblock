package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.SkyblockManager;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;

public class IslandListeners implements Listener {

	private SkyblockCore plugin;
	private SkyblockManager manager;
	
	public IslandListeners(SkyblockCore plugin) {
		this.plugin = plugin;
		this.manager = this.plugin.getSkyblockManager();
		this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin) this.plugin);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if(event.getTo().getBlock() != event.getFrom().getBlock()) {
			Player player = event.getPlayer();
			
			if(player.getWorld().getName().equals(this.plugin.getWorld().getWorld().getName())) {
				for(Island island : this.manager.getIslands()) {
					if(this.manager.isOverProtectionRange(island, event.getFrom()) && !this.manager.isOverProtectionRange(island, event.getTo())) {
						event.setCancelled(true);
						player.sendMessage(this.plugin.getResources().format("ProtectionRange.NoFurther"));
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		float xploss = Math.abs(player.getExp() * 10 / 100);
		// TODO moneyloss
		
		player.setExp(player.getExp() - xploss);
		
		if(this.manager.hasIsland(player)) {
			Island island = this.manager.getIsland(player);
			
			if(this.manager.isOverProtectionRange(island, player.getLocation())) {
				island.sendHome(this.manager.getMember(player));
				
				player.sendMessage(this.plugin.getResources().format("Death.Death", xploss));
			} else {
				// TODO send to spawn
			}
		} else {
			// TODO send to spawn
		}
	}
}
