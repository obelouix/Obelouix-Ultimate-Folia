package fr.obelouix.ultimate.commands.parsers;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.bukkit.parsers.MaterialArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

public class BlockArgument<C> extends CommandArgument<C, Material> {

    protected BlockArgument(final boolean required, final @NonNull String name, final @NonNull String defaultValue,
                            final @Nullable BiFunction<@NonNull CommandContext<C>, @NonNull String, @NonNull List<@NonNull String>> suggestionsProvider,
                            final @NonNull ArgumentDescription defaultDescription) {

        super(required, name, new BlockParser<>(), defaultValue, Material.class, suggestionsProvider, defaultDescription);
    }

    public static <C> @NonNull Builder<C> builder(final @NonNull String name) {
        return new Builder<>(name);
    }

    public static <C> @NonNull CommandArgument<C, Material> of(final @NonNull String name) {
        return BlockArgument.<C>builder(name).asRequired().build();
    }

    private static class BlockParser<C> implements ArgumentParser<C, Material> {
        @Override
        public @NonNull ArgumentParseResult<@NonNull Material> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull Queue<@NonNull String> inputQueue) {
            String input = inputQueue.peek();
            if (input == null) {
                return ArgumentParseResult.failure(new NoInputProvidedException(BlockParser.class, commandContext));
            }

            try {
                final Material material = Material.getMaterial(input.toUpperCase());
                if (material != null && material.isBlock()) {
                    inputQueue.remove();
                    return ArgumentParseResult.success(material);
                }
            } catch (final IllegalArgumentException e) {
                return ArgumentParseResult.failure(new MaterialArgument.MaterialParseException(input, commandContext));
            }

            return ArgumentParseResult.failure(new MaterialArgument.MaterialParseException(input, commandContext));
        }

        @Override
        public @NonNull List<@NonNull String> suggestions(@NonNull CommandContext<C> commandContext, @NonNull String input) {
            final List<String> list = new ArrayList<>();
            for (final Material material : Material.values()) {
                if (material.isBlock()) {
                    list.add(material.name().toLowerCase());
                }
            }
            return list;
        }

        @Override
        public int getRequestedArgumentCount() {
            return ArgumentParser.super.getRequestedArgumentCount();
        }
    }

    public static final class Builder<C> extends CommandArgument.Builder<C, Material> {

        private Builder(final @NonNull String name) {
            super(Material.class, name);
        }

        @Override
        public @NonNull BlockArgument<@NonNull C> build() {
            return new BlockArgument<>(this.isRequired(), this.getName(), this.getDefaultValue(), this.getSuggestionsProvider(), this.getDefaultDescription());
        }
    }
}
