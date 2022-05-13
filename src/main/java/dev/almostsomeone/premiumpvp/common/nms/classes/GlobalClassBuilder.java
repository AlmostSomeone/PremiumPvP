package dev.almostsomeone.premiumpvp.common.nms.classes;

import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GlobalClassBuilder {

    public void init(String version, final Plugin plugin) throws ClassNotFoundException {
        if ("v1_8_R3".equals(version))
            new ClassBuilder_v1_8_R3();
        else
            plugin.getLogger().log(Level.WARNING, () -> "The NMS " + version + " is not supported, features which depend NMS might not work.");
    }

    // Used for hover-able and clickable chat messages
    public Class<?> ChatSerializer;
    public Class<?> ChatComponent;
}
