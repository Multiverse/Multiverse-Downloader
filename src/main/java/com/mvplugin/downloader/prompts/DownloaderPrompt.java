package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.MultiverseDownloaderPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

abstract class DownloaderPrompt implements Prompt {

    protected final MultiverseDownloaderPlugin plugin;
    protected final CommandSender sender;

    protected DownloaderPrompt(final MultiverseDownloaderPlugin plugin, final CommandSender sender) {
        this.plugin = plugin;
        this.sender = sender;
    }

    @Override
    public boolean blocksForInput(final ConversationContext conversationContext) {
        return true;
    }
}
