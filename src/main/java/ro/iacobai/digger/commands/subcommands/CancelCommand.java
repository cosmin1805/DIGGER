package ro.iacobai.digger.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

public class CancelCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "cancel";
    }

    @Override
    public String getDescription() {
        return "Cancel some event";
    }

    @Override
    public String getSyntax() {
        return "/digger cancel";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        int confirm = DataHandler.get_bool(dataHandler.namespaceKey_Confirm,data);
        if(confirm == 1){
            DataHandler.change_bool(dataHandler.namespaceKey_Confirm,data,player,null);
            player.sendMessage(ChatColor.GREEN+"You canceled your /digger start or /digger cancel !");
        }
        else {
            int task_running = DataHandler.get_bool(dataHandler.namespaceKey_Task_Running ,data);
            if(task_running == 1) {
                DataHandler.change_bool(dataHandler.namespaceKey_Confirm,data,player,null);
                player.sendMessage(ChatColor.RED+"YOU WILL NTO RECEIVE ANY MONEY BACK!!");
                player.sendMessage("Confirm this with /digger confirm or cancel it with /digger cancel !");
            }
        }
    }
}
