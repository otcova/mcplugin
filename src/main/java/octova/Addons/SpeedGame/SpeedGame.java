package octova.Addons.SpeedGame;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import octova.Addons.SpeedGame.commands.SpeedCommand;
import octova.utils.Msg;

// Incremental Speed Game
public class SpeedGame extends BukkitRunnable {
	
	public JavaPlugin plugin;
	
	final HashMap<Player, SpeedPlayer> players = new HashMap<>();
	static int tickSize = 10 * 20;
	
	public SpeedGame(JavaPlugin plugin) {
		this.plugin = plugin;
		runTaskTimer(plugin, tickSize, tickSize);
		
		new SpeedCommand(this);
	}
	
	@Override
	public void run() {
		for (SpeedPlayer player : players.values()) {
			player.tick();
		}
	}
	
	public void toggleJoin(Player player) {
		if (players.containsKey(player)) {
			leave(player);
		} else {
			players.put(player, new SpeedPlayer(player));
		}
	}
	
	public void leave(Player player) {
		players.remove(player).delete();
	}
	
	public String listPlayers() {
		if (players.isEmpty()) {
			return "There are no players";
		}

		String message = "Players:";
		for (Player player : players.keySet()) {
			message += "\n  - " + player.getDisplayName();
		}
		return message;
	}
}
