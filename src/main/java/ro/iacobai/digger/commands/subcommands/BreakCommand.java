package ro.iacobai.digger.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

public class BreakCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "break";
    }

    @Override
    public String getDescription() {
        return "If items should break or not!";
    }

    @Override
    public String getSyntax() {
        return "/digger break";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        player.sendMessage(ChatColor.AQUA+"---------------------");
        DataHandler.change_bool(dataHandler.namespaceKey_Use_Break,data,player,"Digger break tool: ");
        player.sendMessage(ChatColor.AQUA+"---------------------");
    }
}
