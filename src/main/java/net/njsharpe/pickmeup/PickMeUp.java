package net.njsharpe.pickmeup;

import lombok.Getter;
import net.njsharpe.pickmeup.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PickMeUp extends JavaPlugin {

    @Getter
    private static PickMeUp instance;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
        super.onDisable();
    }

}
