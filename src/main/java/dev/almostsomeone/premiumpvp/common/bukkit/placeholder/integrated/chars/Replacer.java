package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.chars;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Replacer {
    @NotNull
    String apply(@NotNull String var1, @Nullable OfflinePlayer var2);

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
