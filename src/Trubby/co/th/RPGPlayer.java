package Trubby.co.th;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RPGPlayer {

	String name;
	int level;
	int exp;
	
	int strengh;
	int intellgent;
	int stamina;
	
	public RPGPlayer(Player p){
		name = p.getName();
		try {
		
		//GET DATA FROM SQL TO RPG-PLAYER
		Statement statement = Leveling.c.createStatement();
		ResultSet res = statement.executeQuery("SELECT * FROM RPG WHERE Name='"+p.getName()+"'");
		res.next();
		level = res.getInt("Level");
		exp = res.getInt("EXP");
		
		p.sendMessage(ChatColor.GREEN + "Connected to Leveling System " + ChatColor.DARK_GRAY + "by olTrubBylo");
		p.sendMessage(ChatColor.YELLOW + p.getName() + " Lv. " + level);
		
		res.close();
		statement.close();
		
		//UPDATE PLAYER IN-GAME STATUS
		p.setLevel(level);
		p.setExp((float) exp / LevelMeter.getNeededExp(level));
		
		} catch (SQLException e) {
			System.out.println("--- BUG construct");
		}
	}
	
	public void save(){
		try {
			Statement statement = Leveling.c.createStatement();
			statement.executeUpdate("UPDATE RPG SET Level='"+level+"',EXP='"+exp+"' WHERE Name='"+name+"';");
			statement.close();
		} catch (SQLException e) {
			System.out.println("--- BUG save");
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return this.exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
	
}
