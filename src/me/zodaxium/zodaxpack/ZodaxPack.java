package me.zodaxium.zodaxpack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import me.zodaxium.zodaxpack.commands.Commandalert;
import me.zodaxium.zodaxpack.commands.Commandfind;
import me.zodaxium.zodaxpack.commands.Commandg;
import me.zodaxium.zodaxpack.commands.Commandlist;
import me.zodaxium.zodaxpack.commands.Commands;
import me.zodaxium.zodaxpack.commands.Commandsend;
import me.zodaxium.zodaxpack.commands.Commandserver;
import me.zodaxium.zodaxpack.listeners.Listenerchat;
import me.zodaxium.zodaxpack.listeners.Listenerping;
import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;

public class ZodaxPack extends ConfigurablePlugin{
	
	public String motd;
	public List<UUID> server;
	public List<String> playerlist;
	public HashMap<String, ServerInfo> servers;
	
	public void onEnable(){
		saveDefaultConfig();
		
		generateVariables();
		
		new Commandalert(this);
		new Commandg(this);
		new Commandfind(this);
		new Commandlist(this);
		new Commands(this);
		new Commandsend(this);
		new Commandserver(this);
		
		new Listenerchat(this);
		new Listenerping(this);
	}
	
	private void generateVariables(){
		motd = colorize(getConfig().getString("MOTD"));
		playerlist = colorize(getConfig().getStringList("PlayerList"));
		server = new ArrayList<UUID>();
		servers = new HashMap<String, ServerInfo>();
		for(Entry<String, ServerInfo> server : getProxy().getServers().entrySet()){
			servers.put(server.getValue().getName().toLowerCase(), server.getValue());
		}
	}
	
	public String colorize(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public List<String> colorize(List<String> message){
		List<String> list = new ArrayList<String>();
		for(String msg : message){
			msg = ChatColor.translateAlternateColorCodes('&', msg);
			list.add(msg);
		}
		return list;
	}
}
