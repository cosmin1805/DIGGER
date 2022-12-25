package ro.iacobai.digger.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

public class CancelCommand extends SubCommand {
    NamespacedKey namespacedKey_Confirm = new NamespacedKey(DIGGER.getPlugin(),"task_await_confirm");
    @Override
    public String getName() {
        return "cancel";
    }

    @Override
    public String getDescription() {
        return "Cancel something!";
    }

    @Override
    public String getSyntax() {
        return "/digger cancel";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        int confirm = DataHandler.get_bool(namespacedKey_Confirm,data);
        if(confirm == 1){
            DataHandler.change_bool(namespacedKey_Confirm,data,player,null);
            player.sendMessage(ChatColor.GREEN+"You cancel your /digger start!");
        }
        else {

        }
    }
}
