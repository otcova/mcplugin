package octova.Addons.Snake.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import octova.Addons.Snake.Board.SnakeBoard;
import octova.Addons.Snake.SnakeGame;
import octova.utils.TabComplete;
import octova.utils.ArgParser.ArgParser;
import octova.utils.ArgParser.Argument;
import octova.utils.ArgParser.ArgBlockLocation;
import octova.utils.ArgParser.ArgInt;

public class SnakeBoardCommand implements CommandExecutor, TabCompleter {

    SnakeGame snakeGame;

    public SnakeBoardCommand(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
        var command = snakeGame.plugin.getCommand("snakeboard");
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] strArgs) {
        var size = new ArgInt("size");
        var centerLocation = new ArgBlockLocation("center");
        
        Argument[] args = { size, centerLocation };
        
        if (!ArgParser.parse(sender, alias, new ArrayList<>(Arrays.asList(strArgs)), args)) return true;
        
        new SnakeBoard(snakeGame, centerLocation.get(), size.get());        
        
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length >= 1 && ArgInt.canParse(args[0]) && sender instanceof Player) {
            Location loc = ((Player) sender).getLocation();
            list.add(args[0] + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
        }

        return TabComplete.filter(list, args);
    }
}
