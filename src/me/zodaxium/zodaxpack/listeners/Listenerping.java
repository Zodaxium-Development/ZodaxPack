package me.zodaxium.zodaxpack.listeners;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Listenerping implements Listener{

	ZodaxPack plugin;
	
	public Listenerping(ZodaxPack plugin){
		plugin.getProxy().getPluginManager().registerListener(plugin, this);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onProxyPing(ProxyPingEvent e){
		ServerPing r = e.getResponse();
		r.setDescription((plugin.motd).replaceFirst("%n%", "\n"));
		r.getPlayers().setSample(plugin.playerlist);
		e.setResponse(r);
	}
}
