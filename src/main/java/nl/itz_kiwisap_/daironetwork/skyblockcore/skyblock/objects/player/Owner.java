package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.SkyblockRanks;

public class Owner {

	private Player player;
	private SkyblockRanks rank;
	
	public Owner(Player player, SkyblockRanks rank) {
		this.player = player;
		this.rank = rank;
	}
	
	public String getName() { return this.player.getName(); }
	public UUID getUniqueId() { return this.player.getUniqueId(); }
	public Player getPlayer() { return this.player; }
	public SkyblockRanks getRank() { return this.rank; }
}
