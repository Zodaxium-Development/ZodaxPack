package me.zodaxium.zodaxpack.commands;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commandfind extends Command{

	ZodaxPack plugin;
	
	public Commandfind(ZodaxPack plugin){
		super("find", "zpack.find", new String[0]);
		this.plugin = plugin;
		plugin.getProxy().getPluginManager().registerCommand(plugin, this);
	}
	
	public void execute(CommandSender sender, String[] args){
		if(args.length < 1){ sender.sendMessage(new TextComponent(plugin.colorize("&aUsage: /find (Player)"))); }
		else{
			ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
			if(p == null || p.getServer() == null) 
				sender.sendMessage(new TextComponent(plugin.colorize("&cPlayer not found!")));
			else
				sender.sendMessage(new TextComponent(plugin.colorize("&6" + args[0] + " &9is online at: &6" + p.getServer().getInfo().getName().substring(0, 1).toUpperCase() + p.getServer().getInfo().getName().substring(1))));
		}
	}
}
