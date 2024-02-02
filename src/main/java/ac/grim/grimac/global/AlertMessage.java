package ac.grim.grimac.global;

import lombok.Getter;

@Getter
public class AlertMessage {

    public String alertString;

    public AlertMessage(String alertString) {
        this.alertString = alertString;
    }

}
