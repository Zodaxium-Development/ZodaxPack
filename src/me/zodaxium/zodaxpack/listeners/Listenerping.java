package me.zodaxium.zodaxpack.listeners;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
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
		r.setDescription(plugin.motd);
		PlayerInfo[] players = new PlayerInfo[plugin.playerlist.size()];
		for(int i = 0; i < plugin.playerlist.size(); i++){
            players[i] = new PlayerInfo(plugin.playerlist.get(i), "");
        }
		r.getPlayers().setSample(players);
		e.setResponse(r);
	}
}
