package ac.grim.grimac.commands;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.MessageUtil;
import ac.grim.grimac.watcher.WatchSession;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@CommandAlias("grim|grimac")
public class GrimWatch {

    @Subcommand("watch")
    @CommandPermission("grim.watch")
    public void onWatch(Player player, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            String message = GrimAPI.INSTANCE.getConfigManager().getConfig().getString("player-not-found");
            player.sendMessage(MessageUtil.format(message));
            return;
        }

        GrimPlayer watcher = GrimAPI.INSTANCE.getPlayerDataManager().getPlayer(player);
        GrimPlayer watchingPlayer = GrimAPI.INSTANCE.getPlayerDataManager().getPlayer(target);
        Set<Check> checks = new HashSet<>();

        for (int i = 1; i < args.length; i++) {
            String checkName = args[i];
            Check check = Check.checkByName.get(checkName);

            if (check != null) {
                checks.add(check);
            }
        }

        WatchSession.start(watcher, watchingPlayer, checks);
        String message = GrimAPI.INSTANCE.getConfigManager().getConfig().getString("watching-player");
        String checkString = checks.stream().map(Check::getCheckName).collect(Collectors.joining(", "));
        player.sendMessage(MessageUtil.format(message + "(" + checkString + ")"));
    }

}
