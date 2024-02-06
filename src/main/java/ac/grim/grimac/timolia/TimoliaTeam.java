package ac.grim.grimac.timolia;

import ac.grim.grimac.player.GrimPlayer;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class TimoliaTeam implements Listener {

    protected Player player;
    @Getter public GrimPlayer grimPlayer;

    public static Map<GrimPlayer, TimoliaTeam> teamMembers = new HashMap<>();

    public TimoliaTeam(GrimPlayer grimPlayer) {
        this.grimPlayer = grimPlayer;
        this.player = grimPlayer.bukkitPlayer;
        teamMembers.put(grimPlayer, this);
    }

    public TimoliaTeam() {}


    protected abstract boolean isHelper();

    protected abstract boolean isAdmin();

    protected abstract String getPermission();

    private boolean hasPermission() {
        assert player != null;
        return player.hasPermission(getPermission());
    }

    public static Set<GrimPlayer> members() {
        return teamMembers.keySet();
    }

}
