package me.arimas.temporaryspawn.commands;

import me.arimas.temporaryspawn.TemporarySpawn;
import org.bukkit.ChatColor;
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
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        } if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        // Get sending player
        Player senderPlayer = (Player) sender;

        // Get player UUID and Location
        UUID senderPlayerUUID = senderPlayer.getUniqueId();
        Location temporarySpawnPoint = new Location(senderPlayer.getWorld(), senderPlayer.getLocation().getBlockX(), senderPlayer.getLocation().getBlockY(), senderPlayer.getLocation().getBlockZ(), senderPlayer.getLocation().getYaw(), senderPlayer.getLocation().getPitch());

        // Set original spawn point if they do not have one
        if (!TemporarySpawn.playerOriginalSpawnPoints.containsKey(senderPlayerUUID)) {
            TemporarySpawn.playerOriginalSpawnPoints.put(senderPlayerUUID, senderPlayer.getBedSpawnLocation());
        }

        // Save and set player temporary spawn point
        TemporarySpawn.playerTemporarySpawnPoints.put(senderPlayerUUID, temporarySpawnPoint);
        senderPlayer.setBedSpawnLocation(temporarySpawnPoint, true);

        // Send message to player
        sender.sendMessage(ChatColor.GREEN + "Temporary spawn point set to (" + senderPlayer.getBedSpawnLocation().getBlockX()+", "+senderPlayer.getBedSpawnLocation().getBlockY()+", "+senderPlayer.getBedSpawnLocation().getBlockZ()+") in " + senderPlayer.getBedSpawnLocation().getWorld().getName());
        return true;
    }

}