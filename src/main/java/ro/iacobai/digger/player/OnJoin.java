package ro.iacobai.digger.player;

import org.bukkit.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;

import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.tasks.DigPlace;

public class OnJoin implements Listener {
    DataHandler dataHandler = new DataHandler();
    DIGGER digger = DIGGER.getPlugin();
    @EventHandler
    public void onjoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1 && DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==0){
            DigPlace digPlace = new DigPlace();
            digPlace.run_t(player,DataHandler.get_int(dataHandler.namespaceKey_Task_Next_Time,data));
            System.out.println("Task of " +player.getName()+" resumed!");
            player.sendMessage(ChatColor.AQUA+"---------------------");
            player.sendMessage(ChatColor.GREEN+"Your Digger Task was resumed!");
            player.sendMessage(ChatColor.AQUA+"---------------------");
        }
    }
}
