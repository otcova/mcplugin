package octova;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msg {
    public static void send(CommandSender sender, String message) {
        send(sender, message, '&');
    }

    public static void send(CommandSender sender, String message, char prefix) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes(prefix, message));
    }
}
