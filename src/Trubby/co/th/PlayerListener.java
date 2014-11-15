package Trubby.co.th;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Leveling.instance.rpgmanager.addPlayer(e.getPlayer());
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Leveling.instance.rpgmanager.removePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onKill(EntityDeathEvent e){
		e.setDroppedExp(0);
		if(e.getEntity() instanceof Player){
			return;
		}
		if(e.getEntity().getKiller() != null){
			if(e.getEntity().getKiller() instanceof Player){
				Player p = e.getEntity().getKiller();
				Leveling.instance.rpgmanager.addXP(p, 50);
				
			}
		}
	}
	
	@EventHandler
	public void onExpChance(PlayerExpChangeEvent e){
		e.setAmount(0);
	}
}
