package octova.utils.ArgParser;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class ArgBlockLocation extends Argument {
	Location location;

	public ArgBlockLocation(String name) {
		super(name);
	}

	@Override
	public String typeName() {
		return "Location";
	}

	@Override
	public boolean parse(CommandSender sender, List<String> value) {
		if (value.size() < 3)
			return false;

		try {
			int x = Integer.parseInt(value.get(0));
			value.remove(0);

			int y = Integer.parseInt(value.get(0));
			value.remove(0);

			int z = Integer.parseInt(value.get(0));
			value.remove(0);

			World world = null;
			if (!value.isEmpty()) {
				world = Bukkit.getServer().getWorld(value.get(0));
				if (world != null) {
					value.remove(0);
				}
			}

			if (world == null) {
				if (sender instanceof Entity) {
					world = ((Entity) sender).getWorld();
				} else {
					return false;
				}
			}
			location = new Location(world, x, y, z);

		} catch (NumberFormatException ex) {
			return false;
		}

		return true;
	}

	public Location get() {
		return location;
	}
}
