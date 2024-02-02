package ac.grim.grimac.timolia;

import ac.grim.grimac.player.GrimPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class TimoliaTeam {

    protected final Player player;
    protected final GrimPlayer grimPlayer;

    public static Map<Player, TimoliaTeam> teamMembers = new HashMap<>();

    public TimoliaTeam(GrimPlayer grimPlayer) {
        this.grimPlayer = grimPlayer;
        this.player = grimPlayer.bukkitPlayer;
        teamMembers.put(player, this);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        teamMembers.remove(player);
    }

    protected abstract boolean isHelper();

    protected abstract boolean isAdmin();

    protected abstract String getPermission();

    private boolean hasPermission() {
        assert player != null;
        return player.hasPermission(getPermission());
    }

    public static Set<Player> members() {
        return teamMembers.keySet();
    }

}
