package net.zyuiop.automatedOpenUHC.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event fired when the countdown is finished
 * @author zyuiop
 *
 */
public class AUHCCountdownEnded extends Event {

	private static final HandlerList handlers = new HandlerList();
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
        return handlers;
    }

}
