package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.island;

import java.io.File;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.SkyblockRanks;

public class Schematica {

	private File file;
	
	public Schematica(SkyblockRanks rank) {
		switch(rank) {
			case CLOUD:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Cloud_Island.schematic");
				break;
			case HAIL:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Hail_Island.schematic");
				break;
			case LIGHTNING:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Lightning_Island.schematic");
				break;
			case RAIN:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Rain_Island.schematic");
				break;
			case SNOW:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Snow_Island.schematic");
				break;
			case THUNDER:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Thunder_Island.schematic");
				break;
			case STAFF:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Lightning_Island.schematic");
				break;
			case PLAYER:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Default_Island.schematic");
				break;
			default:
				this.file = new File(SkyblockCore.getInstance().getDataFolder() + File.separator + "schematics", "Default_Island.schematic");
				break;
		}
	}
	
	public File getSchematicFile() { return this.file; }
}
