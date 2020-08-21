	 package fr.grandoz.hidesurprise.files;

import java.util.Arrays;
import java.util.List;

import fr.grandoz.hidesurprise.Main;
import fr.grandoz.hidesurprise.utils.PlayerData;
import fr.grandoz.hidesurprise.utils.Surprise;

public class SerializeManager {

	public Main main = Main.get();
	
	
	
	public String serializePD(PlayerData pd) {
		return this.main.getGson().toJson(pd);
	}
	
	public PlayerData deserialize(String str) {
		return this.main.getGson().fromJson(str , PlayerData.class);
	}
	
	public String serializeSp(List<Surprise> surprises) {
		return this.main.getGson().toJson(surprises);
	}
	
	public List<Surprise> deserializeSu(String str){
		return stringToArray(str, Surprise[].class);
	}
	
	public <T> List<T> stringToArray(String s, Class<T[]> clazz) {
	    T[] arr = main.getGson().fromJson(s, clazz);
	    return Arrays.asList(arr); 
	}
}
