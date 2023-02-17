package me.arimas.temporaryspawn.commands;

import me.arimas.temporaryspawn.TemporarySpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ResetTemporarySpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if sender has permission and whether they are a player
        if(!sender.hasPermission("temporaryspawn.resettempspawn")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        } if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        // Get player UUID
        Player senderPlayer = (Player) sender;
        UUID targetPlayerUUID = senderPlayer.getUniqueId();

        // Check if they have a temporary spawn
        if (!TemporarySpawn.playerOriginalSpawnPoints.containsKey(targetPlayerUUID)) {
            sender.sendMessage(ChatColor.RED + "You do not have a temporary spawn point set!");
            return true;
        }

        // Reset and remove data for players temporary spawn point
        senderPlayer.setBedSpawnLocation(TemporarySpawn.playerOriginalSpawnPoints.get(targetPlayerUUID));
        TemporarySpawn.playerTemporarySpawnPoints.remove(targetPlayerUUID);
        TemporarySpawn.playerOriginalSpawnPoints.remove(targetPlayerUUID);

        // Send message to player
        sender.sendMessage(ChatColor.GREEN + "Temporary spawn point reset.");
        return true;
    }

}