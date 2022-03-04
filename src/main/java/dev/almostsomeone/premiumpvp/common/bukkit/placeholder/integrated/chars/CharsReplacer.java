package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.chars;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.Placeholder;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class CharsReplacer implements Replacer {

    @NotNull
    private final Closure closure;

    public CharsReplacer(@NotNull Closure closure) {
        this.closure = closure;
    }

    @NotNull
    public String apply(@NotNull String text, @Nullable OfflinePlayer player) {
        char[] chars = text.toCharArray();
        StringBuilder builder = new StringBuilder(text.length());
        StringBuilder identifier = new StringBuilder();
        StringBuilder parameters = new StringBuilder();

        for(int i = 0; i < chars.length; ++i) {
            char l = chars[i];
            if (l == this.closure.head && i + 1 < chars.length) {
                boolean identified = false;
                boolean isCloser = false;
                boolean hadSpace = false;

                while(true) {
                    ++i;
                    if (i >= chars.length) {
                        break;
                    }

                    char p = chars[i];
                    if (p == ' ' && !identified) {
                        hadSpace = true;
                        break;
                    }

                    if (p == this.closure.tail) {
                        isCloser = true;
                        break;
                    }

                    if (p == '_' && !identified) {
                        identified = true;
                    } else if (identified) {
                        parameters.append(p);
                    } else {
                        identifier.append(p);
                    }
                }

                String identifierString = identifier.toString();
                String parametersString = parameters.toString();
                identifier.setLength(0);
                parameters.setLength(0);
                if (!isCloser) {
                    builder.append(this.closure.head).append(identifierString);
                    if (identified) {
                        builder.append('_').append(parametersString);
                    }

                    if (hadSpace) {
                        builder.append(' ');
                    }
                } else {
                    Placeholder placeholder = Main.getInstance().getPlaceholder();
                    Optional<PlaceholderPack> placeholderPack = placeholder.getRegisteredPacks().stream().filter(pack -> pack.getIdentifier().equalsIgnoreCase(identifierString)).findFirst();
                    placeholderPack.ifPresent(pack -> builder.append(pack.apply(player, parametersString)));
                    if(placeholderPack.isEmpty())
                        builder.append("%").append(identifierString).append("_").append(parametersString).append("%");
                }
            } else {
                builder.append(l);
            }
        }

        return builder.toString();
    }
}