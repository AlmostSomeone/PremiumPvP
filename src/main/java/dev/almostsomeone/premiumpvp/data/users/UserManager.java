package dev.almostsomeone.premiumpvp.data.users;

import dev.almostsomeone.premiumpvp.Configuration;
import dev.almostsomeone.premiumpvp.storage.Storage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.UUID;

public class UserManager implements Listener {

    private final Plugin plugin;
    private final Configuration configuration;
    private final Storage storage;

    private final HashMap<UUID, User> users = new HashMap<>();

    public UserManager(@Nonnull Plugin plugin, @Nonnull Configuration configuration, @Nonnull Storage storage) {
        this.plugin = plugin;
        this.configuration = configuration;
        this.storage = storage;
    }

    public User getUser(@Nonnull UUID uuid) {
        return users.get(uuid);
    }

    private void loadUser(@Nonnull UUID uuid) {
        User user = new User(uuid);
        users.put(uuid, user);
        /*
         * TODO
         *  - Check if User exists
         *  - Create user if not
         *  - Load user
         */
        // TODO Find a way to trigger UserLoadEvent outside of the async
    }

    // I don't actually need the storage object here, I just require it because I want this to only be executable from the main class.
    public void loadUsers(@Nonnull Storage storage) {
        // TODO Load all users (#loadUser())
    }

    @EventHandler
    private void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        if (configuration.getSettings().getBoolean("performance.caching.read-on-join", false) ||
                !users.containsKey(event.getUniqueId())) {
            loadUser(event.getUniqueId());
        }
    }
}
