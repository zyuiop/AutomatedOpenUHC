package net.zyuiop.automatedOpenUHC;

import net.zyuiop.automatedOpenUHC.events.AUHCCountdownEnded;
import net.zyuiop.automatedOpenUHC.events.AUHCEnoughPlayers;
import net.zyuiop.automatedOpenUHC.events.AUHCNotEnoughPlayers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventsListener implements Listener {
	
	public AutomatedOpenUHC pl = null;
	public EventsListener(AutomatedOpenUHC pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onCountdownEnd(AUHCCountdownEnded e) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable() {
            public void run() {
            	for (Player p : Bukkit.getServer().getOnlinePlayers())
            		p.setScoreboard(null);
            	pl.getServer().dispatchCommand(pl.getServer().getConsoleSender(), "gamestart");
            }
        }, 1L);
		
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable() {
            public void run() {
        		p.setScoreboard(pl.getSbManager().getMainScoreboard());
            }
        }, 5L);
	}
	
	@EventHandler
	public void onEnoughPlayers(AUHCEnoughPlayers e) {
		pl.getThread().setCountdownEnabled(true);
		pl.getSbManager().setCountdownEnabled(true);
	}
	
	@EventHandler
	public void onNotEnoughPlayers(AUHCNotEnoughPlayers e) {
		Bukkit.broadcastMessage(pl.localize("not_enough_players"));
		pl.getThread().setCountdownEnabled(false);		
		pl.getSbManager().setCountdownEnabled(false);
		pl.getThread().setTime(-1);
	}
}
