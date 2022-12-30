package ro.iacobai.digger.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

public class ChestCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "chest";
    }

    @Override
    public String getDescription() {
        return "Select if you want tu use a chest or not, default it doesn't use one!";
    }

    @Override
    public String getSyntax() {
        return "/digger chest";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        player.sendMessage(ChatColor.AQUA+"---------------------");
        DataHandler.change_bool(dataHandler.namespaceKey_Use_Chest,data,player,"Digger use chest ");
        player.sendMessage(ChatColor.AQUA+"---------------------");
    }
}
