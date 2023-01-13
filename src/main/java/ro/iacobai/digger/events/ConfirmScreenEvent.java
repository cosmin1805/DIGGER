package ro.iacobai.digger.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.gui.ConfirmScreen;
import ro.iacobai.digger.gui.SelectionScreen;
import ro.iacobai.digger.tasks.DigPlace;
import ro.iacobai.digger.tasks.SelectionScreenRefresh;

public class ConfirmScreenEvent implements Listener {
    DataHandler dataHandler = new DataHandler();
    DigPlace digPlace = new DigPlace();
    DIGGER digger = DIGGER.getPlugin();
    SelectionScreenRefresh selectionScreenRefresh = new SelectionScreenRefresh();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getClickedInventory();
        if (inv == null) {
            return;
        }
        if (inv.getHolder() instanceof ConfirmScreen) {

            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            PersistentDataContainer data = player.getPersistentDataContainer();
            Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1, data);
            if(DataHandler.get_bool(dataHandler.namespaceKey_Await_Confirm,data) == 1) {
                if (e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
                    if (DataHandler.get_bool(dataHandler.namespaceKey_Running, data) == 1) {
                        Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Id, data));
                        DataHandler.change_bool(dataHandler.namespaceKey_Running, data, player, null);
                        if (DataHandler.get_bool(dataHandler.namespaceKey_Pause, data) == 1) {
                            DataHandler.change_bool(dataHandler.namespaceKey_Pause, data, player, null);
                        }
                    } else {
                        //start
                        DigPlace digPlace = new DigPlace();
                        DataHandler.change_bool(dataHandler.namespaceKey_Running, data, player, null);
                        DataHandler.save_position(dataHandler.namespacesKey_PosCurrent, data, pos1);
                        digPlace.run_t(player, digger.getConfig().getInt("Time") * 20);
                        DataHandler.save_int(dataHandler.namespaceKey_Task_Next_Time, data, digger.getConfig().getInt("Time") * 20);
                    }
                }
            }
            SelectionScreen gui = new SelectionScreen(player);
            player.openInventory(gui.getInventory());
            selectionScreenRefresh.run_t(player);
        }
    }
    @EventHandler
    public  void leave(InventoryCloseEvent event){
        Player player = (Player)event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        Inventory inv = event.getInventory();
        if (inv == null) {
            return;
        }
        if (inv.getHolder() instanceof ConfirmScreen) {
            if(DataHandler.get_bool(dataHandler.namespaceKey_Await_Confirm,data) == 1){
                DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
            }
        }
    }
}
