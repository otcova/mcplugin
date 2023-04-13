package octova.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import octova.Msg;

public class Snake implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (!(commandSender instanceof Player)) {
            Msg.send(commandSender, "&cOnly Players can use this command.");
            return true;
        }

        Msg.send(commandSender, "&aHello :) !");
        return true;
    }
}
