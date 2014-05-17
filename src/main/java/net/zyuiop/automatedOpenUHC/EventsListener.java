package net.zyuiop.automatedOpenUHC;

import net.zyuiop.automatedOpenUHC.events.AUHCCountdownEnded;
import net.zyuiop.automatedOpenUHC.events.AUHCEnoughPlayers;
import net.zyuiop.automatedOpenUHC.events.AUHCNotEnoughPlayers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventsListener implements Listener {
	
	public AutomatedOpenUHC pl = null;
	public EventsListener(AutomatedOpenUHC pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onCountdownEnd(AUHCCountdownEnded e) {
		pl.getServer().dispatchCommand(pl.getServer().getConsoleSender(), "gamestart");
	}
	
	@EventHandler
	public void onEnoughPlayers(AUHCEnoughPlayers e) {
		pl.getThread().setCountdownEnabled(true);
	}
	
	@EventHandler
	public void onNotEnoughPlayers(AUHCNotEnoughPlayers e) {
		Bukkit.broadcastMessage(pl.localize("not_enough_players"));
		pl.getThread().setCountdownEnabled(false);		
		pl.getThread().setTime(-1);
	}
}
