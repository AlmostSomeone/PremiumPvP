package dev.almostsomeone.premiumpvp.chat.placeholder.integrated.chars;

import dev.almostsomeone.premiumpvp.chat.placeholder.Placeholder;
import dev.almostsomeone.premiumpvp.chat.placeholder.integrated.PlaceholderPack;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public record CharsReplacer(
        @Nonnull Replacer.Closure closure) implements Replacer {

    public CharsReplacer(@Nonnull Closure closure) {
        this.closure = closure;
    }

    @Nonnull
    public String apply(@Nonnull String text, OfflinePlayer player) {
        char[] chars = text.toCharArray();
        StringBuilder builder = new StringBuilder(text.length());
        StringBuilder identifier = new StringBuilder();
        StringBuilder parameters = new StringBuilder();

        for (int i = 0; i < chars.length; ++i) {
            char l = chars[i];
            if (l == this.closure.head && i + 1 < chars.length) {
                boolean identified = false;
                boolean isCloser = false;
                boolean hadSpace = false;

                while (true) {
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
                    Optional<PlaceholderPack> placeholderPack = Objects.requireNonNull(Placeholder.getRegisteredPacks()).stream().filter(pack -> pack.getIdentifier().equalsIgnoreCase(identifierString)).findFirst();
                    placeholderPack.ifPresent(pack -> builder.append(pack.apply(player, parametersString)));
                    if (placeholderPack.isEmpty())
                        builder.append("%").append(identifierString).append("_").append(parametersString).append("%");
                }
            } else {
                builder.append(l);
            }
        }

        return builder.toString();
    }
}