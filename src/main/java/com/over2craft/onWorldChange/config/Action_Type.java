package com.over2craft.onWorldChange.config;

import com.over2craft.onWorldChange.exceptions.ActionTypeNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent available actions
 */
public enum Action_Type {

    COMMAND_AS_CONSOLE("[command_as_console]"),

    COMMAND_AS_PLAYER("[command_as_player]");

    /**
     * Value of the action as a string like defined in the plugin configuration
     */
    public final String value;

    Action_Type(String s) {
        value = s;
    }

    /**
     * Get all available actions as string like defined in the plugin configuration
     * @return List of actions as string like defined in the plugin configuration
     */
    public static List<String> getAvailableActions() {

        List<String> available = new ArrayList<String>();

        for (Action_Type action : Action_Type.values()) {
            available.add(action.value);
        }

        return available;
    }

    /**
     * Get a {@link Action_Type} from an action type as a string like defined in the plugin configuration
     * @param value Action type as a string
     * @return Action type as an Enum {@link Action_Type}
     */
    public static Action_Type getEnumForAction(String value) {

        for (Action_Type action : Action_Type.values()) {
            if (action.value.equals(value)) {
                return action;
            }
        }

        throw new ActionTypeNotFoundException(value);
    }

}
