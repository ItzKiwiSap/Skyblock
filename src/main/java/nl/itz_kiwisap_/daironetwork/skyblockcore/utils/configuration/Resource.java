package nl.itz_kiwisap_.daironetwork.skyblockcore.utils.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Resource extends YamlConfiguration {

	private String name;
	private final File file;
	
	public Resource(Plugin plugin, String name) {
		this.name = name;
		this.file = new File(plugin.getDataFolder(), name);
		
		if(!this.file.getParentFile().exists())	this.file.getParentFile().mkdirs();
		if(!this.file.exists()) plugin.saveResource(name, true);
	}
	
	public void load() {
		try {
			load(this.file);
		} catch(IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			save(this.file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() { return this.name; }
	public File getFile() { return this.file; }
}
