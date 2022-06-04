package dev.almostsomeone.premiumpvp.chat.placeholder.integrated.chars;

import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;

public interface Replacer {
    @Nonnull
    String apply(@Nonnull String var1, OfflinePlayer var2);

    enum Closure {
        BRACKET('{', '}'),
        PERCENT('%', '%');

        public final char head;
        public final char tail;

        Closure(char head, char tail) {
            this.head = head;
            this.tail = tail;
        }
    }
}
