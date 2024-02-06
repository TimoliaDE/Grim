package ac.grim.grimac.global;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class AlertMessage {

    public String alertString;

    public AlertMessage(String alertString) {
        this.alertString = Bukkit.getServer().getName() + ": " + alertString;
    }

}
