package octova.Addons.Snake.Food;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import octova.Addons.Snake.SnakePlayer;
import octova.utils.ItemFrame;

public class GoldenApple extends FoodType {

	static final List<Material> materials = Arrays.asList(
			Material.RED_MUSHROOM_BLOCK);

	public boolean tryEat(SnakePlayer snake, Block block) {
		if (materials.contains(block.getType())) {
			snake.tail.length += 5.0;
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
		block.setType(material);
		ItemFrame.place(block.getWorld(),
				new Vector(block.getX(), block.getY() + 1, block.getZ()),
				Material.GOLDEN_APPLE);
	}
}
