package com.gmail.marvinj91.CakePort;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class CakePort extends JavaPlugin
{
	public static final String s = File.separator;
	static String maindirectory = "plugins" + s + "CakePort" + s;
	static File blocks = new File(maindirectory + "data" + s + "locs.data");
	static File links = new File(maindirectory + "data" + s + "links.data");
	private final CakePortPlayerListener playerListener = new CakePortPlayerListener(this);
	private final CakePortBlockListener blockListener = new CakePortBlockListener(this);
	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	
	public static Server server;
	
	public void onLoad(){
	}
	
	public void onEnable()
	{
		new File(maindirectory + "data/").mkdir();
		if(!blocks.exists()){
			try {
				blocks.createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		new File(maindirectory + "data/").mkdir();
		if(!links.exists()){
			try{
				links.createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		server = this.getServer();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.High, this);
		
		
		getCommand("addcake").setExecutor(new addCakeCommand(this));
		getCommand("linkcakes").setExecutor(new linkCakeCommand(this));
		
		setupPermissions();
		
		PluginDescriptionFile pdfFile = getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been enabled.");

		if(Files.loadBlocks())
			System.out.println("CakePort blocks loaded.");
		if(Files.loadLinks())
			System.out.println("CakePort links loaded");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdfFile = getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been disabled.");
	}
	
	public boolean isDebugging(Player player){
		if (this.debugees.containsKey(player)){
			return ((Boolean)this.debugees.get(player)).booleanValue();
		}
		return false;
	}
	
	public void setDebugging(Player player, boolean value)
	{
		this.debugees.put(player, Boolean.valueOf(true));
	}
	
	 public boolean anonymousCheck(CommandSender sender) 
	 {
		if (!(sender instanceof Player)) {
			 sender.sendMessage("Cannot execute that command, I don't know who you are!");
			 return true;
		} else {
			return false;
		}
	 }
	 
	 public static PermissionHandler Permissions;
	 
	 private void setupPermissions(){
		 Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
		 
		 if(CakePort.Permissions == null){
			 if( test != null){
				 CakePort.Permissions = ((Permissions)test).getHandler();
			 }else{
				 System.out.println("Permission system not detected, defaulting to OP");
			 }
		 }
	 }
	 
	 public static boolean CheckPermission(Player player, String command){
		 command = command.replace(" ", ".");
		 
		 if(Permissions != null){
			 if(Permissions.has(player, command) || player.isOp())
				 return true;
		 }
		 else if(player.isOp())
			 return true;
		 
		 return false;
	 }
}
