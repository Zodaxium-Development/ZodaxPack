package me.zodaxium.zodaxpack.commands;

import me.zodaxium.zodaxpack.ZodaxPack;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commandg extends Command{

	ZodaxPack plugin;
	
	public Commandg(ZodaxPack plugin){
		super("g", "zpack.channel", new String[0]);
		this.plugin = plugin;
		plugin.getProxy().getPluginManager().registerCommand(plugin, this);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) return;
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if(plugin.server.contains(p.getUniqueId())){
			plugin.server.remove(p.getUniqueId());
		}
		p.sendMessage(plugin.colorize("&9Channel: &6Global"));
	}
}