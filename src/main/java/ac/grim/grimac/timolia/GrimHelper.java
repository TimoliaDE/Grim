package ac.grim.grimac.timolia;

import ac.grim.grimac.player.GrimPlayer;

public class GrimHelper extends TimoliaTeam {

    public GrimHelper(GrimPlayer grimPlayer) {
        super(grimPlayer);
    }

    @Override
    public boolean isHelper() {
        return true;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public String getPermission() {
        return "timolia.grim.helper";
    }
}
