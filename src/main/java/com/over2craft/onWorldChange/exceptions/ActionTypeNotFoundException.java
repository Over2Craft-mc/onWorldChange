package com.over2craft.onWorldChange.exceptions;

import com.over2craft.onWorldChange.config.Action_Type;
import org.bukkit.Bukkit;

import java.util.logging.Level;

/**
 * This exception is thrown when the type of action defined in the plugin configuration is not recognized
 */
public class ActionTypeNotFoundException extends IllegalArgumentException {

    public ActionTypeNotFoundException() {
        Bukkit.getLogger().log(Level.SEVERE, "[onWorldChange] An action where defined but no one was used. " +
                String.format("Available actions are %s. Refer to configuration documentation. This will throw an exception.",
                        Action_Type.getAvailableActions().toString()));
    }

    public ActionTypeNotFoundException(String incorrectAction) {
        Bukkit.getLogger().log(Level.SEVERE, "[onWorldChange] Action " + incorrectAction + " doesn't exist. You must use one of the following " +
                Action_Type.getAvailableActions().toString() + ". Refer to configuration documentation. This will throw an exception."
        );
    }

}
