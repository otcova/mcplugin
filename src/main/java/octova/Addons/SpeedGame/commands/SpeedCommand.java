package octova.Addons.SpeedGame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import octova.Addons.SpeedGame.SpeedGame;
import octova.utils.Msg;

public class SpeedCommand implements CommandExecutor {
    SpeedGame speedGame;

    public SpeedCommand(SpeedGame speedGame) {
        this.speedGame = speedGame;
        var command = speedGame.plugin.getCommand("speed");
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            speedGame.toggleJoin((Player) commandSender);
        }
        
        Msg.send(commandSender, speedGame.listPlayers());
        return true;
    }
}