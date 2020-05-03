package nl.itz_kiwisap_.daironetwork.skyblockcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.SkyblockManager;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.world.SkyblockWorld;
import nl.itz_kiwisap_.daironetwork.skyblockcore.utils.configuration.Resources;

public class SkyblockCore extends JavaPlugin {
	
	private static SkyblockCore instance;
	private Resources resources = new Resources(this);
	private SkyblockManager manager = new SkyblockManager(this);
	private WorldEditPlugin worldedit;
	private SkyblockWorld world = new SkyblockWorld(this);
	private LuckPerms lpapi;
	
	public void onEnable() {
		instance = this;
		
		if(Bukkit.getWorld(world.getWorld().getName()) == null) {
			world.createWorld();
		}
		
		this.worldedit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if(this.worldedit == null) {
			Bukkit.getLogger().warning("[!] Worldedit is required!");
			Bukkit.getPluginManager().disablePlugin(this);
		} else {
			this.resources.load();
			this.lpapi = LuckPermsProvider.get();
			manager.enable();
		}
	}
	
	public void onDisable() {
		instance = null;
		this.resources = null;
		manager.disable();
	}
	
	public static SkyblockCore getInstance() { return instance; }
	public Resources getResources() { return this.resources; }
	public SkyblockManager getSkyblockManager() { return this.manager; }
	public WorldEditPlugin getWorldEdit() { return this.worldedit; }
	public SkyblockWorld getWorld() { return world; }
	public LuckPerms getLuckPerms() { return this.lpapi; }
}
