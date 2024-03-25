package net.njsharpe.pickmeup.entity;

import net.njsharpe.pickmeup.Constants;
import org.bukkit.*;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Player;

public class StackerEntity {

    // Source = Player who clicked
    // Target = Player who got clicked
    public static void spawn(World world, Location location, Player source, Player target) {
        world.spawn(location, AreaEffectCloud.class, cloud -> {
            cloud.setRadius(0.0F);
            cloud.setGravity(false);
            cloud.setInvulnerable(true);
            cloud.setDuration(Integer.MAX_VALUE);
            cloud.setParticle(Particle.BLOCK_CRACK, Material.AIR.createBlockData());
            cloud.setWaitTime(0);

            cloud.addScoreboardTag(Constants.getStackerEntityTag());

            source.addPassenger(cloud);
            cloud.addPassenger(target);
        });
    }

}
