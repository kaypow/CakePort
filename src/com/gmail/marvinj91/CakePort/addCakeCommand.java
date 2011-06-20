package com.gmail.marvinj91.CakePort;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class addCakeCommand implements CommandExecutor {
	private final CakePort plugin;
	
	public addCakeCommand(CakePort plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg) {
		Player player = (Player)sender;
		
		if((arg.length == 1) && (!plugin.anonymousCheck(sender)) && CakePort.CheckPermission(player, "cakeport.add")){
			String name = arg[0];
			Block block = CakePortPlayerListener.SelectBlock.get(player);
			
			if(block == null)
			{
				player.sendMessage(ChatColor.RED + "Cake has not been selected.");
				return false;
			}
			
			boolean cakeExist = Files.containskey(name, CakePort.blocks);
			if(!cakeExist)
			{
				if (Files.write(name, block, CakePort.blocks))
				{
				Files.CakeBlock.put(name, block);
				player.sendMessage(ChatColor.GREEN + "Cake " + ChatColor.AQUA + name + ChatColor.GREEN + " has been added.");
				CakePortPlayerListener.SelectBlock.put(player, null);
				}else
				{
					player.sendMessage("Could not save CakePort.");
				}
			}
			else
				player.sendMessage(ChatColor.AQUA + name + ChatColor.RED + " already exists.");
		}
		
		return false;
	}

}
