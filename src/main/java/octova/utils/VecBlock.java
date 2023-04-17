package octova.utils;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class VecBlock {
	public static Block at(World world, Vector pos) {
		return world.getBlockAt(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
	}
}
