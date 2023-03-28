package me.arimas.tempspawn;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class TempSpawn extends JavaPlugin {
    private HashMap<UUID, Location> originalSpawnPoints;

@Override
public void onEnable() {
    originalSpawnPoints = new HashMap<>();
    this.getCommand("settempspawn").setExecutor(new SetTempSpawnCommand());
    this.getCommand("resettempspawn").setExecutor(new ResetTempSpawnCommand());
}

private boolean isPlayer(CommandSender sender) {
    if (!(sender instanceof Player)) {
        sender.sendMessage("§cOnly players can use this command!");
        return false;
    }
    return true;
}

public class SetTempSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        if (args.length > 0 && player.hasPermission("tempspawn.adminset")) {
            setTempSpawnForTarget(player, args[0]);
        } else {
            setTempSpawnForSelf(player);
        }

        return true;
    }

    private void setTempSpawnForSelf(Player player) {
        if (!player.hasPermission("tempspawn.set")) {
            player.sendMessage("§cYou do not have permission to use this command.");
            return;
        }

        UUID playerUUID = player.getUniqueId();
        if (originalSpawnPoints.containsKey(playerUUID)) {
            player.setBedSpawnLocation(player.getLocation(), true);
            player.sendMessage("§aTemporary spawn point updated!");
        } else {
            originalSpawnPoints.put(playerUUID, player.getBedSpawnLocation());
            player.setBedSpawnLocation(player.getLocation(), true);
            player.sendMessage("§aTemporary spawn point set!");
        }
    }

    private void setTempSpawnForTarget(Player player, String targetName) {
        Player target = getServer().getPlayerExact(targetName);
        if (target != null) {
            UUID targetUUID = target.getUniqueId();

            if (originalSpawnPoints.containsKey(targetUUID)) {
                target.setBedSpawnLocation(player.getLocation(), true);
                player.sendMessage("§aTemporary spawn point updated for §6" + target.getName());
                target.sendMessage("§eAn admin has updated your temporary spawn point.");
            } else {
                originalSpawnPoints.put(targetUUID, target.getBedSpawnLocation());
                target.setBedSpawnLocation(player.getLocation(), true);
                player.sendMessage("§aTemporary spawn point set for §6" + target.getName());
                target.sendMessage("§eAn admin has set your temporary spawn point.");
            }
        } else {
            player.sendMessage("§cPlayer not found!");
        }
    }
}

public class ResetTempSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        if (args.length > 0 && player.hasPermission("tempspawn.adminreset")) {
            resetTempSpawnForTarget(player, args[0]);
        } else {
            resetTempSpawnForSelf(player);
        }

        return true;
    }

    private void resetTempSpawnForSelf(Player player) {
        if (!player.hasPermission("tempspawn.reset")) {
            player.sendMessage("§cYou do not have permission to use this command.");
            return;
        }

        UUID playerUUID = player.getUniqueId();
        if (!originalSpawnPoints.containsKey(playerUUID)) {
            player.sendMessage("§cYou do not have a temporary spawn point set.");
return;
}
player.setBedSpawnLocation(originalSpawnPoints.get(playerUUID), true);
originalSpawnPoints.remove(playerUUID);
player.sendMessage("§aTemporary spawn point reset!");
}
    private void resetTempSpawnForTarget(Player player, String targetName) {
        Player target = getServer().getPlayerExact(targetName);

        if (target != null && originalSpawnPoints.containsKey(target.getUniqueId())) {
            UUID targetUUID = target.getUniqueId();
            target.setBedSpawnLocation(originalSpawnPoints.get(targetUUID), true);
            originalSpawnPoints.remove(targetUUID);
            player.sendMessage("§aTemporary spawn point reset for §6" + target.getName());
            target.sendMessage("§eAn admin has reset your temporary spawn point.");
        } else {
            player.sendMessage("§cThat player does not have a temporary spawn set.");
        }
    }
}

@Override
public void onDisable() {
    originalSpawnPoints.clear();
}
}