package dev.almostsomeone.premiumpvp.common.nms;

import dev.almostsomeone.premiumpvp.common.nms.classes.GlobalClassBuilder;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class NMS {

    private GlobalClassBuilder globalClassBuilder;

    public NMS(final Plugin plugin) {
        String version = plugin.getServer().getClass().getPackage().getName();
        version = version.substring(version.lastIndexOf('.') + 1);
        plugin.getLogger().log(Level.INFO, "Hooking into NMS " + version);

        this.globalClassBuilder = new GlobalClassBuilder();
        try {
            globalClassBuilder.init(version, plugin);
        } catch (ClassNotFoundException exception) {
            plugin.getLogger().log(Level.WARNING, "Failed to hook into NMS " + version + ". Please check if you are running a supported fork, if you are, contact the developer.");
            exception.printStackTrace();
        }
    }

    public GlobalClassBuilder getClasses(){
        return globalClassBuilder;
    }
}
