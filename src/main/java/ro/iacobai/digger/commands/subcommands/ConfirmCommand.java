package ro.iacobai.digger.commands.subcommands;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.tasks.DigPlace;

public class ConfirmCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "confirm";
    }

    @Override
    public String getDescription() {
        return "Confirm a digger action";
    }

    @Override
    public String getSyntax() {
        return "/digger confirm";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Confirm,data) == 1){
            if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Running,data) == 1){
                Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Id,data));
                DataHandler.change_bool(dataHandler.namespaceKey_Confirm,data,player,null);
                DataHandler.change_bool(dataHandler.namespaceKey_Task_Running,data,player,null);
                player.sendMessage(ChatColor.GREEN+"DIGGER HAS BEEN CANCELED!");
                return;
            }
            player.sendMessage(ChatColor.GREEN+"DIGGER HAS STARTED!");
            DigPlace digPlace = new DigPlace();
            DataHandler.change_bool(dataHandler.namespaceKey_Task_Running,data,player,null);

            Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
            DataHandler.save_position(dataHandler.namespacesKey_PosCurrent,data,pos1);
            digPlace.run_t(player);
            DataHandler.change_bool(dataHandler.namespaceKey_Confirm,data,player,null);
        }else {
            player.sendMessage(ChatColor.RED+"First do /digger start or /digger stop !");
        }
    }
}
