package ro.iacobai.digger.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.tasks.DigPlace;

public class ResumeCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    DigPlace digPlace = new DigPlace();
    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getDescription() {
        return "Resume if your digger is on pause!";
    }

    @Override
    public String getSyntax() {
        return "/digger resume";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Pause,data)==1 && DataHandler.get_bool(dataHandler.namespaceKey_Task_Running,data)==1){
            DataHandler.change_bool(dataHandler.namespaceKey_Task_Pause,data,player,null);
            digPlace.run_t(player,DataHandler.get_int(dataHandler.namespaceKey_Task_Next_Time,data));
            player.sendMessage(ChatColor.GREEN+"Digger resumed!");
        }
        else {
            player.sendMessage(ChatColor.RED+"Nothing to resume!");
        }
    }
}
