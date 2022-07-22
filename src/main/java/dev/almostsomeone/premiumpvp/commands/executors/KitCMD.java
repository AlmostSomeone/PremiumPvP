package dev.almostsomeone.premiumpvp.commands.executors;

import dev.almostsomeone.premiumpvp.commands.CommandBuilder;

import java.util.HashMap;

public class KitCMD extends CommandBuilder {

    public KitCMD() {
        super("commands.kit");

        subCommands = new HashMap<>() {{
            put("help", "Get a list of sub-commands");
            put("list", "Get a list of all kits");
            put("create", "Create a new kit");
            put("delete", "Delete a kit");
            put("enable", "Enable a disabled kit");
            put("disable", "Disable an enabled kit");
            put("edit", "Edit a kit");
        }};
    }
}
