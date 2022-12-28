package ro.iacobai.digger.commands.subcommands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import org.bukkit.entity.Player;

import org.bukkit.persistence.PersistentDataContainer;

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
    DIGGER digger = DIGGER.getPlugin();

    @Override
    public void perform(Player player, String[] args) {
        //
        PersistentDataContainer data = player.getPersistentDataContainer();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);

        Location chest_pos = DataHandler.get_position(dataHandler.namespaceKey_PosChest,data);
        Location hopper_pos = DataHandler.get_position(dataHandler.namespaceKey_PosHopper,data);
        DIGGER plugin = DIGGER.getPlugin();
        //
        if(DataHandler.get_bool(dataHandler.namespaceKey_Confirm,data) == 1){
            if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Running,data) == 1){
                Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Id,data));
                DataHandler.change_bool(dataHandler.namespaceKey_Confirm,data,player,null);
                DataHandler.change_bool(dataHandler.namespaceKey_Task_Running,data,player,null);
                player.sendMessage(ChatColor.GREEN+"DIGGER HAS BEEN CANCELED!");
                if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Pause,data)==1) {
                    DataHandler.change_bool(dataHandler.namespaceKey_Task_Pause,data,player,null);
                }
                return;
            }
            //start
            player.sendMessage(ChatColor.GREEN+"DIGGER HAS STARTED!");
            DigPlace digPlace = new DigPlace();
            DataHandler.change_bool(dataHandler.namespaceKey_Task_Running,data,player,null);
            DataHandler.save_position(dataHandler.namespacesKey_PosCurrent,data,pos1);
            digPlace.run_t(player,digger.getConfig().getInt("Time") *20);
            DataHandler.change_bool(dataHandler.namespaceKey_Confirm,data,player,null);
            DataHandler.save_int(dataHandler.namespaceKey_Task_Next_Time,data,digger.getConfig().getInt("Time") *20);
        }else {
            player.sendMessage(ChatColor.RED+"First do /digger start or /digger stop !");
        }
    }
}
