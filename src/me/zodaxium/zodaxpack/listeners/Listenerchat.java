package me.zodaxium.zodaxpack.listeners;

import java.util.Iterator;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@SuppressWarnings("deprecation")
public class Listenerchat implements Listener{

	ZodaxPack plugin;
	
	public Listenerchat(ZodaxPack plugin){
		this.plugin = plugin;
		plugin.getProxy().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void onPlayerChat(ChatEvent e){
		if(e.isCancelled()) return;
		if(e.getSender() instanceof ProxiedPlayer){
			String message = e.getMessage();
			ProxiedPlayer p = (ProxiedPlayer) e.getSender();
			if(message.startsWith("@") && p.hasPermission("adminchat")){
				handleAdminChat(e, p, message);
				return;
			}else if(e.isCommand()){
				handleCommand(e, p, message);
				return;
			}else if(plugin.server.contains(p.getUniqueId())){
				handleServerChat(e, p, message);
				return;
			}else{
				handleGlobalChat(e, p, message);
			}
		}
	}
	
	/* Server Switch Cmd */
	private void handleCommand(ChatEvent e, ProxiedPlayer p, String message){
		String[] args = message.split(" ");
		if(!(args.length < 1)){
			if(plugin.servers.containsKey(args[0].toLowerCase())){
				p.connect(plugin.servers.get(args[0].toLowerCase()));
			}
		}
		return;
	}
	
	/* Admin Chat */
	private void handleAdminChat(ChatEvent e, ProxiedPlayer p, String message){
		e.setCancelled(true);
		if(message.equals("@")) return;
		for(ProxiedPlayer player : plugin.getProxy().getPlayers()){
			if(player.hasPermission("adminchat")){
				player.sendMessage(plugin.colorize("&8[&a@&8] &a&o" + p.getName() + "&7: " + message.replaceFirst("@", "")));
			}
		}
	}

	/* Server Chat */
	private void handleServerChat(ChatEvent e, ProxiedPlayer p, String message){
		e.setCancelled(true);
		Iterator<ProxiedPlayer> i = p.getServer().getInfo().getPlayers().iterator();
		while(i.hasNext()){
			ProxiedPlayer player = i.next();
			player.sendMessage(plugin.colorize("&e[S]&8" + p.getName() + "&7: " + message));	
		}
	}
	
	/* Global Chat */
	private void handleGlobalChat(ChatEvent e, ProxiedPlayer p, String message){
		e.setCancelled(true);
		for(ProxiedPlayer player : plugin.getProxy().getPlayers()){
			player.sendMessage(plugin.colorize("&8" + p.getName() + "&7: " + message));
		}
	}
}
