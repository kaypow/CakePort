package com.gmail.marvinj91.CakePort;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class CakePortBlockListener extends BlockListener 
{
	private final CakePort plugin;
	
	public CakePortBlockListener(CakePort instance)
	{
		this.plugin = instance;
	}
	
	/*public void onBlockRightClick(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		
		if(block.getType() == Material.CAKE_BLOCK && player.getItemInHand().getType() == Material.BONE)
		{
			if(CakePort.Permissions.has(player, "cakeport.add") || player.isOp())
			{
				CakePort.setBlock(block);
				player.sendMessage(ChatColor.DARK_GREEN + "Cake selected.");
			}
		}
	}*/
	
	public void onBlockBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		Player player = event.getPlayer();
		String cakeName = "";
		
		if(Cakes.isCakeBlock(block))
		{
			if( CakePort.Permissions != null)
			{
				if(CakePort.Permissions.has(player, "cakeport.remove") || player.isOp() )
				{
					cakeName = Cakes.getName(block);
					Files.deleteCake(cakeName);
					player.sendMessage( ChatColor.RED + "Cake " + ChatColor.AQUA + cakeName + ChatColor.RED + " has been removed.");
				}else
				{
					player.sendMessage( ChatColor.DARK_RED + "You do not have permission to remove CakePorts.");
					event.setCancelled(true);
				}
			}else if ( player.isOp())
			{
				cakeName = Cakes.getName(block);
				Files.deleteCake(cakeName);
				player.sendMessage( ChatColor.RED + "Cake " + ChatColor.AQUA + cakeName + ChatColor.RED + " has been removed.");
			}else
			{
				player.sendMessage( ChatColor.DARK_RED + "You do not have permission to remove CakePorts.");
				event.setCancelled(true);
			}
			
		}
	}
}
