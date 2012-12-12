package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.MultiverseDownloaderPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

public class InitialPrompt extends DownloaderPrompt {

    public InitialPrompt(final MultiverseDownloaderPlugin plugin, final CommandSender sender) {
        super(plugin, sender);
    }

    @Override
    public String getPromptText(final ConversationContext conversationContext) {
        return null;
    }

    @Override
    public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
        return null;
    }
}
