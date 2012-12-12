package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.api.MultiverseDownloader;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 * The conversation prompt for choosing which plugin to download.
 */
class DownloadPrompt extends DownloaderPrompt {

    DownloadPrompt(final MultiverseDownloader plugin, final CommandSender sender) {
        super(plugin, sender);
    }

    @Override
    protected String getText(final ConversationContext context) {
        return ChatColor.AQUA + "Which Multiverse plugin would you like to download?"
                + "\n" + ChatColor.ITALIC + ChatColor.GRAY + "Example: Core";
    }

    @Override
    protected Prompt handleInput(final ConversationContext context, final String input) {
        return null;
    }
}
