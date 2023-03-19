package dev.almostsomeone.premiumpvp.game;

import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static dev.almostsomeone.premiumpvp.utilities.Chat.color;

public class Lobby {

    public Lobby() {
    }

    public void teleport() {
        Player player = null;
        if (player == null) return;

        player.getInventory().clear();
        player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
        getHotbarItems().forEach((slot, item) -> player.getInventory().setItem(slot, item));

        //game.getBoardManager().showBoard(gamePlayer);
    }

    public boolean isAllowDrop() {
        return Settings.getConfig().getBoolean("lobby.settings.item-drop", false);
    }

    public boolean isAllowMove() {
        return Settings.getConfig().getBoolean("lobby.settings.item-move", false);
    }

    public boolean isAllowPickup() {
        return Settings.getConfig().getBoolean("lobby.settings.item-pickup", false);
    }

    public HashMap<Integer, ItemStack> getHotbarItems() {
        YamlConfiguration config = Settings.getConfig();
        HashMap<Integer, ItemStack> hotbarItems = new HashMap<>();
        if (config.isSet("lobby.slots"))
            for (String slotStr : Objects.requireNonNull(config.getConfigurationSection("lobby.slots")).getKeys(false)) {
                try {
                    int slot = Integer.parseInt(slotStr);
                    if (slot < 0 || slot > 9) {
                        Bukkit.getLogger().log(Level.WARNING, () -> "The slot " + slotStr + " is not valid. Skipping item.");
                        continue;
                    }

                    Material material = Material.getMaterial(config.getString("lobby.slots." + slotStr + ".material", "BARRIER"));
                    if (material == null) {
                        Bukkit.getLogger().log(Level.WARNING, () -> "The item " + config.getString("lobby.slots." + slotStr + ".material", "BARRIER") + " is not valid. Skipping item.");
                        continue;
                    }

                    int amount = config.getInt("lobby.slots." + slotStr + ".amount", 1);
                    String name = color(config.getString("lobby.slots." + slotStr + ".name", ""));
                    List<String> lore = new ArrayList<>();
                    config.getStringList("lobby.slots." + slotStr + ".lore").forEach(line -> lore.add(color(line)));
                    boolean enchanted = config.getBoolean("lobby.slots." + slotStr + ".enchanted", false);
                    //TODO
//                String menu = config.getString("lobby.slots." + slotStr + ".menu", "");
//                String command = config.getString("lobby.slots." + slotStr + ".command", "");
                    ItemStack itemStack = new ItemStack(material, amount);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (itemMeta != null) {
                        itemMeta.setDisplayName(name);
                        itemMeta.setLore(lore);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        if (enchanted) itemMeta.addEnchant(Enchantment.LUCK, 1, true);
                        itemStack.setItemMeta(itemMeta);
                    }
                    hotbarItems.put(slot - 1, itemStack);
                } catch (NumberFormatException exception) {
                    Bukkit.getLogger().log(Level.WARNING, () -> "The slot " + slotStr + " is not valid. Skipping hotbar configuration.");
                }
            }
        return hotbarItems;
    }
}
