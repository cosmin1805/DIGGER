package ro.iacobai.digger.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

public class PauseCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getDescription() {
        return "Pause a running digger!";
    }

    @Override
    public String getSyntax() {
        return "/digger pause";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==0 && DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1){
            Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Id,data));
            DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
            player.sendMessage(ChatColor.AQUA+"---------------------");
            player.sendMessage(ChatColor.GREEN+"The digger has been paused!");
            player.sendMessage(ChatColor.AQUA+"---------------------");
        }
        else {
            player.sendMessage(ChatColor.AQUA+"---------------------");
            player.sendMessage(ChatColor.RED+"Nothing to pause or the digger is already paused!");
            player.sendMessage(ChatColor.AQUA+"---------------------");
        }
    }

}
