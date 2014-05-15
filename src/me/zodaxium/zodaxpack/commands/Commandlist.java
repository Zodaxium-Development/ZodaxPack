package me.zodaxium.zodaxpack.commands;

import java.util.ArrayList;
import java.util.Iterator;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commandlist extends Command{

	ZodaxPack plugin;
	
	public Commandlist(ZodaxPack plugin){
		super("glist", "zpack.list", new String[0]);
		this.plugin = plugin;
		plugin.getProxy().getPluginManager().registerCommand(plugin, this);
	}
	
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args){
		int online = 0;
		ArrayList<ServerInfo> serverinfo = new ArrayList<ServerInfo>();
		for(ServerInfo s : plugin.getProxy().getServers().values()){
			serverinfo.add(s);
		}
		
		for(ServerInfo s : serverinfo){
			if(s.canAccess(sender)){
				String servername = s.getName().toLowerCase();
				servername = (servername.substring(0, 1).toUpperCase() + servername.substring(1));
				if(s.getPlayers().size() == 1)
					servername = plugin.colorize("&9[" + servername + "]: &6" + s.getPlayers().size() + " player");
				else
					servername = plugin.colorize("&9[" + servername + "]: &6" + s.getPlayers().size() + " players");
				
				TextComponent comp = new TextComponent(servername);
				StringBuffer sb = new StringBuffer();
				for(Iterator<ProxiedPlayer> i = s.getPlayers().iterator(); i.hasNext();){
					sb.append(i.next());
			    	if(i.hasNext())
			    		sb.append(", ");
			    		online++;
				}
				
				if(s.getPlayers().size() != 0)
					comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW + sb.toString()).create()));
				else
					comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW + "No Players").create()));
				
				comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + s.getName()));
				sender.sendMessage(comp);
			}
		}
		sender.sendMessage(plugin.colorize("&6Total Online: &9" + online));
	}
}
