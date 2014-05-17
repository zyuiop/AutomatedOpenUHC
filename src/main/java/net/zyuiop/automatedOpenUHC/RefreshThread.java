package net.zyuiop.automatedOpenUHC;

import org.bukkit.Bukkit;

import net.zyuiop.automatedOpenUHC.events.AUHCCountdownEnded;
import net.zyuiop.automatedOpenUHC.events.AUHCEnoughPlayers;
import net.zyuiop.automatedOpenUHC.events.AUHCNotEnoughPlayers;

public class RefreshThread extends Thread {
  private AutomatedOpenUHC pl;
  private long time;
  private boolean count = false;
 
  public RefreshThread(AutomatedOpenUHC openUHC) {
	    this.pl = openUHC;
	    this.time = -1;
  }
  
  public void setCountdownEnabled(boolean enable) {
	  count = true;
	  time = pl.getConfig().getInt("countdown",120);
  }
  
  @SuppressWarnings("static-access")
  public void run() {
    while (true) {
    	try {
			this.sleep(1000);
			if (count)
			{	
				formatTime();
				time--;
				if (Bukkit.getOnlinePlayers().length < pl.getConfig().getInt("min-players",5))  {
					Bukkit.getServer().getPluginManager().callEvent(new AUHCNotEnoughPlayers());
				} else if (time == 0 && count) {
					Bukkit.getServer().getPluginManager().callEvent(new AUHCCountdownEnded());
					return;
				}
			}
			else {
				if (Bukkit.getOnlinePlayers().length >= pl.getConfig().getInt("min-players",5))  {
					Bukkit.getServer().getPluginManager().callEvent(new AUHCEnoughPlayers());
				}
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  }
  
  public void formatTime() {
		int hours = (int) time / 3600;
	    int remainder = (int) time - hours * 3600;
	    int mins = remainder / 60;
	    remainder = remainder - mins * 60;
	    int secs = remainder;
	    String time = null;

	    if (hours > 1) {
	    	if (secs == 0) {
	    		if (mins == 30 || mins == 0) time = hours+"h"+mins;
	    	}
	    }
	    else {
	    	if ((mins == 45 || mins == 30 || mins == 20 || mins == 10 || mins == 5 || mins == 3 || mins == 2 || mins == 1) && secs == 0) time = mins+" "+pl.localize("minutes");
	    	if (mins == 1 && secs == 30) time = mins+" "+pl.localize("minutes")+" "+secs+" "+pl.localize("seconds");
	    	if (mins == 0) {
	    		if (secs == 30 || secs == 10 || (secs <= 5 && secs > 0)) time = secs+" "+pl.localize("seconds");
	    	}
	    }
	    if (time != null)
	    	Bukkit.broadcastMessage(pl.localize("begin_timer").replace("{TIME}", time));
	}
  
}
