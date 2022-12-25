package ro.iacobai.digger;



import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ro.iacobai.digger.commands.CommandManager;
import ro.iacobai.digger.items.ItemManager;


public final class DIGGER extends JavaPlugin implements Listener {

    private static DIGGER plugin;
    public static DIGGER getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
            plugin = this;
            getCommand("digger").setExecutor(new CommandManager());
            getServer().getPluginManager().registerEvents(new ItemManager(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
