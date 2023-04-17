package octova.utils.ArgParser;

import java.util.List;

import org.bukkit.command.CommandSender;

import octova.utils.Msg;

public class ArgParser {
	public ArgParser() {

	}

	public static boolean parse(CommandSender sender, String alias, List<String> strArgs, Argument[] args) {
		for (Argument arg : args) {
			if (!arg.parse(sender, strArgs)) {
				if (strArgs.isEmpty()) {
					Msg.error(sender, "Invalid Arguments");
				} else {
					Msg.error(sender, "Invalid Argument \"" + strArgs.get(0) + '"');
				}
				Msg.send(sender, usage(alias, args));
				return false;
			}
		}

		if (!strArgs.isEmpty()) {
			Msg.error(sender, "Command /" + alias + " requires less arguments");
			Msg.send(sender, usage(alias, args));
			return false;
		}

		return true;
	}

	static String usage(String alias, Argument[] args) {
		String usage = "";
		for (Argument arg : args) {
			usage += " " + arg.toString();
		}
		return "Usage: /" + alias + usage;
	}
}
