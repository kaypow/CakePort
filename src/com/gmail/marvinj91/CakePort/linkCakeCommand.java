package com.gmail.marvinj91.CakePort;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class linkCakeCommand implements CommandExecutor {
	
	private final CakePort plugin;
	
	public linkCakeCommand(CakePort plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg) {
		Player player = (Player)sender;
		
		if ((arg.length == 2) && (!plugin.anonymousCheck(sender)) && CakePort.CheckPermission(player, "cakeport.link")) {
			String fCake = arg[0];
			String sCake = arg[1];
			
			boolean doesExist1 = Files.containskey(fCake, CakePort.blocks);
			boolean doesExist2 = Files.containskey(sCake, CakePort.blocks);
			boolean isFirstLinked = Files.containskey(fCake, CakePort.links);
			boolean isSecondLinked = Files.containskey(sCake, CakePort.links);
			
			if(!doesExist1 || !doesExist2 ){
				if (!doesExist1)
					player.sendMessage(ChatColor.AQUA + fCake + ChatColor.RED + " does not exist.");
				if (!doesExist2)
					player.sendMessage(ChatColor.AQUA + sCake + ChatColor.RED + " does not exist.");
				
				return false;
			}
			
			if(isFirstLinked || isSecondLinked){
				if(isFirstLinked)
					player.sendMessage( ChatColor.AQUA + fCake + ChatColor.RED + " is already linked.");
				if(isSecondLinked)
					player.sendMessage(ChatColor.AQUA + sCake + ChatColor.RED + " is already linked.");
		
				return false;
			}
			
			Files.linkCakes(fCake, sCake, CakePort.links);
			player.sendMessage(ChatColor.GREEN + "Cakes linked.");
			
			
			
		}
		return false;
	}

}
