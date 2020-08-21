package fr.grandoz.hidesurprise;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.grandoz.hidesurprise.configuration.Variables;
import fr.grandoz.hidesurprise.utils.PlayerData;
import fr.grandoz.hidesurprise.utils.Surprise;

public class SurpriseListener implements Listener {

	public Main main = Main.get();

	public SurpriseManager manager = main.getSurprisemanager();

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(main.getSurprisemanager().getData().containsKey(player.getName())) {
			main.getDbFiles().savePlayerToDisk(player.getName());
			main.getSurprisemanager().getData().remove(player.getName());
		}
	}


	@EventHandler
	public void onInterract(PlayerInteractEvent event) {
				if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
						Block block = event.getClickedBlock();

						if(block == null || block.getType() != Material.SKULL) return;
						if(!manager.getSurprise().containsKey(block.getLocation()))return;

						Player player = event.getPlayer();

						PlayerData data = manager.getPlayerData(player.getName());

						Surprise sup = manager.getSurprise().get(block.getLocation());

						if(data.getComplete().contains(sup.getName())) {
							
						}else {
							data.getComplete().add(sup.getName());
							player.getWorld().spawnParticle(Particle.HEART, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 0, 0,0, 10);
							manager.onMessage(data, player, sup);
							
							//WINNER
							
							if(data.getComplete().size() == manager.getByName().size()) {
								Bukkit.broadcastMessage(Variables.PREFIX +"§f"+player.getName()+" §fvient de trouver toutes les surprises du spawn !"); 
								
								for(String cmd : Variables.cmds) {
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd.replace("%player%", player.getName()));
								}
								
							}
							
						}

	}


}
