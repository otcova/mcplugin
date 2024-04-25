package octova;

import octova.Addons.Snake.SnakeGame;
import octova.Addons.SpeedGame.SpeedGame;

import org.bukkit.plugin.java.JavaPlugin;

public class Octopus extends JavaPlugin {
    
    @Override
    public void onEnable() {
        new SnakeGame(this);
        new SpeedGame(this);
    }
}
