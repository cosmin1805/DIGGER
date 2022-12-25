package ro.iacobai.digger.data;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class DataHandler {
    public static void change_bool(NamespacedKey namespacedKey, PersistentDataContainer data, Player player, String message) {
        if (!data.has(namespacedKey, PersistentDataType.INTEGER)) {
            data.set(namespacedKey, PersistentDataType.INTEGER, 0);
        }
        int data_select = data.get(namespacedKey, PersistentDataType.INTEGER);
        if (data_select == 1) {
            data.set(namespacedKey, PersistentDataType.INTEGER, 0);
            if(message != null)
                player.sendMessage(ChatColor.RED + message + "OFF");
        } else {
            data.set(namespacedKey, PersistentDataType.INTEGER, 1);
            if(message != null)
                player.sendMessage(ChatColor.GREEN + message + "ON");
        }
    }
    public  static int  get_bool(NamespacedKey namespacedKey, PersistentDataContainer data){
        if (!data.has(namespacedKey, PersistentDataType.INTEGER)) {
            data.set(namespacedKey, PersistentDataType.INTEGER, 0);
        }
        return data.get(namespacedKey, PersistentDataType.INTEGER);
    }
    public static void save_position(NamespacedKey namespacedKey, PersistentDataContainer data, Location location){
        int[] pos = new int[3];
        pos[0] = (int)location.getX();
        pos[1] = (int)location.getY();
        pos[2] = (int)location.getZ();
        data.set(namespacedKey, PersistentDataType.INTEGER_ARRAY, pos);
    }
    public static Location get_position(NamespacedKey namespacedKey, PersistentDataContainer data){
        World world = Bukkit.getWorld("world");
        if (!data.has(namespacedKey, PersistentDataType.INTEGER_ARRAY)) {
            Location location = new Location(world,0,0,0);
            save_position(namespacedKey,data,location);
            return location;
        }
        int[] pos = data.get(namespacedKey, PersistentDataType.INTEGER_ARRAY);
        Location location = new Location(world,pos[0],pos[1],pos[2]);
        return location;
    }
    public static void save_price(NamespacedKey namespacedKey, PersistentDataContainer data,double price){
        data.set(namespacedKey, PersistentDataType.DOUBLE, price);
    }
    public static double get_price(NamespacedKey namespacedKey, PersistentDataContainer data){
        if (!data.has(namespacedKey, PersistentDataType.DOUBLE)) {
            double price = 0;
            save_price(namespacedKey,data,price);
            return price;
        }
        double price = data.get(namespacedKey, PersistentDataType.DOUBLE);
        return price;
    }
}
