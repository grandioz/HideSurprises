package fr.grandoz.hidesurprise.utils;

import java.util.ArrayList;
import java.util.List;


public class PlayerData {

	private String name;
	
	private List<String> complete = new ArrayList<String>();
	
    public PlayerData(String name , List<String> complete) {
    	
    	this.setName(name);
    	this.setComplete(complete);
    	
	}

	public List<String> getComplete() {
		return complete;
	}

	public void setComplete(List<String> complete) {
		this.complete = complete;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
   
	
	
}
