package me.zodaxium.zodaxpack.commands;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Commandreload extends Command{

	ZodaxPack plugin;
	
	public Commandreload(ZodaxPack plugin){
		super("zpreload", "zpack.reload", new String[0]);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args){
		try{
			plugin.reloadConfigFile();
			sender.sendMessage(ChatColor.GREEN + "Config reloaded successfully!");
		}catch(Exception e){
			sender.sendMessage(ChatColor.RED + "Config reload failed! Are there any illegal characters?");
		}
	}
}
