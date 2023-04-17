package octova.utils.ArgParser;

import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class Argument {
	protected String name;
	
	public Argument(String name) {
		this.name = name;
	}
	
	/** Return true if success */
	public abstract boolean parse(CommandSender sender, List<String> strArgs);
	
	public abstract String typeName();
	
	public String argumentName() {
		return name;
	}
	
	public String toString() {
		return "<" + typeName() + " " + argumentName() + ">";
	}
	
	
}
