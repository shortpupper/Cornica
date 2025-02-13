package org.example.cornica.cornica;

import org.bukkit.Bukkit;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getLogger;

public class ChatListeners implements Listener, ChatRenderer { // Implement the ChatRenderer and Listener interface
    public ChatListeners(Cornica plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Listen for the AsyncChatEvent
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (Cornica.CHANGECHAT) {
            event.renderer(this); // Tell the event to use our renderer
        }
//        event.renderer((source, sourceDisplayName, message, viewer) -> {
//            // ...
//        });
    }

    @Override
    public Component render(Player source, Component sourceDisplayName, Component message, Audience viewer) {
        return sourceDisplayName
                    .append(Component.text(": "))
                    .append(message);
    }
}