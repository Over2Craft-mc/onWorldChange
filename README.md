# onWorldChange
A Minecraft Spigot plugin that allow you to execute some actions (like commands) when a player change world.  Support PlaceHolderAPI

## Requirements 
* [PlaceHolderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional) if you want to use [PlaceHolderAPI placeholders](https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/Placeholders) (see configuration). 
* Spigot or Paper 1.16.4 (others version not tested)

## Configuration 
http://over2craft-mc.github.io/onWorldChange/src/main/resources/config.yml
```yaml
onJoin:

  # Name this as you wish. It represent a set of world(s) on which some actions well be executed when a player join
  # If you have multiple set of world(s) matching the same world(s), actions will be executed in the same order defined here.
  # With this configuration example if a player join the world named "world",
  # action defined in "all" will be executed and then actions defined in "build"
  all:

    # Value must be a regex that allow you to match some worlds. "actions" will be executed when a player join matched worlds
    #
    # Some example :
    #
    #   (.*) Will match all worlds. "actions" will be executed when a player join any worlds
    #
    #   (world) Will match the world named "world". "actions" will be executed when a player join "world"
    #
    #   (world|world_the_end) Will match the world named "world" or "world_the_end".
    #     "actions" will be executed when a player join "world" or "world_the_end"
    #
    # Tips: You can use this site https://regex101.com/ to test your regex
    match: (.*)

    # Action are represent by [<action>] <option>
    #
    # Actions supported:
    #   [command_as_console] <minecraft_command> # Replace <minecraft_command> with a command that will be executed as console
    #   [command_as_player] <minecraft_command> # Replace <minecraft_command> with a command that will be executed as the player that joined the world
    #
    # Placeholders:
    #   This plugin has a few native placeholders :
    #     $playername$ -> name of the player that joined the world
    #     $worldname$ -> name of the world joined (This is not the alias, just the world name. Use placeholderAPI for Alias)
    #     $oldworldname$ -> name of the world where player was before joining the new one.
    #   You can also use placeholder API (optional).
    #     Download -> https://www.spigotmc.org/resources/placeholderapi.6245/
    #     Available placeholders with PlaceHolderAPI -> https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/Placeholders
    #     For example if you to send a message with the Multiverse world alias use %multiverse_world_alias%
    #
    # Tips: You can use this site to generate JSON message and make them clickable, hoverable...
    # https://minecraft.tools/en/tellraw.php
    actions:
      - '[command_as_console] tellraw $playername$ {"text":""}'
      - '[command_as_console] tellraw $playername$ ["",{"text":"Vous Ãªtes actuellement sur la map","color":"dark_green","clickEvent":{"action":"run_command","value":"/map"}},{"text":" ","clickEvent":{"action":"run_command","value":"/map"}},{"text":"%multiverse_world_alias%","bold":true,"color":"gold","clickEvent":{"action":"run_command","value":"/map"}},{"text":"\n","clickEvent":{"action":"run_command","value":"/map"}},{"text":"Vous pouvez utiliser la commande","color":"dark_green","clickEvent":{"action":"run_command","value":"/map"}},{"text":" ","color":"gold","clickEvent":{"action":"run_command","value":"/map"}},{"text":"/map ","bold":true,"color":"red","clickEvent":{"action":"run_command","value":"/map"}},{"text":"(ou cliquez ici)","italic":true,"color":"red","clickEvent":{"action":"run_command","value":"/map"}},{"text":" ","color":"gold","clickEvent":{"action":"run_command","value":"/map"}},{"text":"pour voir la liste des mondes","color":"dark_green","clickEvent":{"action":"run_command","value":"/map"}}]'

  build:
    match: (world|world_nether)
    actions:
      - '[command_as_console] tellraw $playername$ {"text":"Attention, cette map est utilisÃ©e pour construire. Si vous souhaitez rÃ©colter des ressources, consultez la liste des maps.","color":"blue","clickEvent":{"action":"run_command","value":"/map"}}'
      - '[command_as_console] tellraw $playername$ {"text":""}'

  resources:
    match: (world_resources|Nether_Ressources|world_the_end)
    actions:
      - '[command_as_console] tellraw $playername$ {"text":""}'
      - '[command_as_console] tellraw $playername$ {"text":"Attention, cette map est utilisÃ©e pour la rÃ©colte de ressource uniquement. Si vous souhaitez construire, consultez la liste des maps.","color":"red","clickEvent":{"action":"run_command","value":"/map"}}'
      - '[command_as_console] tellraw $playername$ {"text":""}'
```

## Commands and permissions 
https://over2craft-mc.github.io/onWorldChange/src/main/resources/plugin.yml
```yaml
name: onWorldChange
version: 1.0
author: Over2Craft
main: com.over2craft.onWorldChange.Main

softdepend: [PlaceholderAPI]

commands:
  onWorldChangereload:
    description: Reload onWorldChange configuration
    permission: over2craft.onWorldChange.reload

permissions:
  over2craft.onWorldChange.reload:
    description: Allows onWorldChangereload command
    default: op

```

## Download 
http://over2craft-mc.github.io/onWorldChange/target/onWorldChange.jar
