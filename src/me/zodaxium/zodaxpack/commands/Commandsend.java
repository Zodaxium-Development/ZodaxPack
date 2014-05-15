package me.zodaxium.zodaxpack.commands;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commandsend extends Command{

	ZodaxPack plugin;
	
	public Commandsend(ZodaxPack plugin){
		super("send", "zpack.send", new String[0]);
		this.plugin = plugin;
		plugin.getProxy().getPluginManager().registerCommand(plugin, this);
	}
	
	public void execute(CommandSender sender, String[] args){
		if(args.length != 2){
			sender.sendMessage(new TextComponent(plugin.colorize("Usage: /send <Player|All> <Server>")));
			return;
	    }
		
	    ServerInfo target = ProxyServer.getInstance().getServerInfo(args[1]);
	    if(target == null){
	    	sender.sendMessage(new TextComponent(ProxyServer.getInstance().getTranslation("no_server", new Object[0])));
	    	return;
	    }
	    
	    if(args[0].equalsIgnoreCase("all")){
	    	for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
	    		summon(p, target, sender);
	    	}
	    	
	    }else{
	    	ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
	    	if (player == null){
	    		sender.sendMessage(new TextComponent(plugin.colorize("&cPlayer not found!")));
	    		return;
	    	}
	    	summon(player, target, sender);
	    }
	    sender.sendMessage(new TextComponent(plugin.colorize("&aPlayer(s) successfully summoned!")));
	}
	  
	private void summon(ProxiedPlayer player, ServerInfo target, CommandSender sender){
		if((player.getServer() != null) && (!player.getServer().getInfo().equals(target))){
			player.connect(target);
	        player.sendMessage(new TextComponent(plugin.colorize("&9Summoned to &6" + target.getName().substring(0, 1).toUpperCase() + target.getName().substring(1) + " &9by &6" + sender.getName())));
	    }
	}
}
