# TemporarySpawn (v1.3)
This is a Minecraft plugin that allows players to set and reset their own temporary spawn points, as well as allows server administrators to set and reset temporary spawn points for other players.

## Usage
Setting a Temporary Spawn Point:
Players can set their own temporary spawn point by typing /tempspawn in-game.
This will save the player's current location as their temporary spawn point, and set their bed spawn point to this location.
If the player does not have a bed spawn point set, the plugin will save their original spawn point and set it when they reset their temporary spawn point.

Resetting a Temporary Spawn Point:
Players can reset their own temporary spawn point by typing /resettempspawn in-game.
This will set the player's bed spawn point back to their original spawn point.

Setting a Temporary Spawn Point for another player:
Server administrators can set a temporary spawn point for another player by typing /setplayertempspawn [player(s)] [x] [y] [z] [yaw] [pitch] [world] in-game.
This will save the target player's current location as their temporary spawn point, and set their bed spawn point to this location.
If the target player does not have a bed spawn point set, the plugin will save their original spawn point and set it when they reset their temporary spawn point.

Resetting a Temporary Spawn Point for another player:
Server administrators can reset a temporary spawn point for another player by typing /resetplayertempspawn [player(s)] [x] [y] [z] [yaw] [pitch] [world] in-game.
This will set the target player's bed spawn point back to their original spawn point.

Setting a Temporary Spawn Point for another player without informing them:
Server administrators can set a temporary spawn point for another player without informing them by typing /setplayertempspawn [player(s)] [x] [y] [z] [yaw] [pitch] [world] silent in-game.
This will save the target player's current location as their temporary spawn point, and set their bed spawn point to this location.

Resetting a Temporary Spawn Point for another player without informing them:
Server administrators can reset a temporary spawn point for another player without informing them by typing /resetplayertempspawn [player(s)] [x] [y] [z] [yaw] [pitch] [world] silent in-game.
This will set the target player's bed spawn point back to their original spawn point.

## Permissions
temporaryspawn.tempspawn: Allows player to use the /tempspawn command
temporaryspawn.resettempspawn: Allows player to use the /resettempspawn command
temporaryspawn.setplayertempspawn: Allows player to use the /setplayertempspawn command
temporaryspawn.resetplayertempspawn: Allows player to use the /resetplayertempspawn command
temporaryspawn.silentsetplayertempspawn: Allows player to use the /silentsetplayertempspawn command
temporaryspawn.silentresetplayertempspawn: Allows player to use the /silentresetplayertempspawn command

## Notes
If there are unspecified arguments it will automatically set them to the sender (only works if the command is run by a player).

This plugin stores the player's temporary spawn point in the plugin's internal map, and sets the player's bed spawn point to this location.

The plugin will reset players temporary spawn points when it is disabled.

This plugin will work with multiverse.

The players original spawn point will be set to the spawn they had before running the command.

If the player updates their temporary spawn point when they already have one set, the original spawn point will not change.