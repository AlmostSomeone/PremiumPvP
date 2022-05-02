package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated;

import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.chars.CharsReplacer;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.chars.Replacer;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs.CustomPlaceholders;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs.PlayerPlaceholders;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs.ServerPlaceholders;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class IntegratedPlaceholders {

    private final List<PlaceholderPack> registeredPacks;

    private final CharsReplacer percentReplacer = new CharsReplacer(Replacer.Closure.PERCENT);
    private final CharsReplacer bracketReplacer = new CharsReplacer(Replacer.Closure.BRACKET);
    private final Pattern percentPattern = Pattern.compile("[%]([^%]+)[%]");
    private final Pattern bracketPattern = Pattern.compile("[{]([^{}]+)[}]");

    public IntegratedPlaceholders() {
        this.registeredPacks = Arrays.asList(
                new CustomPlaceholders(),
                new ServerPlaceholders(),
                new PlayerPlaceholders()
        );
    }

    public List<PlaceholderPack> getRegisteredPacks() {
        return this.registeredPacks;
    }

    private boolean containsPlaceholders(Pattern pattern, String text) {
        return text != null && pattern.matcher(text).find();
    }

    public String setPlaceholders(OfflinePlayer player, String string) {
        if(player == null || string == null)
            return null;
        if(containsPlaceholders(percentPattern, string))
            string = percentReplacer.apply(string, player);
        if(containsPlaceholders(bracketPattern, string))
            string = bracketReplacer.apply(string, player);
        return string;
    }
}
