package fr.grandoz.hidesurprise;

import java.util.Map.Entry;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.grandoz.hidesurprise.commands.SurpriseCommand;
import fr.grandoz.hidesurprise.configuration.Conf;
import fr.grandoz.hidesurprise.files.DataBaseFiles;
import fr.grandoz.hidesurprise.utils.PlayerData;
import fr.grandoz.hidesurprise.utils.Surprise;

public class Main extends JavaPlugin {

	public static Main main;
	
	private DataBaseFiles dbFiles;
	
	private SurpriseManager surprisemanager;
	
	private Gson gson;
	
	private Conf conf;
	
	@Override
	public void onEnable() {

		main = this;
		
		builgson();
		
		this.dbFiles = new DataBaseFiles();
		
		this.surprisemanager = new SurpriseManager();
		
		this.conf = new Conf();
		
		getServer().getPluginManager().registerEvents(new SurpriseListener(), this);
		
		getCommand("surprise").setExecutor(new SurpriseCommand());
	}
	
	@Override
	public void onDisable() {

		conf.saveSurprises();
		
		for(Entry<String , PlayerData> entry  : surprisemanager.getData().entrySet()) {
			dbFiles.savePlayerToDisk(entry.getKey());
		}
		
	
	}
	
	public void builgson() {
		this.gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.disableHtmlEscaping()
				.create();
	}
	
	public static Main get() {
		return main;
	}

	public Gson getGson() {
		return gson;
	}

	public DataBaseFiles getDbFiles() {
		return dbFiles;
	}

	public void setDbFiles(DataBaseFiles dbFiles) {
		this.dbFiles = dbFiles;
	}

	public SurpriseManager getSurprisemanager() {
		return surprisemanager;
	}

	public void setSurprisemanager(SurpriseManager surprisemanager) {
		this.surprisemanager = surprisemanager;
	}

	public Conf getConf() {
		return conf;
	}

	public void setConf(Conf conf) {
		this.conf = conf;
	}

	
}
