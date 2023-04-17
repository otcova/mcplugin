package octova.Addons.Snake.Food;

import org.bukkit.block.Block;

import octova.Addons.Snake.SnakePlayer;

abstract class FoodType {
	/** Returns true if the block has been eaten */
	public abstract boolean tryEat(SnakePlayer snake, Block block);
}
