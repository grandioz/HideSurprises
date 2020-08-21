package fr.grandoz.hidesurprise.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonSyntaxException;

import fr.grandoz.hidesurprise.Main;
import fr.grandoz.hidesurprise.utils.PlayerData;
import fr.grandoz.hidesurprise.utils.Surprise;


public class DataBaseFiles {

	public Main main = Main.get();

	public File folder = new File(main.getDataFolder() , "players");

	public SerializeManager srl = new SerializeManager();

	public FileUtils fileutils = new FileUtils();

	public DataBaseFiles() {
		if(!folder.exists())folder.mkdir();


	}

	public PlayerData createAccount(String name) {
		File file = new File(folder , name+".json");
		PlayerData pd = new PlayerData(name ,new ArrayList<String>());

		try {
			file.createNewFile();
			FileUtils.writeFile(file.getCanonicalPath(), srl.serializePD(pd));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pd;
	}
	public PlayerData createAccount(String name , File file) {
		PlayerData pd = new PlayerData(name ,new ArrayList<String>());

		try {
			file.createNewFile();
			FileUtils.writeFile(file.getCanonicalPath(), srl.serializePD(pd));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pd;
	}

	public PlayerData getPlayerFromDisk(String name) {
		File file = new File(folder , name+".json");
		PlayerData data = null;
		if(!file.exists()) {
			data = createAccount(name , file);
			removebadData(data);
			return data;
		}
		try {	
			data = main.getGson().fromJson(FileUtils.readFileAsString(file.getCanonicalPath()),PlayerData.class);
			removebadData(data);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void savePlayerToDisk(String name) {
		File file = new File(folder , name+".json");
		PlayerData pd = main.getSurprisemanager().getPlayerData(name);
		try {
			FileUtils.writeFile(file.getCanonicalPath(), srl.serializePD(pd));
		} catch (IOException e) {
			e.printStackTrace();
		}	

	}

	public void removebadData(PlayerData pd) { 
		List<String> toRemove = new ArrayList();
		for(String str : pd.getComplete()){

			if(!main.getSurprisemanager().getByName().containsKey(str)) {

				toRemove.add(str);

			}

		}
		pd.getComplete().removeAll(toRemove);
	}

}
