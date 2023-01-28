package me.arimas.temporaryspawn.commands;

import me.arimas.temporaryspawn.TemporarySpawn;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TemporarySpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if sender has permission and whether they are a player
        if(!sender.hasPermission("temporaryspawn.tempspawn")) {
            return true;
        } if (!(sender instanceof Player)) {
            return true;
        }

        // Get sending player
        Player senderPlayer = (Player) sender;

        // Get player UUID and Location
        UUID senderPlayerUUID = senderPlayer.getUniqueId();
        Location temporarySpawnPoint = new Location(senderPlayer.getWorld(), senderPlayer.getLocation().getBlockX(), senderPlayer.getLocation().getBlockY(), senderPlayer.getLocation().getBlockZ());

        // Set original spawn point if they do not have one
        if (!TemporarySpawn.playerOriginalSpawnPoints.containsKey(senderPlayerUUID)) {
            TemporarySpawn.playerOriginalSpawnPoints.put(senderPlayerUUID, senderPlayer.getBedSpawnLocation());
        }

        // Save and set player temporary spawn point
        TemporarySpawn.playerTemporarySpawnPoints.put(senderPlayerUUID, temporarySpawnPoint);
        senderPlayer.setBedSpawnLocation(temporarySpawnPoint, true);

        // Send message to player
        senderPlayer.sendMessage("Temporary spawn point set to " + temporarySpawnPoint.toString());
        return true;
    }

}