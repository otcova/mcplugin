package octova.utils.ArgParser;

import java.util.List;

import org.bukkit.command.CommandSender;

public class ArgInt extends Argument {
	int value;

	public ArgInt(String name) {
		super(name);
	}

	@Override
	public String typeName() {
		return "Integer";
	}

	@Override
	public boolean parse(CommandSender sender, List<String> value) {
		if (value.isEmpty()) return false;
		
		try {
			this.value = Integer.parseInt(value.get(0));
			value.remove(0);
		} catch (NumberFormatException ex) {
			return false;
		}
		
		return true;
	}
	
	public static boolean canParse(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	public int get() {
		return value;
	}
}
