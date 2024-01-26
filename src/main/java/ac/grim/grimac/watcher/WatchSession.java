package ac.grim.grimac.watcher;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.MessageUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class WatchSession {

    private final GrimPlayer watchedPlayer;
    private final GrimPlayer watcher;

    public static Table<GrimPlayer, GrimPlayer, Set<Check>> watchingPlayers = HashBasedTable.create();

    public static Map<GrimPlayer, WatchSession> watchSessions = new HashMap<>();

    public WatchSession(GrimPlayer watcher, GrimPlayer watchedPlayer) {
        this.watcher = watcher;
        this.watchedPlayer = watchedPlayer;
    }

    public static WatchSession start(GrimPlayer watcher, GrimPlayer watchedPlayer, Set<Check> checks) {
        WatchSession watchSession = new WatchSession(watcher, watchedPlayer);
        add(watcher, watchedPlayer, new HashSet<>(checks));
        watchSessions.put(watcher, watchSession);
        watcher.watchSession(watchSession);
        return watchSession;
    }

    public static WatchSession get(GrimPlayer watcher) {
        return watchSessions.get(watcher);
    }

    private static void message(GrimPlayer watcher, Check check, String value) {
        Player player = watcher.bukkitPlayer;
        String message = GrimAPI.INSTANCE.getConfigManager().getConfig().getString("watch-alert");

        if (player != null && player.isOnline()) {
            player.sendMessage(MessageUtil.format(message + " " + check.getCheckName() + " -> " + value));
        }
    }

    public static void messageToWatchers(GrimPlayer watchingPlayer, Check check, String value) {
        Set<GrimPlayer> watchers = watchingPlayers.rowKeySet();
        watchers.forEach(watcher -> {
            Set<Check> checks = getActiveChecks(watcher, watchingPlayer);
            if (checks.contains(check)) {
                message(watcher, check, value);
            }
        });
    }

    private static void add(GrimPlayer watcher, GrimPlayer watchedPlayer, Set<Check> checks) {
        watchingPlayers.put(watcher, watchedPlayer, checks);
    }

    private void addCheck(Check check) {
        Set<Check> newCheckSet = getActiveChecks(watcher, watchedPlayer);
        newCheckSet.add(check);
        watchingPlayers.put(watcher, watchedPlayer, newCheckSet);
    }

    private void removeCheck(Check check) {
        Set<Check> newCheckSet = getActiveChecks(watcher, watchedPlayer);
        newCheckSet.remove(check);
        watchingPlayers.put(watcher, watchedPlayer, newCheckSet);
    }

    public static Set<Check> getActiveChecks(GrimPlayer watcher, GrimPlayer watchedPlayer) {
        return watchingPlayers.get(watcher, watchedPlayer);
    }
}
