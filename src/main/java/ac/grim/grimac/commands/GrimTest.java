package ac.grim.grimac.commands;

import ac.grim.grimac.AbstractCheck;
import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.checks.impl.combat.Reach;
import ac.grim.grimac.player.GrimPlayer;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("grim|grimac")
public class GrimTest extends BaseCommand {

    @Subcommand("test")
    @CommandPermission("grim.test")
    public void onTest(CommandSender sender) {
        Player player = null;
        if(sender instanceof Player) player = (Player) sender;

        if(player == null) return;

        GrimPlayer grimPlayer = GrimAPI.INSTANCE.getPlayerDataManager().getPlayer(player);

        for(AbstractCheck check : grimPlayer.checkManager.allChecks.values()) {

            if(check instanceof Reach) {
                player.sendMessage("§7Skipped Reach...");
                continue;
            }

            check.setEnabled(false);
            player.sendMessage("§cDisabled check " + check.getCheckName());

        }
    }

}
