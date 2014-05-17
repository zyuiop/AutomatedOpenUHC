package net.zyuiop.automatedOpenUHC.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event fired when there is no more enough players to launch the game
 * @author zyuiop
 *
 */
public class AUHCNotEnoughPlayers extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
        return handlers;
    }
}
