package fr.grandoz.hidesurprise.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import fr.grandoz.hidesurprise.Main;
import fr.grandoz.hidesurprise.SurpriseManager;
import fr.grandoz.hidesurprise.configuration.Variables;
import fr.grandoz.hidesurprise.utils.PlayerData;
import fr.grandoz.hidesurprise.utils.Surprise;

public class SurpriseCommand implements CommandExecutor {

	public Main main = Main.get();
	
	public SurpriseManager manager = main.getSurprisemanager();
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		
		if(args.length == 0 && sender instanceof Player ) {
			Player player = (Player) sender;
			PlayerData pd = manager.getPlayerData(player.getName());
			
			removebadData(pd);
			
			player.sendMessage("§6§m-----------------------------------");
			player.sendMessage("§eVous avez trouvé §f"+pd.getComplete().size() +" §esurprises sur §f" + manager.getByName().size() + " §e!");
			player.sendMessage("§eProgression:");
			player.sendMessage(manager.getProgressBar(pd.getComplete().size(), manager.getByName().size(), 60, '|', ChatColor.GOLD, ChatColor.GRAY));
			player.sendMessage("§6§m-----------------------------------");
			
		}
		
		
		if(!sender.hasPermission("surprise.use"))return false;
		
		//surprise create <name>
		if(args.length == 2 && args[0].equalsIgnoreCase("create") && sender instanceof Player ) {
			Player player = (Player) sender;
			if(manager.getByName().containsKey(args[1])) {
				player.sendMessage(Variables.PREFIX+"§9Ce nom de surprise existe déja !");
				return true;
			}
			
			Location loc = player.getLocation().getBlock().getLocation();
			Surprise sup = new Surprise(loc.getX(), loc.getY(), loc.getZ(), loc.getWorld().getName(), ChatColor.stripColor(args[1]));
			
			manager.getByName().put(sup.getName(), sup);
			manager.getSurprise().put(sup.getLocation(), sup);
			
			manager.placeSuprise(player.getLocation(), sup);
			
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("delete")  ) {
			if(!manager.getByName().containsKey(args[1])) {
				sender.sendMessage(Variables.PREFIX+"§9Cette surprise n'existe pas !");
				return true;
			}
			
			Surprise sup = manager.getByName().get(args[1]);
			manager.getByName().remove(sup.getName());
			manager.getSurprise().remove(sup.getLocation());
			sender.sendMessage(Variables.PREFIX + "§eVous venez de retirer la surprise §f"+sup.getName());
			
		}	
		
		if(sender instanceof Player && args.length == 2 && args[0].equalsIgnoreCase("teleport")  ) {
			Player player = (Player) sender;
			if(!manager.getByName().containsKey(args[1])) {
				player.sendMessage(Variables.PREFIX+"§9Cette surprise n'existe pas !");
				return true;
			}
			
			Surprise sup = manager.getByName().get(args[1]);
			player.teleport(sup.getLocation());
		}	
		
		//surprise list
		if(args.length == 1 && args[0].equalsIgnoreCase("list")  ) {

			StringBuilder builder = new StringBuilder(Variables.PREFIX +"§9List des surprises: §f");
			
			for(Entry<String , Surprise> entry : manager.getByName().entrySet()) {
				builder.append(entry.getKey() +" ");
			}
			sender.sendMessage(builder.toString());
		}	
		
		//surprise reset <player>
		if(args.length == 2 && args[0].equalsIgnoreCase("reset")  ) {

			Player target = Bukkit.getPlayer(args[1]);
			if(target != null) {
				
				PlayerData pd = manager.getPlayerData(target.getName());
				pd.getComplete().clear();
				target.sendMessage(sender.getName() +" §evient de clear toutes vos surprises");
				sender.sendMessage(Variables.PREFIX +"§eVous venez de clear les surprises de §f"+target.getName());
				
			}else {
				sender.sendMessage("§cCe joueur n'est pas connecté !");
				return true;
			}
		}	
		
		
		//surprise list
		if(args.length == 1 && args[0].equalsIgnoreCase("reload")  ) {
			main.reloadConfig();
			sender.sendMessage("§creload complete");
			Variables.cmds = main.getConfig().getStringList("cmds");
		}	
		
		
		//surprise list
		if(args.length == 1 && args[0].equalsIgnoreCase("help")  ) {
			sender.sendMessage("§c/surprise: §eVoir votre progression");
			sender.sendMessage("§c/surprise create <nom>: §eCréer uen surprise");
			sender.sendMessage("§c/surprise delete <nom>: §eEnlever une surprise");
			sender.sendMessage("§c/surprise list: §eVoir la list des surprises");
			sender.sendMessage("§c/surprise reset <joueur>: §eReset la progression d'un joueur");
			sender.sendMessage("§c/surprise teleport <nom>: §eSe teleporter vers une surprise");
			
		}	
		
		
		
		return false;
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
