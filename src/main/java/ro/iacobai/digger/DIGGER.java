package ro.iacobai.digger;



import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ro.iacobai.digger.commands.CommandManager;
import ro.iacobai.digger.commands.TabComplete;

import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.items.ItemManager;
import ro.iacobai.digger.player.OnJoin;
import ro.iacobai.digger.player.OnLeave;
import ro.iacobai.digger.tasks.DigPlace;
import ro.iacobai.digger.tasks.Particles;


public final class DIGGER extends JavaPlugin implements Listener {

    private static DIGGER plugin;
    private static Economy econ = null;
    public static DIGGER getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
            getConfig().options().copyDefaults();
            saveDefaultConfig();
            plugin = this;
            getCommand("digger").setExecutor(new CommandManager());
            getCommand("digger").setTabCompleter(new TabComplete());
            getServer().getPluginManager().registerEvents(new ItemManager(),this);
            getServer().getPluginManager().registerEvents(new OnJoin(),this);
            getServer().getPluginManager().registerEvents(new OnLeave(),this);
            for(Player player : Bukkit.getOnlinePlayers()){
                DataHandler dataHandler = new DataHandler();
                PersistentDataContainer data = player.getPersistentDataContainer();
                if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Running,data)==1 && DataHandler.get_bool(dataHandler.namespaceKey_Task_Pause,data)==0){
                    DigPlace digPlace = new DigPlace();
                    digPlace.run_t(player,DataHandler.get_int(dataHandler.namespaceKey_Task_Next_Time,data));
                    System.out.println("Task of " +player.getName()+" resumed!");
                    player.sendMessage(ChatColor.GREEN+"Your Digger Task was resumed!");
                }
                if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Highlight,data)==1){
                    Particles particles = new Particles();
                    particles.run_t(player);
                    System.out.println("Highlight of " +player.getName()+" resumed!");
                }
            }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

}
