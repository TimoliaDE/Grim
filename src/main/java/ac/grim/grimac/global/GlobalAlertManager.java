package ac.grim.grimac.global;

import ac.grim.grimac.timolia.TimoliaTeam;
import de.timolia.core.redis.TRedis;
import org.bukkit.entity.Player;

public class GlobalAlertManager {

    public static GlobalAlertManager instance = new GlobalAlertManager();
    public static final String CHANNEL = "grim.globalalert";

    private GlobalAlertManager() {
        TRedis.subscribe(CHANNEL, AlertMessage.class, alertMessage -> {
            TimoliaTeam.members().forEach(member -> {
                member.sendMessage(alertMessage.getAlertString());
            });
        });

        instance = this;
    }

    public static GlobalAlertManager instance() {
        return instance;
    }

}
