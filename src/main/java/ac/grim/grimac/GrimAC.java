package ac.grim.grimac;

import de.timolia.core.redis.TRedis;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GrimAC extends JavaPlugin {

    public static GrimAC INSTANCE;

    @Override
    public void onLoad() {
        GrimAPI.INSTANCE.load(this);
    }

    @Override
    public void onDisable() {
        GrimAPI.INSTANCE.stop(this);
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        GrimAPI.INSTANCE.start(this);
        Bukkit.getScheduler().runTaskLaterAsynchronously(GrimAC.INSTANCE, () -> {
            TRedis.getSubscriptionHandler().run();
        }, 1);
    }
}
