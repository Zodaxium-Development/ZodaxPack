package me.zodaxium.zodaxpack.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commandserver extends Command{

	ZodaxPack plugin;
	
	public Commandserver(ZodaxPack plugin){
		super("server", "bungeecord.command.server", new String[0]);
		this.plugin = plugin;
		plugin.getProxy().getPluginManager().registerCommand(plugin, this);
	}
	
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args){
		if(!(sender instanceof ProxiedPlayer)){ return; }
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if(args.length == 0){
		    p.sendMessage(new TextComponent(ChatColor.BLUE + "You are connected to: " + ChatColor.GOLD + ProxyServer.getInstance().getPlayer(p.getName()).getServer().getInfo().getName().substring(0, 1).toUpperCase() + ProxyServer.getInstance().getPlayer(p.getName()).getServer().getInfo().getName().substring(1)));
		    List<String> servernames = new ArrayList<String>();
		    for(ServerInfo server : plugin.servers.values()){
		    	 servernames.add(server.getName().substring(0, 1).toUpperCase() + server.getName().substring(1));
		    }
		    Collections.sort(servernames);
		    StringBuffer sb = new StringBuffer();
		    for(Iterator<String> it = servernames.iterator(); it.hasNext();){
		    	sb.append(it.next());
		    	if(it.hasNext()){
		    		sb.append(", ");
		    	}
		    }
		    String message = plugin.colorize("&9Servers: &6" + sb.toString());
		    p.sendMessage(message);
		}else{
			ServerInfo server = (ServerInfo) plugin.servers.get(args[0]);
			if(server == null){ p.sendMessage(new TextComponent(ProxyServer.getInstance().getTranslation("no_server", new Object[0]))); }
			else if(!server.canAccess(p)){ p.sendMessage(new TextComponent(ProxyServer.getInstance().getTranslation("no_server_permission", new Object[0]))); }
			else{ p.connect(server); }
		}
	}
}