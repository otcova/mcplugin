package octova.Addons.Snake.Food;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;

import octova.Addons.Snake.SnakeGame;
import octova.Addons.Snake.SnakePlayer;
import octova.Addons.Snake.SnakeTail;
import octova.utils.ItemFrame;
import octova.utils.Msg;

public class Apple extends FoodType {
	
	static final List<Material> materials = Arrays.asList(
		Material.OAK_LEAVES,
		Material.SPRUCE_LEAVES,
		Material.BIRCH_LEAVES,
		Material.JUNGLE_LEAVES,
		Material.ACACIA_LEAVES,
		Material.DARK_OAK_LEAVES,
		Material.MANGROVE_LEAVES,
		Material.AZALEA_LEAVES,
		Material.FLOWERING_AZALEA_LEAVES
	);
	
	public boolean tryEat(SnakePlayer snake, Block block) {
		if (materials.contains(block.getType())) {
			snake.tail.length += 1.0;
			ItemFrame.remove(block.getWorld(), new Vector(
				block.getX(), block.getY() + 1, block.getZ()));
			block.setType(Material.BARRIER);
			return true;
		}
		return false;
	}
	
	static public void spawn(Block block) {
		final Random random = new Random();
		var material = materials.get(random.nextInt(materials.size()));
		BlockData leave = material.createBlockData("[persistent=true]");
		block.setBlockData(leave);
		
		ItemFrame.place(block.getWorld(),
			new Vector(block.getX(), block.getY() + 1, block.getZ()),
			Material.APPLE);
	}
}
