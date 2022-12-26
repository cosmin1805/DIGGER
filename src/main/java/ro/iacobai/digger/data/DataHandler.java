package ro.iacobai.digger.data;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import ro.iacobai.digger.DIGGER;


public class DataHandler {

    //ALL THE "BOOLEANS"
    public NamespacedKey namespacesKey_Pos = new NamespacedKey(DIGGER.getPlugin(),"pos_select");
    public NamespacedKey namespaceKey_Chest = new NamespacedKey(DIGGER.getPlugin(),"chest_select");
    public NamespacedKey namespaceKey_Use_Chest = new NamespacedKey(DIGGER.getPlugin(),"use_chest");
    public NamespacedKey namespaceKey_Confirm = new NamespacedKey(DIGGER.getPlugin(),"task_await_confirm");
    public NamespacedKey namespaceKey_Task_Running = new NamespacedKey(DIGGER.getPlugin(),"task_running");
    //ALL THE LOCATIONS
    public NamespacedKey namespaceKey_Pos1 = new NamespacedKey(DIGGER.getPlugin(),"task_pos1");
    public NamespacedKey namespaceKey_Pos2 = new NamespacedKey(DIGGER.getPlugin(),"task_pos2");
    public NamespacedKey namespaceKey_PosChest = new NamespacedKey(DIGGER.getPlugin(),"chest_pos");
    public NamespacedKey namespacesKey_PosCurrent = new NamespacedKey(DIGGER.getPlugin(),"current_pos");
    //ALL THE INTEGERS AND DOUBLE DATA
    public NamespacedKey namespaceKey_Price = new NamespacedKey(DIGGER.getPlugin(),"task_price");
    public NamespacedKey namespaceKey_Task_Blocks = new NamespacedKey(DIGGER.getPlugin(),"task_blocks");
    public NamespacedKey namespaceKey_Task_Id = new NamespacedKey(DIGGER.getPlugin(),"task_id");

    public static void change_bool(NamespacedKey namespacedKey, PersistentDataContainer data, Player player, String message) {
        if (!data.has(namespacedKey, PersistentDataType.INTEGER)) {
            data.set(namespacedKey, PersistentDataType.INTEGER, 0);
            if(message != null)
                player.sendMessage(ChatColor.RED + message + "OFF");
            return;
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
    public static void save_double(NamespacedKey namespacedKey, PersistentDataContainer data,double value){
        data.set(namespacedKey, PersistentDataType.DOUBLE, value);
    }
    public static double get_double(NamespacedKey namespacedKey, PersistentDataContainer data){
        if (!data.has(namespacedKey, PersistentDataType.DOUBLE)) {
            double value = 0;
            save_double(namespacedKey,data,value);
            return value;
        }
        double value = data.get(namespacedKey, PersistentDataType.DOUBLE);
        return value;
    }
    public static void save_int(NamespacedKey namespacedKey, PersistentDataContainer data,int value){
        data.set(namespacedKey, PersistentDataType.INTEGER, value);
    }
    public static int get_int(NamespacedKey namespacedKey, PersistentDataContainer data){
        if (!data.has(namespacedKey, PersistentDataType.INTEGER)) {
            int value = 0;
            save_double(namespacedKey,data,value);
            return value;
        }
        int value = data.get(namespacedKey, PersistentDataType.INTEGER);
        return value;
    }
}
