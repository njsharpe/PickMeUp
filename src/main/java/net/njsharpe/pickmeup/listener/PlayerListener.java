package net.njsharpe.pickmeup.listener;

import net.njsharpe.pickmeup.Constants;
import net.njsharpe.pickmeup.entity.StackerEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class PlayerListener implements Listener {

    @EventHandler
    public void onRightClickPlayer(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clicked = event.getRightClicked();
        EquipmentSlot hand = event.getHand();

        // Clicked entity must be a player
        if(!(clicked instanceof Player target)) {
            return;
        }

        // Clicking player's hand must be the main hand
        if(hand != EquipmentSlot.HAND) {
            return;
        }

        PlayerInventory inventory = player.getInventory();
        ItemStack item = inventory.getItem(hand);

        // Item in clicking player's hand must be null or air
        if(item != null && !item.getType().isAir()) {
            return;
        }

        // Clicking player must be sneaking
        if(!player.isSneaking()) {
            return;
        }

        // Add the spacer seat entity then the target player the to
        // the clicking player's passenger list
        StackerEntity.spawn(player.getWorld(), player.getLocation(), player, target);
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        if(!event.isSneaking()) {
            return;
        }

        this.removePassengers(player);
        this.removeVehicles(player);
    }

    private void removePassengers(Entity entity) {
        List<Entity> passengers = entity.getPassengers();
        for(Entity passenger : passengers) {
            if(!passenger.getScoreboardTags().contains(Constants.getStackerEntityTag())) {
                continue;
            }

            this.removePassengers(passenger);

            passenger.remove();
        }
    }

    private void removeVehicles(Entity entity) {
        Entity vehicle = entity.getVehicle();

        if(vehicle == null) {
            return;
        }

        if(!vehicle.getScoreboardTags().contains(Constants.getStackerEntityTag())) {
            return;
        }

        this.removeVehicles(vehicle);

        vehicle.remove();
    }

}
