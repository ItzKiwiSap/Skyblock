package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.island;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.SkyblockRanks;

public class Size {

	private SkyblockCore plugin;
	private int size;
	
	public Size(SkyblockRanks rank, SkyblockCore plugin) {
		this.plugin = plugin;
		
		switch(rank) {
			case CLOUD:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Cloud.IslandSize");
				break;
			case HAIL:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Hail.IslandSize");
				break;
			case LIGHTNING:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Lightning.IslandSize");
				break;
			case PLAYER:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Default.IslandSize");
				break;
			case RAIN:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Rain.IslandSize");
				break;
			case SNOW:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Snow.IslandSize");
				break;
			case STAFF:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Lightning.IslandSize");
				break;
			case THUNDER:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Thunder.IslandSize");
				break;
			default:
				this.size = this.plugin.getResources().getConfig().getInt("Ranks.Default.IslandSize");
				break;
		}
	}
	
	public int getSize() { return this.size; }
}
