package com.over2craft.onWorldChange.listeners;

import com.over2craft.onWorldChange.Main;
import com.over2craft.onWorldChange.config.Action_Type;
import com.over2craft.onWorldChange.exceptions.ActionTypeNotFoundException;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class onWorldChange implements Listener {

    /**
     * Set the native placeholder and PlaceHolderAPI placeholders if available
     *
     * @param str Str in which you want to replace placeholders
     * @param e Used to fill placeholders
     *
     * @return String with placeholders replaced
     */
    private String replacePlaceHolder(String str, PlayerChangedWorldEvent e)
    {
        str = str.replaceAll(Pattern.quote("$playername$"), e.getPlayer().getName());
        str = str.replaceAll(Pattern.quote("$worldname$"), e.getPlayer().getWorld().getName());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            str = PlaceholderAPI.setPlaceholders(e.getPlayer(), str);
        }
        return  str.replaceAll(Pattern.quote("$oldworldname$"), e.getFrom().getName());
    }

    /**
     * Execute a List of actions and their command.
     * @param actions List of actions that will be executed
     *                [<name_of_action>] <minecraft_command>
     * @param e
     */
    private void executeActions(List<String> actions, PlayerChangedWorldEvent e)
    {
        actions.forEach((action) -> executeAction(action, e));
    }

    /**
     * Execute an action
     * @param action that will be executed
     *               [<name_of_action>] <minecraft_command>
     * @param e
     */
    private void executeAction(String action, PlayerChangedWorldEvent e)
    {

        Action_Type actionType = extractActionType(action);

        String command = extractMinecraftCommand(action, actionType);
        command = replacePlaceHolder(command, e);

        switch (actionType) {
            case COMMAND_AS_PLAYER:
                e.getPlayer().performCommand(command);
                break;
            case COMMAND_AS_CONSOLE:
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
                break;
        }
    }

    /**
     * Extract the minecraft command from an action
     * @param action With this format [<action_name>] <minecraft_command>
     * @param type Type of action
     * @return Return only the minecraft command without the action : <minecraft_command>
     */
    private String extractMinecraftCommand(String action, Action_Type type)
    {
        return action.replaceFirst(Pattern.quote(type.value), "").trim();
    }

    /**
     * Exract the type of action
     * @param action With this format [<action_name>] <minecraft_command>
     * @return [<action_name>] as a {@link Action_Type}
     */
    private Action_Type extractActionType(String action) {

        Pattern pattern = Pattern.compile("(\\[[a-zA-Z_0-9]*\\])");
        Matcher matcher = pattern.matcher(action);

        if (!matcher.find()) {
            throw new ActionTypeNotFoundException();
        }

        String type = matcher.group(1);

        return Action_Type.getEnumForAction(type);
    }

    @EventHandler
    public void PlayerChangedWorldEvent(PlayerChangedWorldEvent e)
    {
        for (String section : Objects.requireNonNull(Main.plugin.getConfig().getConfigurationSection("onJoin")).getKeys(false)) {
            Pattern pattern = Pattern.compile(Objects.requireNonNull(Main.plugin.getConfig().getString("onJoin." + section + ".match")));
            Matcher matcher = pattern.matcher(e.getPlayer().getWorld().getName());
            if (matcher.find()) {
                executeActions(Main.plugin.getConfig().getStringList("onJoin." + section + ".actions"), e);
            }
        }
    }

}
