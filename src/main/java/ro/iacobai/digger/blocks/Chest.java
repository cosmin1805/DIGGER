package ro.iacobai.digger.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class Chest {
    public boolean exists(Location chest_pos){
        Material material = chest_pos.getBlock().getBlockData().getMaterial();
        if(!material.equals(Material.CHEST)){
            return false;
        }
        return true;
    }
    public void get_nearby_entities(Location current_pos,Location chest_pos){
        for (Entity entity : current_pos.getNearbyEntities(3, 3, 3))  {
            if (entity instanceof Item) {
                org.bukkit.block.Chest chest_b = (org.bukkit.block.Chest) chest_pos.getBlock().getState();
                chest_b.getInventory().addItem(((Item) entity).getItemStack());
                entity.remove();
            }
        }
    }
}
