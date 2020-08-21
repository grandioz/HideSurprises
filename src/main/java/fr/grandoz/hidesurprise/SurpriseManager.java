package fr.grandoz.hidesurprise;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.common.base.Strings;

import fr.grandoz.hidesurprise.configuration.Variables;
import fr.grandoz.hidesurprise.utils.PlayerData;
import fr.grandoz.hidesurprise.utils.Surprise;

public class SurpriseManager {

	public Main main = Main.get();
	
	private HashMap<Location, Surprise> surprises = new HashMap<>();
	
	private HashMap<String, PlayerData> data = new HashMap<>();	
	
	private HashMap<String, Surprise> byName = new HashMap<>();

	
	public PlayerData getPlayerData(String name) {
		PlayerData pd = data.get(name);
		if(pd == null) {
			pd = main.getDbFiles().getPlayerFromDisk(name);
			data.put(pd.getName(), pd);
		}
		return pd;
	}
	
	public void placeSuprise(Location loc , Surprise surprise) {
		Block block = loc.getBlock();
		block.setType(Material.SKULL);
		BlockState state = block.getState();
		Skull skull = (Skull) state;
		skull.setOwner("grandoz_");
		skull.update(true , false);
		skull.update();
		surprises.put(loc.getBlock().getLocation(), surprise);
		surprise.setlocation(loc.getBlock().getLocation());
	}
	
	public void onMessage(PlayerData pd , Player player , Surprise sup) {
		player.sendMessage("§6§m---------------§b§lSurprise§6§m---------------");
		player.sendMessage("§eVous venez de trouver la surprise §f" + sup.getName());
		player.sendMessage("§eVous avez trouvé §f"+pd.getComplete().size() +" §esurprises sur §f" + byName.size() +" §!");
		player.sendMessage("§6§m--------------------------------------");
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 20, 20);
		player.sendTitle("§6bravo !", sup.getName());
	}
	
	   public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
	            ChatColor notCompletedColor) {
	        float percent = (float) current / max;
	        int progressBars = (int) (totalBars * percent);

	        return Strings.repeat("" + completedColor + symbol, progressBars)
	                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
	    }	
		
	
	public void removeSurprise(Surprise surprise) {
		surprise.getLocation().getBlock().setType(Material.AIR);
			surprises.remove(surprise.getLocation());
	}


	
	public HashMap<Location, Surprise> getSurprise() {
		return surprises;
	}

	public HashMap<String, PlayerData> getData() {
		return data;
	}

	public void setData(HashMap<String, PlayerData> data) {
		this.data = data;
	}

	public void setSurprise(HashMap<Location, Surprise> surprise) {
		this.surprises = surprise;
	}

	public HashMap<String, Surprise> getByName() {
		return byName;
	}

	public void setByName(HashMap<String, Surprise> byName) {
		this.byName = byName;
	}
	
}
