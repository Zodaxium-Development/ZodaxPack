package me.zodaxium.zodaxpack.commands;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commandalert extends Command{

	ZodaxPack plugin;
	
	public Commandalert(ZodaxPack plugin){
		super("alert", "zpack.alert", new String[0]);
		this.plugin = plugin;
		plugin.getProxy().getPluginManager().registerCommand(plugin, this);
	}
	
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args){
		if(args.length == 0){ sender.sendMessage(new TextComponent(ChatColor.RED + "You must supply a message.")); }
		else{
			StringBuilder builder = new StringBuilder();
			for(String s : args){
				builder.append(s + " ");
			}
			String message = plugin.colorize("&8[&5Alert&8] &7" + builder.substring(0, builder.length()));
			for(ProxiedPlayer player : plugin.getProxy().getPlayers()){
				player.sendMessage(message);
			}
		}
	}
}