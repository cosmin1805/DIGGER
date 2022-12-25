package ro.iacobai.digger.player;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.data.DataHandler;

public class OnLeave implements Listener {
    NamespacedKey namespacedKey_Task_Id= new NamespacedKey(DIGGER.getPlugin(),"task_id");
    NamespacedKey namespacedKey_Task_Running= new NamespacedKey(DIGGER.getPlugin(),"task_running");
    @EventHandler
    public void onleave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        int task_running = DataHandler.get_bool(namespacedKey_Task_Running,data);
        if(task_running==1) {
            int ID = data.get(namespacedKey_Task_Id, PersistentDataType.INTEGER);
            Bukkit.getScheduler().cancelTask(ID);
            System.out.println("Task of " +player.getName()+" canceled!");
        }
    }
}
