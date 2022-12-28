package ro.iacobai.digger.blocks;

import org.bukkit.Location;
import org.bukkit.Material;

public class Chest {
    public boolean exists(Location chest_pos){
        Material material = chest_pos.getBlock().getBlockData().getMaterial();
        if(!material.equals(Material.CHEST)){
            return false;
        }
        return true;
    }
    public void get_nearby_entities(){
            
    }
}
