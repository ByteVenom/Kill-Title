package com.katsaroucraft.gopaintman.killtitle;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.tag.PlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

public class Main extends JavaPlugin implements Listener{
	private static final Map<String, Integer> killStreak = new HashMap<String, Integer>();
	@Override
	public void onEnable() {
	if(getServer().getPluginManager().getPlugin("TagAPI") == null){
		this.getServer().getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
        getServer().getPluginManager().disablePlugin(this);
        return;
	}
	this.getServer().getPluginManager().registerEvents(this, this);
		
	}

	@Override
	public void onDisable() {
	
		
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent ev){
		if(ev.getEntity().getKiller() instanceof Player){
			if(killStreak.containsKey(ev.getEntity().getKiller().getName())){
				int kills = killStreak.get(ev.getEntity().getKiller().getName());
				TagAPI.refreshPlayer(ev.getEntity().getKiller());
				killStreak.put(ev.getEntity().getKiller().getName(), kills+= 1);
				if(kills == 3){
					ev.getEntity().getKiller().sendMessage(ChatColor.GOLD + "You're on a 3 kill streak!");
				}else if(kills == 5){
					ev.getEntity().getKiller().sendMessage(ChatColor.GOLD + "You're on a 5 kill streak!");
				}else if(kills == 7){
					ev.getEntity().getKiller().sendMessage(ChatColor.GOLD + "You're on a 7 kill streak!");
				}else if(kills == 10){
					ev.getEntity().getKiller().sendMessage(ChatColor.GOLD + "You're on a 10 kill streak!");
					this.getServer().broadcastMessage(ChatColor.GOLD + "Player: " + ChatColor.RED + ev.getEntity().getKiller().getName() +ChatColor.GOLD +  " is on a 10 kill streak!");
					
				}
			
			}
			if(!killStreak.containsKey(ev.getEntity().getKiller().getName())){
				killStreak.put(ev.getEntity().getKiller().getName(), 1);
				TagAPI.refreshPlayer(ev.getEntity().getKiller());
			
			}
			if(killStreak.containsKey(ev.getEntity().getName())){
				int kills = killStreak.get(ev.getEntity().getName());
				if(kills > 2 && kills <5){
					this.getServer().broadcastMessage(ChatColor.GREEN + ev.getEntity().getName() + ChatColor.GOLD  + " has ended " + ChatColor.RED +  ev.getEntity().getName() + ChatColor.GOLD + "'s small streak!");
				}else if(kills > 5 && kills < 7){
					this.getServer().broadcastMessage(ChatColor.GREEN + ev.getEntity().getName() + ChatColor.GOLD  + " has ended " + ChatColor.RED +  ev.getEntity().getName() + ChatColor.GOLD + "'s medium streak!");
				}else if(kills > 7 && kills < 10){
					this.getServer().broadcastMessage(ChatColor.GREEN + ev.getEntity().getName() + ChatColor.GOLD  + " has ended " + ChatColor.RED +  ev.getEntity().getName() + ChatColor.GOLD + "'s dominating streak!");
				}else if(kills > 10){
					this.getServer().broadcastMessage(ChatColor.GREEN + ev.getEntity().getName() + ChatColor.GOLD  + " has ended " + ChatColor.RED +  ev.getEntity().getName() + ChatColor.GOLD + "'s devastating streak!");
				}
				killStreak.remove(ev.getEntity().getName());
				TagAPI.refreshPlayer(ev.getEntity().getKiller());
			
			
			}
			
		}else{
			
		}
	}
	@EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent ev){
		if(killStreak.containsKey(ev.getNamedPlayer().getName())){
			ev.setTag("Kills: "  + killStreak.get(ev.getNamedPlayer().getName()) +" "  + ev.getNamedPlayer().getName());
			this.getServer().broadcastMessage("Named");
		}
	}
	
}