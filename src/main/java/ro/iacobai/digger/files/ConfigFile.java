package ro.iacobai.digger.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFile {
    private static File file;
    private static FileConfiguration fileConfiguration;
    //FIND OR GENERATE THE CONFIG FILE
    public static void setFile(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DIGGER").getDataFolder(),"config.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            }
            catch (IOException e){
                System.out.println("Couldn't create the file");
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return fileConfiguration;
    }

    public static void save(){
        try {
            fileConfiguration.save(file);
        }
        catch (IOException e){
            System.out.println("Couldn't save the file");
        }
    }
    public static void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}
