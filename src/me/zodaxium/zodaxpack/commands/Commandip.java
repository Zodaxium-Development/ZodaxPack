package me.zodaxium.zodaxpack.commands;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commandip extends Command{

	ZodaxPack plugin;
	
	public Commandip(ZodaxPack plugin){
		super("ip", "zpack.ip", new String[0]);
		this.plugin = plugin;
		plugin.getProxy().getPluginManager().registerCommand(plugin, this);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 1){
			if(sender instanceof ProxiedPlayer){
				ProxiedPlayer p = (ProxiedPlayer) sender;
				String ip = p.getAddress().toString().replaceFirst("/", "");
				sender.sendMessage(new TextComponent(plugin.colorize("&9Your Ip: &6" + ip)));
			}
		}else{
			ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
			if(p == null || p.getServer() == null) 
				sender.sendMessage(new TextComponent(plugin.colorize("&cPlayer not found!")));
			else{
				String ip = p.getAddress().toString().replaceFirst("/", "");
				sender.sendMessage(new TextComponent(plugin.colorize("&9" + p.getName() + "'s Ip: &6" + ip)));
			}
		}
	}
}
