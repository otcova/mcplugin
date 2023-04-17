package octova.Addons.Snake.Food;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import octova.Addons.Snake.SnakePlayer;
import octova.utils.ItemFrame;

public class EnchantedGoldenApple extends FoodType {

	static final List<Material> materials = Arrays.asList(
			Material.GOLD_BLOCK);

	public boolean tryEat(SnakePlayer snake, Block block) {
		if (materials.contains(block.getType())) {
			snake.tail.length += 10.0;
			ItemFrame.remove(block.getWorld(), new Vector(
					block.getX(), block.getY() + 1, block.getZ()));
			block.setType(Material.BARRIER);

			snake.player.addPotionEffect(new PotionEffect(
					PotionEffectType.SPEED,
					20 * 20,
					25,
					false,
					false,
					false));

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
				Material.ENCHANTED_GOLDEN_APPLE);
	}
}
