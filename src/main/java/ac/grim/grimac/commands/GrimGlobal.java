package ac.grim.grimac.commands;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.GrimExternalAPI;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.timolia.TimoliaTeam;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import de.timolia.core.redis.TRedis;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.TimeUnit;

@CommandAlias("grim|grimac")
public class GrimGlobal implements Listener {

    public static final String globalKey = "grim:global:";

    @Subcommand("global")
    @CommandPermission("grim.global")
    public void onGlobal(Player player) {
        TRedis.execute(jedis -> {
            String playerKey = globalKey + player.getUniqueId();
            if (!jedis.exists(playerKey)) {
                jedis.set(playerKey, "active");
                jedis.expire(playerKey, (int) TimeUnit.DAYS.toSeconds(1));
                return null;
            }

            boolean active = jedis.get(playerKey).equals("active");
            jedis.set(playerKey, active ? "inactive" : "active");

            return null;
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        GrimPlayer grimPlayer = GrimAPI.INSTANCE.getPlayerDataManager().getPlayer(event.getPlayer());
        TimoliaTeam.teamMembers.remove(grimPlayer);
    }

}
