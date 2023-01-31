package ac.grim.grimac.commands;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.player.GrimPlayer;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("grim|grimac")
public class GrimWatch extends BaseCommand {

    private static String prefix = "§1│ §9Grim§1» §7";

    @Subcommand("watch")
    @CommandPermission("grim.watch")
    @CommandCompletion("@players")
    public void onSpectate(CommandSender sender, @Optional OnlinePlayer target) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;


        if (target == null) {
            player.sendMessage(prefix + "§7Der Spieler ist offline oder existiert nicht.");
            return;
        }

        GrimPlayer targetGrimPlayer = GrimAPI.INSTANCE.getPlayerDataManager().getPlayer(target.getPlayer());

        if (targetGrimPlayer.isWatched(player.getUniqueId())) {
            targetGrimPlayer.removeWatched(player.getUniqueId());
            player.sendMessage(prefix + "Du schaust §6" + target.getPlayer().getName() + " §7nicht mehr zu.");
            return;
        }

        targetGrimPlayer.addWatched(player.getUniqueId());
        player.sendMessage(prefix + "Du schaust jetzt §6" + target.getPlayer().getName() + " §7zu.");

    }

}

