package net.zyuiop.automatedOpenUHC;

import net.zyuiop.automatedOpenUHC.events.AUHCCountdownEnded;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventsListener implements Listener {
	
	@EventHandler
	public void onCountdownEnd(AUHCCountdownEnded e) {
		// Do stuff (launch game...)		
	}
}
