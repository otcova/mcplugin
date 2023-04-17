package octova.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Msg {
    public static void error(CommandSender sender, String message) {
        send(sender, "&c" + message, '&');
    }
    
    public static void send(CommandSender sender, String message) {
        send(sender, message, '&');
    }

    public static void send(CommandSender sender, String message, char prefix) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes(prefix, message));
    }
    
    public static void broadcast(String message) {
        send(Bukkit.getServer().getConsoleSender(), message);
        for (Player player : Bukkit.getOnlinePlayers()) {
            send(player, message);
        }
    }
}
