package octova.Addons.SpeedGame;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPlayer {
	int speed = 0;
	Player player;
	
	public SpeedPlayer(Player player) {
		this.player = player;
	}
	
	public void tick() {
		player.addPotionEffect(new PotionEffect(
				PotionEffectType.SPEED,
				SpeedGame.tickSize + 1,
				speed,
				false,
				false,
				false));
		++speed;
	}
	
	public void delete() {
		player.removePotionEffect(PotionEffectType.SPEED);
	}
}
