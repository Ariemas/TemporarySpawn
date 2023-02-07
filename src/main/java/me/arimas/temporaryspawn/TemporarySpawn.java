package me.arimas.temporaryspawn;

import me.arimas.temporaryspawn.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class TemporarySpawn extends JavaPlugin {

    // Map to store player spawn points
    public static Map<UUID, Location> playerOriginalSpawnPoints = new HashMap<>();
    public static Map<UUID, Location> playerTemporarySpawnPoints = new HashMap<>();

    @Override
    public void onEnable() {
        // Register commands
        getCommand("setplayertempspawn").setExecutor(new AdminTemporarySpawnCommand());
        getCommand("resetplayertempspawn").setExecutor(new AdminResetTemporarySpawnCommand());
        getCommand("tempspawn").setExecutor(new TemporarySpawnCommand());
        getCommand("resettempspawn").setExecutor(new ResetTemporarySpawnCommand());
    }

    @Override
    public void onDisable() {
        // Reset and remove data for players temporary spawn point
        for (UUID targetPlayerUUID : TemporarySpawn.playerOriginalSpawnPoints.keySet()) {
            Player targetPlayer = Bukkit.getPlayer(targetPlayerUUID);
            targetPlayer.setBedSpawnLocation(TemporarySpawn.playerOriginalSpawnPoints.get(targetPlayerUUID));
            TemporarySpawn.playerTemporarySpawnPoints.remove(targetPlayerUUID);
            TemporarySpawn.playerOriginalSpawnPoints.remove(targetPlayerUUID);
        }
    }

}