package octova.Addons.Snake;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;

import octova.Addons.Snake.Food.Apple;
import octova.Addons.Snake.Food.Food;
import octova.Addons.Snake.Food.GoldenApple;
import octova.Addons.Snake.Food.EnchantedGoldenApple;
import octova.utils.Line;
import octova.utils.VecBlock;

public class SnakeTail {
	ArrayDeque<Vector> blocks = new ArrayDeque<Vector>();
	public double length = 3;
	Material material = Material.RED_CONCRETE;
	Material eye_material = Material.STONE_BUTTON;
	ArrayList<Vector> eyes = new ArrayList<Vector>();
	SnakePlayer snake;

	SnakeTail(SnakePlayer snake) {
		this.snake = snake;
		List<Material> material_list = Arrays.asList(
				Material.RED_CONCRETE,
				Material.ORANGE_CONCRETE,
				Material.BLUE_CONCRETE,
				Material.PURPLE_CONCRETE,
				Material.BLACK_CONCRETE,
				Material.WHITE_CONCRETE,

				Material.RED_WOOL,
				Material.ORANGE_WOOL,
				Material.BLUE_WOOL,
				Material.PURPLE_WOOL,
				Material.BLACK_WOOL,
				Material.WHITE_WOOL);

		List<Material> eye_material_list = Arrays.asList(
				Material.OAK_BUTTON,
				Material.BIRCH_BUTTON,
				Material.STONE_BUTTON,
				Material.WARPED_BUTTON,
				Material.CRIMSON_BUTTON);

		Random rand = new Random();
		material = material_list.get(rand.nextInt(material_list.size()));
		eye_material = eye_material_list.get(rand.nextInt(eye_material_list.size()));
	}

	void go_to(Vector position) {
		expand_head(position);
		cut_to_length();
	}
	
	void delete_with_loot() {
		remove_eyes();
		for (Vector coord : blocks) {
			Block block = clear_block(coord);

			Random random = new Random();
			if (random.nextInt(50) == 0) {
				EnchantedGoldenApple.spawn(block);
			} else if (random.nextInt(10) == 0) {
				GoldenApple.spawn(block);
			}
		}
		blocks.clear();
	}

	void delete() {
		remove_eyes();
		for (Vector coord : blocks) {
			clear_block(coord);
		}
		blocks.clear();
	}

	/** Returns false if death */
	private boolean add_block(Vector head) {
		remove_eyes();
		
		Block block = VecBlock.at(snake.player.getWorld(), head);
		if (block.getType() != Material.BARRIER) {
			if (!blocks.contains(head)) {
				if (!Food.tryEat(this.snake, block)) {
					return false;
				}
			}
		}
		block.setType(material);

		if (!blocks.isEmpty()) {
			Vector past_head = blocks.getLast();
			blocks.add(head);

			if (past_head.getX() == head.getX()) {
				draw_eye(new Vector(+1, 0, 0));
				draw_eye(new Vector(-1, 0, 0));
			} else if (past_head.getZ() == head.getZ()) {
				draw_eye(new Vector(0, 0, 1));
				draw_eye(new Vector(0, 0, -1));
			} else {
				var vx = Math.max(-1, Math.min(1, head.getX() - past_head.getX()));
				draw_eye(new Vector(vx, 0, 0));

				var vz = Math.max(-1, Math.min(1, head.getZ() - past_head.getZ()));
				draw_eye(new Vector(0, 0, vz));
			}
		} else {
			blocks.add(head);
		}

		return true;
	}

	private void remove_eyes() {
		for (Vector coord : eyes) {
			Block block = VecBlock.at(snake.player.getWorld(), coord);
			if (block.getType() == eye_material) {
				block.setType(Material.BARRIER);
			}
		}
	}

	private void draw_eye(Vector offset) {
		Vector head = blocks.getLast();
		Vector coord = head.clone().add(offset);
		
		Block block = VecBlock.at(snake.player.getWorld(), coord);
		if (block.getType() == Material.BARRIER) {
			eyes.add(coord);
			String faceing;

			if (offset.getX() == 0) {
				if (offset.getZ() > 0)
					faceing = "south";
				else
					faceing = "north";
			} else {
				if (offset.getX() > 0)
					faceing = "east";
				else
					faceing = "west";
			}
			BlockData button = eye_material.createBlockData("[facing=" + faceing + "]");
			block.setBlockData(button);
		}
	}

	private Block clear_block(Vector coord) {
		Block block = VecBlock.at(snake.player.getWorld(), coord);
		block.setType(Material.BARRIER);
		return block;
	}

	private void expand_head(Vector coord) {
		if (blocks.isEmpty()) {
			if (!add_block(coord))
				snake.kill(coord);
			return;
		}

		Vector current_head = blocks.peekLast();
		if (!current_head.equals(coord)) {
			for (Vector pos : Line.blocksInBetween(current_head, coord)) {
				if (!add_block(pos)) {
					snake.kill(pos);
					return;
				}
			}
			if (!add_block(coord))
				snake.kill(coord);
		}
	}

	private void cut_to_length() {
		while (!blocks.isEmpty() && blocks.size() > length) {
			Vector removed = blocks.remove();
			if (!blocks.contains(removed)) {

				Block block = clear_block(removed);

				Random random = new Random();
				if (random.nextInt(50) == 0) {
					Apple.spawn(block);
				}
			}
		}
	}
}
