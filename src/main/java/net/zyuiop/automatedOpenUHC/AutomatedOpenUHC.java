package net.zyuiop.automatedOpenUHC;

import org.bukkit.plugin.java.JavaPlugin;

public class AutomatedOpenUHC extends JavaPlugin {
	
	public void onEnable() {
		getServer().getPluginManager().getPlugin("OpenUHC");
	}
}
