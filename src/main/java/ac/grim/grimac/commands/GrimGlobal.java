package ac.grim.grimac.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import de.timolia.core.redis.TRedis;
import org.bukkit.entity.Player;

@CommandAlias("grim|grimac")
public class GrimGlobal {

    public static final String globalKey = "grim:global:";

    @Subcommand("global")
    @CommandPermission("grim.global")
    public void onGlobal(Player player) {
        TRedis.execute(jedis -> {
            String playerKey = globalKey + player.getUniqueId();

            return null;
        });
    }

}
