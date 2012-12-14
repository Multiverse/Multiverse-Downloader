package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.api.MultiverseDownloader;
import com.mvplugin.downloader.tasks.SiteLinkTask;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 * The conversation prompt for choosing which plugin to download.
 */
public class DownloadPrompt extends DownloaderPrompt {

    public DownloadPrompt(final MultiverseDownloader plugin, final CommandSender sender) {
        super(plugin, sender);
    }

    @Override
    protected String getText(final ConversationContext context) {
        sender.sendMessage(ChatColor.AQUA + "Which Multiverse plugin would you like to download?"
                + "\n" + ChatColor.ITALIC + ChatColor.GRAY + "Example: Core");
        return "";
    }

    @Override
    protected Prompt handleInput(final ConversationContext context, String input) {
        if (input.startsWith("-a ")) {
            input = input.replace("-a ", "");
        } else {
            input = "Multiverse-" + input;
        }
        new SiteLinkTask(plugin, sender, input);
        return END_OF_CONVERSATION;
    }
}
