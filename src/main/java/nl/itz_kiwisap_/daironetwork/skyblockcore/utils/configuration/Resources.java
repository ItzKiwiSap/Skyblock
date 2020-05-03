package nl.itz_kiwisap_.daironetwork.skyblockcore.utils.configuration;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;

public class Resources {

	private SkyblockCore plugin;
	private Resource config;
	private Resource blockvalues;
	private Resource messages;
	
	public Resources(SkyblockCore plugin) {
		this.plugin = plugin;
		
		this.config = new Resource((Plugin) this.plugin, "config.yml");
		this.blockvalues = new Resource((Plugin) this.plugin, "blockvalues.yml");
		this.messages = new Resource((Plugin) this.plugin, "messages.yml");
	}
	
	public void load() {
		this.config.load();
		this.blockvalues.load();
		this.messages.load();
	}
	
	public void save() {
		this.config.save();
		this.blockvalues.save();
		this.messages.save();
	}
	
	public String format(String key, Object... args) {
        return format(true, key, args);
    }

    public String format(boolean prefix, String key, Object... args) {
        String message = prefix ? this.messages.getString("Messages.General.Prefix") + this.messages.getString("Messages." + key) : this.messages.getString("Messages." + key);
        for (int i = 0; i < args.length; i++)
            message = message.replace("{" + i + "}", String.valueOf(args[i]));
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String prefix(String msg) {
        return ChatColor.translateAlternateColorCodes('&', this.messages.getString("Messages.General.Prefix") + msg);
    }
	
	public Resource getConfig() { return this.config; }
	public Resource getBlockValues() { return this.blockvalues; }
	public Resource getMessages() { return this.messages; }
}
