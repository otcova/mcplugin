package octova.Addons.Snake.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import octova.Addons.Snake.SnakeGame;
import octova.utils.Msg;

public class SnakeCommand implements CommandExecutor {
    SnakeGame snakeGame;

    public SnakeCommand(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
        var command = snakeGame.plugin.getCommand("snake");
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            snakeGame.toggleJoin((Player) commandSender);
        }
        
        Msg.send(commandSender, snakeGame.listPlayers());
        return true;
    }
}
