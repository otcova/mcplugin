package octova.Addons.Snake.Board;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import octova.Addons.Snake.SnakeGame;
import octova.Addons.Snake.Food.Apple;

public class SnakeBoard {

	SnakeGame snakeGame;
	Location location;
	int radius;
	SimplexNoiseGenerator simplex;
	int vx;

	public SnakeBoard(SnakeGame snakeGame, Location center, int size) {
		this.snakeGame = snakeGame;
		this.radius = size / 2;
		this.location = center;

		this.vx = -radius;

		Random random = new Random();
		simplex = new SimplexNoiseGenerator(random);

		fill();
	}

	private void fill() {
		int sqRadius = radius * radius;

		for (int i = 0; i < 5 && vx <= radius; ++i, ++vx) {
			for (int vz = -radius; vz <= radius; ++vz) {
				int sqLength = vx * vx + vz * vz;
				if (sqLength <= sqRadius) {
					placeBlock(vx, vz);
				}
			}
		}

		if (vx <= radius) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(
					snakeGame.plugin, () -> fill(), 1);
		}
	}

	private void placeBlock(int vx, int vz) {
		final double heightNoiseScale = 50.;
		double heightNoise = simplex.noise(vx / heightNoiseScale, vz / heightNoiseScale);

		int height;
		if (heightNoise < 0.3) {
			height = 0;
		} else if (heightNoise < 0.5) {
			height = 1;
		} else if (heightNoise < 0.85) {
			height = 2;
		} else if (heightNoise < 0.9) {
			height = 3;
		} else if (heightNoise < 0.95) {
			height = 4;
		} else {
			height = 5;
		}

		final double spikeAreaNoiseScale = 20.;
		double spikeAreaNoise = simplex.noise(vx / spikeAreaNoiseScale, vz / spikeAreaNoiseScale, 1e4);
		if (spikeAreaNoise > 0.4) {
			final double spikeNoiseScale = 4.;
			double spikeSize = simplex.noise(vx / spikeNoiseScale, vz / spikeNoiseScale, 1e6);
			if (spikeSize < 0.3) {
				height = 0;
			} else if (spikeSize < 0.5) {
				height = 1;
			} else if (spikeSize < 0.85) {
				height = 2;
			} else {
				height = 3;
			}
		}

		Material material = Material.GRASS_BLOCK;
		if (height > 0) {
			final double materialNoiseScale = 10.;
			double materialNoise = simplex.noise(vx / materialNoiseScale, vz / materialNoiseScale, 1e2);
			if (materialNoise < -0.7)
				material = Material.STONE;
			else if (materialNoise < 0.3)
				material = Material.COBBLESTONE;
			else if (materialNoise < 0.6)
				material = Material.MOSSY_COBBLESTONE;
			else if (materialNoise < 0.8)
				material = Material.GRASS_BLOCK;
			else
				material = Material.MOSS_BLOCK;
		}

		int x = location.getBlockX() + vx;
		int z = location.getBlockZ() + vz;
		int y = height + location.getBlockY() - 5;
		
		World world = location.getWorld();
		world.getBlockAt(x, y++, z).setType(material);
		world.getBlockAt(x, y++, z).setType(material);
		world.getBlockAt(x, y++, z).setType(material);
		world.getBlockAt(x, y++, z).setType(material);
		if (height == 0) {
			final double foodNoiseScale = 5.;
			double foodNoise = simplex.noise(vx / foodNoiseScale, vz / foodNoiseScale, 1e1);
			Block block = world.getBlockAt(x, y++, z);
			if (foodNoise < -0.8) {
				Apple.spawn(block);
			} else {
				block.setType(Material.BARRIER);
			}
		}
		while (y < location.getBlockY() + 5)
			world.getBlockAt(x, y++, z).setType(Material.AIR);

	}
}
