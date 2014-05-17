package net.zyuiop.automatedOpenUHC;

import java.io.File;
import java.io.InputStream;

import net.zyuiop.openUHC.OpenUHC;
import net.zyuiop.openUHC.listeners.BlockEvents;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class AutomatedOpenUHC extends JavaPlugin {
	
	public OpenUHC plugin = null;
	public String language = null;
	public RefreshThread thread = null;
	
	public void onEnable() {
		this.saveDefaultConfig();
		
		this.language = getConfig().getString("language", "en");
		loadTranslations();
		
		
		
		plugin = (OpenUHC) getServer().getPluginManager().getPlugin("OpenUHC");
		thread = new RefreshThread(this);
		thread.start();
		
		getServer().getPluginManager().registerEvents(new EventsListener(this), this);
	}
	
	public RefreshThread getThread() {
		return thread;
	}
	
	private FileConfiguration translationsFile = null;
	private File configFile = null;
	public void loadTranslations(boolean force) {
		if (translationsFile == null || force) {
			configFile = new File(getDataFolder(), "translations."+language+".yml");
			if (!configFile.exists()) {
				try {
					Bukkit.getLogger().info("Language was not found, trying to get it from the plugin file...");
					saveResource("translations."+language+".yml", false);
				} catch (IllegalArgumentException e) {
					if (language.equals("en"))
					{	translationsFile = null;
						Bukkit.getLogger().severe("An error occured : impossible to find en language !"+e.getMessage());
					} else {
						Bukkit.getLogger().warning("An error occured : impossible to find "+language+" language !"+e.getMessage());
						Bukkit.getLogger().info("en language will be used in replacement.");
						language = "en";
						loadTranslations(true);
					}
				}
			}
		 	translationsFile = YamlConfiguration.loadConfiguration(configFile);
		 }
		 InputStream defConfigStream = this.getResource("translations.yml");
		 if (defConfigStream != null) {
		        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		        translationsFile.setDefaults(defConfig);
		 }
	}
	
	public void loadTranslations() {
		loadTranslations(false);
	}
	
	
	public String localize(String key) {
		String tran = translationsFile.getString(key);
		if (tran == null) {
			this.getLogger().warning("An error occured : impossible to find translation for "+key+" in translations.yml file !");
			tran = ChatColor.DARK_RED+"Fatal error : Failed to get translation for key "+ChatColor.AQUA+key;
		}
		else 
			tran = ChatColor.translateAlternateColorCodes('&', tran); //Colors
		return tran;
	}
	
	public void onDisable() {
		thread.end();
	}
}
