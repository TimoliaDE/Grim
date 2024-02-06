package ac.grim.grimac.global;

import ac.grim.grimac.commands.GrimGlobal;
import ac.grim.grimac.timolia.TimoliaTeam;
import de.timolia.core.redis.TRedis;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class GlobalAlertManager {

    private static GlobalAlertManager instance;
    public static final String CHANNEL = "grim.globalalert";

    private GlobalAlertManager() {
        TRedis.subscribe(CHANNEL, AlertMessage.class, alertMessage ->
                TimoliaTeam.members().forEach(member -> {
                    if (member.globalAlerts) {
                        assert member.bukkitPlayer != null;
                        member.bukkitPlayer.sendMessage(alertMessage.getAlertString());
            }
        }));

        instance = this;
    }

    public static GlobalAlertManager instance() {
        if (instance == null) {
            instance = new GlobalAlertManager();
        }

        return instance;
    }

}
