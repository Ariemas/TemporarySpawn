// Same as AdminResetTemporarySpawnCommand.java, but without the message to the target player

package me.arimas.temporaryspawn.commands;

import me.arimas.temporaryspawn.TemporarySpawn;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AdminSilentResetTemporarySpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Check if sender has permission
        if (!sender.hasPermission("temporaryspawn.silentresetplayertempspawn")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        // Get target players
        Set<Player> targetPlayers = new HashSet<>();
        if (args.length > 0) {
            for (String arg : args) {
                Player targetPlayer = Bukkit.getPlayer(arg);
                if (targetPlayer == null) {
                    sender.sendMessage("Player not found: " + arg);
                    continue;
                }
                if (!TemporarySpawn.playerOriginalSpawnPoints.containsKey(targetPlayer.getUniqueId()))
                {
                    sender.sendMessage("Player does not have a temporary spawn: " + arg);
                }
                targetPlayers.add(targetPlayer);
            }
        } else {
            if (sender instanceof Player) {
                Player senderPlayer = (Player) sender;
                targetPlayers.add(senderPlayer);
            } else {
                sender.sendMessage("Usage: /resetplayertempspawn [player(s)]");
                return true;
            }
        }

        // Remove player temporary spawn points
        for (Player targetPlayer : targetPlayers) {
            UUID targetPlayerUUID = targetPlayer.getUniqueId();
            targetPlayer.setBedSpawnLocation(TemporarySpawn.playerOriginalSpawnPoints.get(targetPlayerUUID));
            TemporarySpawn.playerTemporarySpawnPoints.remove(targetPlayerUUID);
            TemporarySpawn.playerOriginalSpawnPoints.remove(targetPlayerUUID);
        }

        // Send message to sender
        sender.sendMessage("Temporary spawn points reset for specified players.");
        return true;

    }

}
