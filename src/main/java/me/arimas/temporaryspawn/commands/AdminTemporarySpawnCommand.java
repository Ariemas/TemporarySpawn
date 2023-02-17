package me.arimas.temporaryspawn.commands;

import me.arimas.temporaryspawn.TemporarySpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AdminTemporarySpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if sender has permission
        if (!sender.hasPermission("temporaryspawn.setplayertempspawn")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        int argsIndex = args.length - 1;

        // Parse world
        World temporaryWorld;
        try {
            temporaryWorld = Bukkit.getWorld(args[argsIndex]);
            if (temporaryWorld == null) {
                throw new NullPointerException();
            }
            argsIndex--;
        } catch (Exception exception) {
            if (sender instanceof Player) {
                Player senderPlayer = (Player) sender;
                temporaryWorld = senderPlayer.getWorld();
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Usage: /setplayertempspawn [player(s)] [x] [y] [z] [yaw] [pitch] [world]");
                return true;
            }
        }

        // Parse pitch and yaw
        float pitch, yaw;
        try {
            pitch = Float.parseFloat(args[(argsIndex - 1)]);
            yaw = Float.parseFloat(args[(argsIndex)]);
            argsIndex -= 2;
        } catch (Exception exception) {
            if (sender instanceof Player) {
                Player senderPlayer = (Player) sender;
                pitch = senderPlayer.getLocation().getPitch();
                yaw = senderPlayer.getLocation().getYaw();
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Usage: /setplayertempspawn [player(s)] [x] [y] [z] [yaw] [pitch] [world]");
                return true;
            }
        }

        // Parse coordinates
        int x, y, z;
        try {
            x = Integer.parseInt(args[(argsIndex - 2)]);
            y = Integer.parseInt(args[(argsIndex - 1)]);
            z = Integer.parseInt(args[(argsIndex)]);
            argsIndex -= 3;
        } catch (Exception exception) {
            if (sender instanceof Player) {
                Player senderPlayer = (Player) sender;
                x = senderPlayer.getLocation().getBlockX();
                y = senderPlayer.getLocation().getBlockY();
                z = senderPlayer.getLocation().getBlockZ();
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Usage: /setplayertempspawn [player(s)] [x] [y] [z] [yaw] [pitch] [world]");
                return true;
            }
        }

        // Get target players
        Set<Player> targetPlayers = new HashSet<>();
        if (argsIndex >= 0) {
            for (int i = 0; i <= argsIndex; i++) {
                Player targetPlayer = Bukkit.getPlayer(args[i]);
                if (targetPlayer == null) {
                    sender.sendMessage(ChatColor.YELLOW + "Player not found: " + args[i]);
                    continue;
                }
                targetPlayers.add(targetPlayer);
            }
        } else {
            if (sender instanceof Player) {
                Player senderPlayer = (Player) sender;
                targetPlayers.add(senderPlayer);
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Usage: /setplayertempspawn [player(s)] [x] [y] [z] [yaw] [pitch] [world]");
            }
        }

        // Set player temporary spawn points
        Location temporarySpawnPoint = new Location(temporaryWorld, x, y, z, yaw, pitch);
        for (Player targetPlayer : targetPlayers) {
            UUID targetPlayerUUID = targetPlayer.getUniqueId();

            // Set original spawn point if they do not have one
            if (!TemporarySpawn.playerOriginalSpawnPoints.containsKey(targetPlayerUUID)) {
                TemporarySpawn.playerOriginalSpawnPoints.put(targetPlayerUUID, targetPlayer.getBedSpawnLocation());
            }

            targetPlayer.setBedSpawnLocation(temporarySpawnPoint, true);
            TemporarySpawn.playerTemporarySpawnPoints.put(targetPlayerUUID, temporarySpawnPoint);
            // Send message to target player
            targetPlayer.sendMessage(ChatColor.GREEN + "Temporary spawn point set to (" + targetPlayer.getBedSpawnLocation().getBlockX()+", "+targetPlayer.getBedSpawnLocation().getBlockY()+", "+targetPlayer.getBedSpawnLocation().getBlockZ()+") in " + targetPlayer.getBedSpawnLocation().getWorld().getName());
        }

        // Send message to sender
        sender.sendMessage(ChatColor.GREEN + "The temporary spawn points have been set.");
        return true;
    }

}