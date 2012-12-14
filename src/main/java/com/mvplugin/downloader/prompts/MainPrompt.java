package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.api.MultiverseDownloader;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 * The initial conversation prompt after using the /mvdownload command.
 */
public class MainPrompt extends DownloaderPrompt {

    public MainPrompt(final MultiverseDownloader plugin, final CommandSender sender) {
        super(plugin, sender);
    }

    @Override
    public String getText(final ConversationContext context) {
        sender.sendMessage(ChatColor.AQUA + "What would you like to do?"
                + "\n" + ChatColor.WHITE + "  Download"
                + "\n" + ChatColor.ITALIC + ChatColor.GRAY + "Type " + ChatColor.GREEN + "## "
                + ChatColor.GRAY + "at any time to end this conversation.");
        return "";
    }

    @Override
    protected Prompt handleInput(final ConversationContext context, final String input) {
        if (input.equalsIgnoreCase("download") || input.equalsIgnoreCase("dl")) {
            return new DownloadPrompt(plugin, sender);
        }
        return this;
    }
}
