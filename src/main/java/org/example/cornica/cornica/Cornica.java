package org.example.cornica.cornica;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Supplier;

public final class Cornica extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        LiteralCommandNode<CommandSourceStack> rootsOfCornica = Commands.literal("cornica")
                .then(Commands.literal("testing")
                        .then(Commands.literal("iscanthavebukkit")
                            .then(Commands.argument("allow", BoolArgumentType.bool())
                                    .executes(ctx -> {
                                        boolean allowed = ctx.getArgument("allow", boolean.class);
                                        CommandSender sender = ctx.getSource().getSender(); // Retrieve the command sender

                                        // Check whether the executor is a player, as you can only set a player's flight speed
                                        if (allowed) {
                                            // If a non-player tried to set their own flight speed
                                            sender.sendPlainMessage("No");
                                        } else if (!allowed) {
                                            sender.sendPlainMessage("NOOOOOO");
                                        }

                                        /* Here we are on /plant tree */
                                        return Command.SINGLE_SUCCESS;
                                    }
                                )
                            )
                        )
                        .then(Commands.literal("flyspeed")
                                .then(Commands.argument("speed", FloatArgumentType.floatArg(0, 1.0f))
                                        .executes(ctx -> {
                                            float speed = FloatArgumentType.getFloat(ctx, "speed"); // Retrieve the speed argument
                                            CommandSender sender = ctx.getSource().getSender(); // Retrieve the command sender
                                            Entity executor = ctx.getSource().getExecutor(); // Retrieve the command executor, which may or may not be the same as the sender

                                            // Check whether the executor is a player, as you can only set a player's flight speed
                                            if (!(executor instanceof Player player)) {
                                                // If a non-player tried to set their own flight speed
                                                sender.sendPlainMessage("Only players can fly!");
                                                return Command.SINGLE_SUCCESS;
                                            }

                                            // Set the player's speed
                                            player.setFlySpeed(speed);

                                            if (sender == executor) {
                                                // If the player executed the command themselves
                                                player.sendPlainMessage("Successfully set your flight speed to " + speed);
                                                return Command.SINGLE_SUCCESS;
                                            }

                                            // If the speed was set by a different sender (Like using /execute)
                                            sender.sendRichMessage("Successfully set <playername>'s flight speed to " + speed, Placeholder.component("playername", player.name()));
                                            player.sendPlainMessage("Your flight speed has been set to " + speed);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                ).build();

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            // register your commands here ...
            commands.registrar().register(rootsOfCornica);
        });

    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
        getLogger().fine("Bye~!");
    }
}
