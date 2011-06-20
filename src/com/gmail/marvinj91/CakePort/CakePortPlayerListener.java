package com.gmail.marvinj91.CakePort;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CakePortPlayerListener extends PlayerListener
{
	private final CakePort plugin;
	public static Map<Player, Boolean> hasTeleported = new HashMap<Player, Boolean>();
	public static Map<Player, Block> SelectBlock = new HashMap<Player, Block>();
	
	public CakePortPlayerListener(CakePort plugin)
	{
		this.plugin = plugin;
	}
	
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK )
		{
			Block block = event.getClickedBlock();
			boolean isCakePort = Files.CakeBlock.containsValue(block);
			
			//Allow block / Item in hand here - May make config for any block
			if(player.getItemInHand().getType() == Material.BONE && block.getType() == Material.CAKE_BLOCK)
			{
				if(CakePort.Permissions != null)
				{
					if(CakePort.Permissions.has(player, "cakeport.add") || player.isOp())
					{
						SelectBlock.put(player, block);
						player.sendMessage(ChatColor.DARK_GREEN + "Cake selected.");
					}
				}
				else if ( player.isOp())
				{
					SelectBlock.put(player, block);
					player.sendMessage(ChatColor.DARK_GREEN + "Cake selected.");
				}
			}
			
			//Cake is a lie
			if(isCakePort && block.getType() == Material.CAKE_BLOCK)
			{
				int health = player.getHealth();
				
				if(health < 20)
				{
					event.setCancelled(true);
					block.setData((byte) 0x0);
				}
			}
		}
	}

	public void onPlayerMove(PlayerMoveEvent event)
	{
		World world = event.getTo().getWorld();
		Player player = event.getPlayer();
		String cakeName = "";
		Location destLoc;
		
		//getY()-.2 = at what point below the player to scan
		Location loc = new Location(world, event.getTo().getX(), event.getTo().getY()-.2, event.getTo().getZ());
		Block blockin = world.getBlockAt(loc);
		
		boolean isCakePort = Files.CakeBlock.containsValue(blockin);

		if(!isCakePort)
			hasTeleported.put(player, false);
		
		//needs OP or Permissions
		if(CakePort.Permissions != null){
			if(player.isOp() || CakePort.CheckPermission(player, "cakeport.warp")){
				if(isCakePort&& !hasTeleported.get(player)){
					cakeName = Cakes.getName(blockin);
					boolean isLinked = Files.CakeLinks.containsKey(cakeName);
			
					if (isLinked){
						destLoc = Cakes.getDest(cakeName, blockin);
						destLoc.setPitch(player.getLocation().getPitch());
						destLoc.setYaw( player.getLocation().getYaw());
						event.setTo(destLoc);
						if (player.teleport(destLoc))
							hasTeleported.put(player, true);
						}
					}
				}
			}
		//default everybody can teleport with no Permissions
		else{
			if(isCakePort&& !hasTeleported.get(player)){
				cakeName = Cakes.getName(blockin);
				boolean isLinked = Files.CakeLinks.containsKey(cakeName);
				if (isLinked){
					destLoc = Cakes.getDest(cakeName, blockin);
					destLoc.setPitch(player.getLocation().getPitch());
					destLoc.setYaw( player.getLocation().getYaw());
					event.setTo(destLoc);
					if (player.teleport(destLoc))
						hasTeleported.put(player, true);
					}
				}
			}
		}
	}


















