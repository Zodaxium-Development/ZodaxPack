package me.zodaxium.zodaxpack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import me.zodaxium.zodaxpack.commands.Commandalert;
import me.zodaxium.zodaxpack.commands.Commandfind;
import me.zodaxium.zodaxpack.commands.Commandg;
import me.zodaxium.zodaxpack.commands.Commandlist;
import me.zodaxium.zodaxpack.commands.Commandreload;
import me.zodaxium.zodaxpack.commands.Commands;
import me.zodaxium.zodaxpack.commands.Commandsend;
import me.zodaxium.zodaxpack.commands.Commandserver;
import me.zodaxium.zodaxpack.listeners.Listenerchat;
import me.zodaxium.zodaxpack.listeners.Listenerping;
import net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.FileConfiguration;
import net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.YamlConfiguration;
import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
import net.md_5.bungee.api.config.ServerInfo;

public class ZodaxPack extends ConfigurablePlugin{
	
	public String motd;
	public List<UUID> server;
	public PlayerInfo[] playerlist;
	public HashMap<String, ServerInfo> servers;
	
	public void onEnable(){
		saveDefaultConfig();
		
		generateVariables();
		
		new Commandalert(this);
		new Commandg(this);
		new Commandfind(this);
		new Commandlist(this);
		new Commandreload(this);
		new Commands(this);
		new Commandsend(this);
		new Commandserver(this);
		
		new Listenerchat(this);
		new Listenerping(this);
	}
	
	private void generateVariables(){
		motd = colorize(getConfig().getString("MOTD"));
		for(int i = 0; i < getConfig().getStringList("PlayerList").size(); i++){
			playerlist[i] = new PlayerInfo(colorize(getConfig().getStringList("PlayerList").get(i)), "");
		}
		server = new ArrayList<UUID>();
		servers = new HashMap<String, ServerInfo>();
		for(Entry<String, ServerInfo> server : getProxy().getServers().entrySet()){
			servers.put(server.getValue().getName().toLowerCase(), server.getValue());
		}
	}
	
	public String colorize(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public void reloadConfigFile() throws Exception{
		File file = new File(getDataFolder(), "config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		motd = colorize(config.getString("MOTD"));
		for(int i = 0; i < config.getStringList("PlayerList").size(); i++){
			playerlist[i] = new PlayerInfo(colorize(config.getStringList("PlayerList").get(i)), "");
		}
		server = new ArrayList<UUID>();
		servers = new HashMap<String, ServerInfo>();
		for(Entry<String, ServerInfo> server : getProxy().getServers().entrySet()){
			servers.put(server.getValue().getName().toLowerCase(), server.getValue());
		}
	}
}
