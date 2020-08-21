package fr.grandoz.hidesurprise.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;

import fr.grandoz.hidesurprise.Main;
import fr.grandoz.hidesurprise.SurpriseManager;
import fr.grandoz.hidesurprise.files.FileUtils;
import fr.grandoz.hidesurprise.files.SerializeManager;
import fr.grandoz.hidesurprise.utils.Surprise;

public class Conf {

	public Main main = Main.get();

	public SurpriseManager manager = main.getSurprisemanager();

	SerializeManager srl = new SerializeManager();

	File file = new File(main.getDataFolder() , "surprises.json");

	public Conf() {
		main.saveDefaultConfig();
		checkFile();
		loadSurprises();
		
		Variables.cmds = main.getConfig().getStringList("cmds");

	}




	public void loadSurprises() {
		try {
			List<Surprise> list = srl.deserializeSu(FileUtils.readFileAsString(file.getCanonicalPath()));
			if(list != null ) {


				for(Surprise sup : list) {
					manager.getSurprise().put(sup.getLocation(), sup);

					manager.getByName().put(sup.getName(), sup);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveSurprises() {
		checkFile();
		List<Surprise> list = new ArrayList<>();
		for(Entry<Location , Surprise> entry : manager.getSurprise().entrySet()) {
			list.add(entry.getValue());
		}
		try {
			FileUtils.writeFile(file.getCanonicalPath(), srl.serializeSp(list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void checkFile() {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


}
