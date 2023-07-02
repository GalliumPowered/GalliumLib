package net.zenoc.gallium.internal.plugin.commands.testing;

import net.kyori.adventure.text.Component;
import net.zenoc.gallium.api.annotations.Args;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.commandsys.CommandContext;
import net.zenoc.gallium.commandsys.args.ArgumentType;

public class TestCommand {
    @Command(
            aliases = "test",
            description = "A test command, for testing Gallium!",
            args = {
                    @Args(name = "something", type = ArgumentType.SINGLE),
                    @Args(name = "greedy", type = ArgumentType.GREEDY)
            })
    public void testCommand(CommandContext ctx) {
        ctx.getArgument("something").ifPresent(something -> {
            ctx.getCaller().sendMessage(Component.text(something));
        });
        ctx.getArgument("greedy").ifPresent(greedy -> {
            ctx.getCaller().sendMessage(Component.text(greedy));
        });
    }

    @Command(aliases = "test2", description = "More testing", args = @Args(name = "helo", type = ArgumentType.GREEDY))
    public void test2Command(CommandContext ctx) {
        ctx.getArgument("helo").ifPresent(helo -> {
            ctx.getCaller().sendMessage(Component.text(helo));
        });
    }

    @Command(aliases = "test3", description = "More testing", args = @Args(name = "helo", type = ArgumentType.QUOTED))
    public void test3Command(CommandContext ctx) {
        ctx.getArgument("helo").ifPresent(helo -> {
            ctx.getCaller().sendMessage(Component.text(helo));
        });
    }

    @Command(aliases = "test4", description = "More testing", args = {
            @Args(name = "helo", type = ArgumentType.QUOTED),
            @Args(name = "helo1", type = ArgumentType.SINGLE)
    })
    public void test4Command(CommandContext ctx) {
        ctx.getArgument("helo").ifPresent(helo -> {
            ctx.getCaller().sendMessage(Component.text(helo));
        });
        ctx.getArgument("helo1").ifPresent(helo1 -> {
            ctx.getCaller().sendMessage(Component.text(helo1));
        });
    }
}
