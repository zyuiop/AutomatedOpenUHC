package net.zyuiop.automatedOpenUHC;

import java.util.Random;

import net.zyuiop.openUHC.utils.UHUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager implements org.bukkit.scoreboard.ScoreboardManager {
	
	protected Scoreboard sb;
	protected Objective right;	
	private AutomatedOpenUHC plugin;
	public String sbname = "count";
	
	public ScoreboardManager(AutomatedOpenUHC pl) {
		this.plugin = pl;
		this.sb = Bukkit.getScoreboardManager().getNewScoreboard();
	}
	
	public int hours = 0;
	public int minutes = 0;
	public int seconds = 0;
	public String phase = "";
	public boolean countdown = false;
	
	public void refresh() {
		Objective obj = null;
		try {
			obj = sb.getObjective(sbname);
			obj.setDisplaySlot(null);
			obj.unregister();
		} catch (Exception e) {

		}
		Random r = new Random();
		sbname = "count"+r.nextInt(10000000);
		obj = sb.registerNewObjective(sbname, "dummy");
		obj = sb.getObjective(sbname);
		this.right = obj;

		right.setDisplayName(ChatColor.DARK_AQUA+"== UHC Games ==");
		right.setDisplaySlot(DisplaySlot.SIDEBAR);
		right.getScore(Bukkit.getOfflinePlayer(this.phase)).setScore(5);
		if (countdown) right.getScore(Bukkit.getOfflinePlayer(((minutes > 0) ? minutes+"m " : "")+seconds+"s")).setScore(4);
		right.getScore(Bukkit.getOfflinePlayer(" ")).setScore(3);
		right.getScore(Bukkit.getOfflinePlayer(plugin.localize("tag_players")+" : "+ChatColor.AQUA+Bukkit.getOnlinePlayers().length)).setScore(2);
		right.getScore(Bukkit.getOfflinePlayer(plugin.localize("tag_min_players")+" : "+plugin.getConfig().getInt("min-players", 5))).setScore(1);
	}
	
	public void init() {
		phase = plugin.localize("tag_waiting");
		refresh();
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(sb);
		}
		
	}

	public Objective getRight() {
		return right;
	}
	
	public void formatTime(long seconds) {
		int hours = (int) seconds / 3600;
	    int remainder = (int) seconds - hours * 3600;
	    int mins = remainder / 60;
	    remainder = remainder - mins * 60;
	    int secs = remainder;

	    this.hours = hours;
	    this.minutes = mins;
	    this.seconds = secs;
	}
	
	public void setCountdownEnabled(boolean en) {
		countdown = en;
		if (en == true)
			phase = plugin.localize("tag_start_in");
		else
			phase = plugin.localize("tag_waiting");
	}

	public Scoreboard getMainScoreboard() {
		return sb;
	}

	public Scoreboard getNewScoreboard() {
		return Bukkit.getScoreboardManager().getNewScoreboard();
	}
}
