package octova.Addons.Snake;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import octova.utils.Msg;

public class SnakePlayer {
	public Player player;
	public SnakeTail tail;
	
	SnakeGame snakeGame;
	GameMode previousGamemode;
	boolean alive = true;

	SnakePlayer(SnakeGame snakeGame, Player player) {
		this.snakeGame = snakeGame;
		this.player = player;
		this.tail = new SnakeTail(this);
		this.previousGamemode = player.getGameMode();

		player.addPotionEffect(new PotionEffect(
				PotionEffectType.SPEED,
				1000000,
				1,
				false,
				false,
				false));
		player.addPotionEffect(new PotionEffect(
				PotionEffectType.SATURATION,
				1000000,
				2,
				false,
				false,
				false));
		player.setGameMode(GameMode.SURVIVAL);
		
		Location location = player.getLocation();
		location.setY(snakeGame.board_y + 1);
		player.teleport(location);
	}

	void tick() {
		pushPlayer();
		Vector head = currentHead();
		tail.go_to(head);
		if (!player.isOnline() || player.isDead())
			kill(new Vector(0, 0, 0), false);
	}

	public void kill(Vector pos) {
		kill(pos, true);
	}

	public void kill(Vector pos, boolean propagate) {
		var deathPlayers = new ArrayList<String>();
		deathPlayers.add(player.getDisplayName());
		death_delete();

		if (propagate) {
			// Kill also other players (in case you collided with a player head)
			for (SnakePlayer snake : snakeGame.players.values()) {
				if (!snake.tail.blocks.isEmpty()) {
					Vector head = snake.tail.blocks.peekLast();
					if (head.equals(pos)) {
						deathPlayers.add(snake.player.getDisplayName());
						snake.death_delete();
					}
				}
			}
		}

		String deadPersons = deathPlayers.remove(0);
		for (String name : deathPlayers) {
			deadPersons += ", " + name;
		}

		Msg.broadcast("[Snake Master] " + deadPersons + " died");
	}

	private void death_delete() {
		if (alive) {
			alive = false;
			tail.delete_with_loot();
			delete();
			snakeGame.leave(player);
			player.setGameMode(GameMode.SPECTATOR);
		}
	}

	void delete() {
		tail.delete();
		player.setGameMode(previousGamemode);
		player.removePotionEffect(PotionEffectType.SPEED);
		player.removePotionEffect(PotionEffectType.SATURATION);
	}

	private void pushPlayer() {
		var velocity = player.getVelocity();
		final double force = ((Entity) player).isOnGround() ? 0.3 : 0.15;
		
		// final double gravityMult = 1.3;
		// if (velocity.getY() > 0)
		//	 velocity.setY(velocity.getY() / gravityMult);

		Vector direction = player.getLocation().getDirection();
		direction.setY(0).normalize().multiply(force);
		velocity.add(direction);

		player.setVelocity(velocity);
	}

	private Vector currentHead() {
		Location location = player.getLocation();
		double pitch = (90 - location.getPitch()) * Math.PI / 180;
		double yaw = (location.getYaw()) * Math.PI / 180;

		double r = 1 + location.getY() - snakeGame.board_y;

		double vx = r * Math.tan(pitch * Math.sin(-yaw));
		double vz = r * Math.tan(pitch * Math.cos(yaw));

		double length = Math.sqrt(vx * vx + vz * vz);

		final var max_length = 6.0;

		if (length > max_length) {
			vx *= max_length / length;
			vz *= max_length / length;
		}

		// Fix in case Math.tan(...) = Infinite
		if (Math.abs(vx) > max_length) {
			vx = Math.sin(vx) * max_length;
			vz = 0;
		} else if (Math.abs(vz) > max_length) {
			vx = 0;
			vz = Math.sin(vz) * max_length;
		}
		// Msg.send(player, vx + ", " + vy);

		return new Vector(
				(int) (location.getX() + vx),
				snakeGame.board_y,
				(int) (location.getZ() + vz));
	}
}