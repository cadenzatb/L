package Trubby.co.th;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Trubby.co.th.Data.MySQL;

public class Leveling extends JavaPlugin{

	public static Leveling instance;
	//public MySQL SQL = new MySQL(this, "localhost", "3306", "MC", "root", "46502");
	public MySQL SQL = new MySQL(this, "localhost", "3306", "minecraft", "root", "");
	public static Connection c = null;
	
	public RPGManager rpgmanager;
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		instance = this;
		rpgmanager = new RPGManager();
		
		openConnection();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			rpgmanager.addPlayer(p);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()){
			rpgmanager.removePlayer(p);
		}
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("connect")){
			try {
				Statement statement = c.createStatement();
				statement.executeUpdate("CREATE TABLE RPG(Name varchar(16),Level int,EXP int);");
				statement.close();
				p.sendMessage(ChatColor.GREEN + "Connected");
				p.sendMessage(ChatColor.GREEN + ""+SQL.checkConnection());
			} catch (SQLException e) {
			}
		}else if(label.equalsIgnoreCase("check")){
			p.sendMessage(rpgmanager.list +"");
			if(rpgmanager.list.containsKey(p.getName())){
				p.sendMessage("true");
			}
			RPGPlayer rp = rpgmanager.list.get(p.getName());
			p.sendMessage("" + rp);
		}
		
		return false;
	}
	
	public void openConnection(){
		try {
			c = SQL.openConnection();
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}
	
	
}
