package Trubby.co.th;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import Trubby.co.th.Data.Utils;

public class RPGManager {

	public Random ran = new Random();
	public HashMap<String, RPGPlayer> list = new HashMap<String, RPGPlayer>();
	
	public void addPlayer(Player p){
		if(checkDataExist(p)){
			list.put(p.getName(), new RPGPlayer(p));
		}else{
			try {
				Statement statement = Leveling.c.createStatement();
				statement.executeUpdate("INSERT INTO RPG (`Name`,`Level`,`EXP`) VALUES ('" + p.getName() + "', '1', '0')");
				statement.close();
				
				list.put(p.getName(), new RPGPlayer(p));
				Bukkit.broadcastMessage(ChatColor.GOLD + "Greeting! :D [sql-registered] " + ChatColor.WHITE + p.getName());
			} catch (Exception e) {
			}
		}
	}
	
	public void removePlayer(Player p){
		if(list.containsKey(p.getName())){
			RPGPlayer rpgplayer = list.get(p.getName());
			rpgplayer.save();
			list.remove(p.getName());
			
			Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " has been deleted from cache");
		}
	}
	
	public boolean checkDataExist(Player p){
		try {
			PreparedStatement pre = Leveling.c.prepareStatement("SELECT * FROM `RPG` WHERE Name=?;");
			pre.setString(1, p.getName());
			ResultSet res = pre.executeQuery();
			boolean containPlayer = res.next();
			
			pre.close();
			res.close();
			
			return containPlayer;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void addXP(Player p, int amount){
		RPGPlayer rp = list.get(p.getName());
		//Should level up?
		if((rp.getExp() + amount) > LevelMeter.getNeededExp(rp.getLevel())){
			levelUP(p);
		}else{
			rp.setExp(rp.getExp() + amount);
		}
		
		updateEXPbar(p);
	}
	
	public void levelUP(Player p){
		RPGPlayer rp = list.get(p.getName());
		rp.setLevel(rp.getLevel() + 1);
		rp.setExp(0);
		
		updateEXPbar(p);
		
		p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "LEVEL UP!");
		p.sendMessage(ChatColor.YELLOW + "Your level is now LV." + rp.getLevel());
		
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
		int[] colors = { ran.nextInt(16711680) };
		int[] fadecolors = { ran.nextInt(16711680) };
		Utils.CreateFireworksExplosion(p.getLocation(), true, true, 2, colors, fadecolors, 0);
	}
	
	public void updateEXPbar(Player p){
		RPGPlayer rp = list.get(p.getName());
		p.setLevel(rp.getLevel());
		p.setExp((float) rp.getExp() / LevelMeter.getNeededExp(rp.getLevel()));
	}
}