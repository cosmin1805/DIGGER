package ro.iacobai.digger.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.tasks.Particles;

public class ParticleCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "particle";
    }

    @Override
    public String getDescription() {
        return "Highlight the selected digger area for 120 seconds";
    }

    @Override
    public String getSyntax() {
        return "/digger particle";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Highlight,data)==0){
            Particles particles = new Particles();
            particles.run_t(player);
            player.sendMessage(ChatColor.AQUA+"---------------------");
            player.sendMessage(ChatColor.GREEN+"The particle highlight has started!");
            player.sendMessage(ChatColor.AQUA+"---------------------");
            DataHandler.change_bool(dataHandler.namespaceKey_Highlight,data,player,null);
        }
        else {
            player.sendMessage(ChatColor.AQUA+"---------------------");
            player.sendMessage(ChatColor.RED+"The particle highlight has stopped!");
            player.sendMessage(ChatColor.AQUA+"---------------------");
            Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Particle_Id,data));
            DataHandler.change_bool(dataHandler.namespaceKey_Highlight,data,player,null);
        }

    }
}
