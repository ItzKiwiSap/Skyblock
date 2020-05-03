package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;

public class SkyblockWorld {

	private SkyblockCore plugin;
	private String name = plugin.getResources().getConfig().getString("Skyblock.WorldName");
	private World world;
	
	public SkyblockWorld(SkyblockCore plugin) { this.plugin = plugin; }
	
	public void createWorld() {
		if(name == null) this.name = "Skyblock";
		
		if(Bukkit.getWorld(name) == null) {
			WorldCreator wc = new WorldCreator(name);
			wc.generateStructures(false);
			wc.generator(new SkyblockWorldGenerator());
			Bukkit.getServer().createWorld(wc);
		}
		
		this.world = Bukkit.getWorld(name);
	}
	
	public String getName() { return this.name; }
	public World getWorld() { return this.world; }
}
