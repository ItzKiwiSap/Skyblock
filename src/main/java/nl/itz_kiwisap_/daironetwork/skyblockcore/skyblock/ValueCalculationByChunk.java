package nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.misc.Pair;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

import nl.itz_kiwisap_.daironetwork.skyblockcore.SkyblockCore;
import nl.itz_kiwisap_.daironetwork.skyblockcore.skyblock.objects.Island;

public class ValueCalculationByChunk {

	private SkyblockCore plugin;
	private Island island;
	private World world;
	private int maxchunks;
	private boolean checking = true;
	private BukkitTask task;
	
	private long newvalue;
	
	private Set<Pair<Integer, Integer>> chunksToScan;
	
	public ValueCalculationByChunk(SkyblockCore plugin, Island island) {
		this.plugin = plugin;
		this.island = island;
		this.world = this.plugin.getWorld().getWorld();
		this.maxchunks = (this.plugin.getResources().getConfig().getConfigurationSection("Calculation.MaxChunks") != null) ? this.plugin.getResources().getConfig().getInt("Calculation.MaxChunks") : 200;
		
		if(island == null) return;
		
		this.chunksToScan = getChunksToScan(island);
		this.checking = true;
		
		task = this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, () -> {
			if(this.island.getOwner() == null) {
				task.cancel();
				return;
			}
			
			Set<ChunkSnapshot> chunkSnapshot = new HashSet<>();
			if(checking) {
				Iterator<Pair<Integer, Integer>> iterator = this.chunksToScan.iterator();
				
				if(!iterator.hasNext()) {
					task.cancel();
					return;
				}
				
				while(iterator.hasNext() && chunkSnapshot.size() < this.maxchunks) {
					Pair<Integer, Integer> pair = iterator.next();
					
					if(!world.isChunkLoaded(pair.a, pair.b)) {
						world.loadChunk(pair.a, pair.b);
						chunkSnapshot.add(world.getChunkAt(pair.a, pair.b).getChunkSnapshot());
						world.unloadChunk(pair.a, pair.b);
					} else {
						chunkSnapshot.add(world.getChunkAt(pair.a, pair.b).getChunkSnapshot());
					}
					iterator.remove();
				}
				
				this.checking = false;
				checkChunkAsync(chunkSnapshot);
				this.island.setValue(this.newvalue);
			}
		}, 0L, 1L);
	}
	
	private void checkChunkAsync(Set<ChunkSnapshot> chunkSnapshot) {
		this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
			for(ChunkSnapshot chunk : chunkSnapshot) {
				scanChunk(chunk);
			}
			checking = true;
		});
	}
	
	private void scanChunk(ChunkSnapshot chunk) {
		for(int x = 0; x < 16; x++) {
			if(chunk.getX() * 16 + x < island.getMinX() || chunk.getX() * 16 + x >= island.getMinX() + island.getSize()) {
				continue;
			}
			for(int z = 0; z < 16; z++) {
				if(chunk.getZ() * 16 + z < island.getMinZ() || chunk.getZ() * 16 + z >= island.getMinZ() + island.getSize()) {
					continue;
				}
				
				for(int y = 0; y < island.getLocation().getWorld().getMaxHeight(); y++) {
					Material blockType = chunk.getBlockType(x, y, z);
					
					if(!blockType.equals(Material.AIR)) {
						checkBlock(blockType);
					}
				}
			}
		}
	}
	
	private void checkBlock(Material type) {
		List<Material> blocks = new ArrayList<>();
		for(String string : this.plugin.getResources().getBlockValues().getConfigurationSection("BlockValues").getKeys(false)) {
			blocks.add(Material.matchMaterial(string));
		}
		
		if(blocks.contains(type)) {
			for(String string : this.plugin.getResources().getBlockValues().getConfigurationSection("BlockValues").getKeys(false)) {
				long value = this.plugin.getResources().getBlockValues().getLong("BlockValues." + string);
				this.newvalue += value;
			}
		}
	}
	
	public Set<Pair<Integer, Integer>> getChunksToScan(Island island) {
		Set<Pair<Integer, Integer>> chunkSnapshot = new HashSet<>();
		for(int x = island.getMinX(); x < (island.getMinX() + island.getSize() + 16); x += 16) {
			for(int z = island.getMinZ(); z < (island.getMinZ() + island.getSize() + 16); z += 16) {
				Pair<Integer, Integer> pair = new Pair<>(world.getBlockAt(x, 0, z).getChunk().getX(), world.getBlockAt(x, 0, z).getChunk().getZ());
				chunkSnapshot.add(pair);
			}
		}
		return chunkSnapshot;
	}
}
