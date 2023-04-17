package octova.Addons.Snake.Food;

import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Block;

import octova.Addons.Snake.SnakePlayer;

public class Food {
	
	public static final List<FoodType> food_list = Arrays.asList(
		new Apple(),
		new GoldenApple(),
		new EnchantedGoldenApple()
	);
	
	public static void add_food_type(FoodType foodType) {
		food_list.add(foodType);
	}
	
	public static boolean tryEat(SnakePlayer snake, Block block) {
		for (FoodType food : food_list) {
			if (food.tryEat(snake, block)) return true;
		}
		return false;
	}
}
