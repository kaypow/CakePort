package com.gmail.marvinj91.CakePort;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Cakes {
	
	public static boolean isCakeBlock(Block block)
	{
		Properties pro = new Properties();
		Location loc = block.getLocation();
		World world = block.getWorld();
		
		String worldName = world.getName();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		try{
			FileInputStream in = new FileInputStream(CakePort.blocks);
			pro.load(in);
			if(pro.contains(worldName + "," + x + "," + y + "," + z ))
				return true;
		} catch(IOException e)
		{
			
		}
		return false;
	}

	public static String getName(Block block) 
	{
		Iterator keys = Files.CakeBlock.keySet().iterator();
		
		while(keys.hasNext()){
			String cakeName = (String)keys.next();
			Block cBlock = Files.CakeBlock.get(cakeName);
			if(cBlock == block)
				return cakeName;
		}
		return null;
		
		/*//Old method
		 Properties pro = new Properties();
		Location loc = block.getLocation();
		
		//String worldName = world.getName();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		try
		{
			FileInputStream in = new FileInputStream(CakePort.blocks);
			pro.load(in);
			Iterator keys = pro.keySet().iterator();
			
			while(keys.hasNext()){
				String key = (String) keys.next();
				String string = (String) pro.getProperty(key);
				String[] arg = string.split(",");
				int x2 = Integer.parseInt(arg[1]);
				int y2 = Integer.parseInt(arg[2]);
				int z2 = Integer.parseInt(arg[3]);
				
				if( x2 == x && y2 == y && z2 == z )
					return key;
			}
		} catch(IOException e)
		{
			
		}
		return null;*/
	}

	public static Location getDest(String cakeName, Block block) 
	{
		Location loc;
		String destCake = "";
		
		if(Files.CakeBlock.containsKey(cakeName)){
			destCake = (String)Files.CakeLinks.get(cakeName);
			Block destBlock = Files.CakeBlock.get(destCake);
			loc = destBlock.getLocation();
			
			//y variable adjusts for Cake, full or half blocks (fooling around)
			loc.add(.5, 1, .5);
			if(block.getType() == Material.CAKE_BLOCK){
				loc.subtract(0, .5625, 0);
			}else if(block.getType() == Material.STEP){
				loc.subtract(0, .5, 0);
			}
			return loc;
		}
		return null;
		
		/*//Old method
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		Location loc = block.getLocation();
		World world = block.getWorld();
		String name = cakeName;
		String destCake = "";
		
		try
		{
			FileInputStream in = new FileInputStream(CakePort.links);
			FileInputStream in2 = new FileInputStream(CakePort.blocks);
			pro.load(in);
			pro2.load(in2);
			
			if(pro.containsKey(name)){
				destCake = (String)pro.getProperty(name);
				String value = (String)pro2.getProperty(destCake);
				String[] arg = value.split(",");
				world = server.getWorld(arg[0]);
				double x = Integer.parseInt(arg[1]) + .5;
				double y = Integer.parseInt(arg[2]) + 1;
				double z = Integer.parseInt(arg[3]) + .5;
				
				if(block.getType() == Material.CAKE_BLOCK){
					y = y - .5625;
				}
				else if(block.getType() == Material.STEP){
					y = y - .5;
				}
				
				loc.setWorld(world);
				loc.setX(x);
				loc.setY(y);
				loc.setZ(z);
				
				return loc;
			}
			
		} catch(IOException e)
		{
			
		}
		
		return null;*/
	}

}
