package octova.Addons.Snake;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import octova.Addons.Snake.commands.SnakeBoardCommand;
import octova.Addons.Snake.commands.SnakeCommand;
import octova.utils.Msg;

public class SnakeGame extends BukkitRunnable {

	public JavaPlugin plugin;

	final HashMap<Player, SnakePlayer> players = new HashMap<Player, SnakePlayer>();
	public final int board_y = 91;

	public SnakeGame(JavaPlugin plugin) {
		this.plugin = plugin;
		runTaskTimer(plugin, 1, 1);

		new SnakeBoardCommand(this);
		new SnakeCommand(this);
	}

	@Override
	public void run() {
		for (SnakePlayer player : players.values()) {
			player.tick();
		}
	}

	public void toggleJoin(Player player) {
		if (players.containsKey(player)) {
			leave(player);
		} else {
			players.put(player, new SnakePlayer(this, player));
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

	public void broadcast(String message) {
		for (SnakePlayer snake : players.values()) {
			Msg.send(snake.player, message);
		}
	}
}
