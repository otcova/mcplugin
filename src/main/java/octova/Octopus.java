package octova;

import octova.commands.Snake;
import org.bukkit.plugin.java.JavaPlugin;

public class Octopus extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("snake").setExecutor(new Snake());
    }

    @Override
    public void onDisable() {
    }
}
