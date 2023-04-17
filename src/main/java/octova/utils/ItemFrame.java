package octova.utils;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.tr7zw.nbtapi.NBT;

public class ItemFrame {
	public static void remove(World world, Vector position) {
		var itemframe = get(world, position);
		if (itemframe != null) {
			itemframe.remove();
		}
	}

	public static void place(World world, Vector position, Material item) {
		Location location = new Location(world,
		position.getBlockX(), position.getBlockY(), position.getBlockZ());
		
		var frame = world.spawn(location, org.bukkit.entity.ItemFrame.class);
		NBT.modify(frame, nbt -> {
			nbt.setBoolean("Invisible", true);
			nbt.setBoolean("Fixed", true);
		});
		frame.setItem(new ItemStack(item));
		frame.setInvulnerable(true);
		frame.setFacingDirection(BlockFace.UP, true);
		
		final var rotations = Rotation.values();
		final var random = new Random();
		frame.setRotation(rotations[random.nextInt(rotations.length)]);
	}

	static org.bukkit.entity.ItemFrame get(World world, Vector position) {
		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();
		Location location = new Location(world, x, y, z);
		for (Entity e : location.getChunk().getEntities()) {
			if (e instanceof org.bukkit.entity.ItemFrame) {
				Location loc = e.getLocation();
				if (loc.getWorld() == world && loc.getBlockX() == x
						&& loc.getBlockY() == y && loc.getBlockZ() == z) {
					return (org.bukkit.entity.ItemFrame) e;
				}
			}
		}
		return null;
	}
}
