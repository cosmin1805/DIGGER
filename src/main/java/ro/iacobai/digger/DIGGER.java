package ro.iacobai.digger;



import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ro.iacobai.digger.commands.CommandManager;
import ro.iacobai.digger.items.ItemManager;




public final class DIGGER extends JavaPlugin implements Listener {

    private static DIGGER plugin;
    private static Economy econ = null;
    public static DIGGER getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
            if (!setupEconomy() ) {
                System.out.println("No economy plugin found!Disabeling Vault!");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            plugin = this;
            getCommand("digger").setExecutor(new CommandManager());
            getServer().getPluginManager().registerEvents(new ItemManager(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }
}
