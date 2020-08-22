package io.github.Deficuet.CactusWrench;

import io.github.Deficuet.CactusWrench.mainFunctions.MainHandOnUse;
import io.github.Deficuet.CactusWrench.mainFunctions.OffHandOnUse;
import io.github.Deficuet.CactusWrench.mainFunctions.DispenserOnTrigger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class CactusWrench extends JavaPlugin {
	private String[] subCmds = {"main-hand", "off-hand", "dispenser"};
	private String[] subCmdsToggle = {"true", "false"};
	public Map<String, String> TextMap = new HashMap<>();
	@Override
	public void onEnable() {
		TextMap.put("true", "enabled.");
		TextMap.put("false", "disabled.");
		TextMap.put("main-hand", "Main-hand");
		TextMap.put("off-hand", "Off-hand");
		TextMap.put("dispenser", "Dispenser");
		this.saveDefaultConfig();
		new MainHandOnUse(this);
		new OffHandOnUse(this);
		new DispenserOnTrigger(this);
		getLogger().info("CactusWrench Enabled!");
	}
	@Override
	public void onDisable() {
		this.saveConfig();
		getLogger().info("CactusWrench Disabled!");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("cactuswrench")) {
			if (args.length == 0) {
				return false;
			}
			if (Arrays.asList(subCmds).contains(args[0])) {
				if (args.length == 1) {
					showStatus(sender, args[0]);
					return true;
				} else if (args.length == 2) {
					if (Arrays.asList(subCmdsToggle).contains(args[1])) {
						if (!(Boolean.parseBoolean(args[1]) ^ this.getConfig().getBoolean(String.format("functions.%s", args[0])))) {
							showStatus(sender, args[0]);
							return true;
						} else {
							sender.sendMessage(String.format("[CactusWrench] %s wrench %s", TextMap.get(args[0]), TextMap.get(args[1])));
							this.getConfig().set(String.format("functions.%s", args[0]), Boolean.parseBoolean(args[1]));
							return true;
						}
					} else {
						showStatus(sender, args[0]);
						return true;
					}
				}
			}
		}
		return false;
	}
	public void showStatus(CommandSender sender, String wrench) {
		sender.sendMessage(String.format("[CactusWrench] %s wrench has been set to %s.", TextMap.get(wrench), Boolean.toString(this.getConfig().getBoolean(String.format("functions.%s", wrench)))));
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length > 2) {
			return new ArrayList<>();
		}
		if (args.length == 1) {
			return Arrays.asList(subCmds);
		}
		if (args.length == 2) {
			return Arrays.asList(subCmdsToggle);
		}
		return Arrays.stream(subCmds).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
	}
}