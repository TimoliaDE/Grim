package ac.grim.grimac.timolia;

import ac.grim.grimac.player.GrimPlayer;
import org.bukkit.entity.Player;

public class GrimAdmin extends TimoliaTeam {

    public GrimAdmin(GrimPlayer grimPlayer) {
        super(grimPlayer);
    }

    @Override
    public boolean isHelper() {
        return true;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public String getPermission() {
        return "timolia.grim.admin";
    }

}
