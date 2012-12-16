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
        sender.sendMessage(ChatColor.ITALIC.toString() + ChatColor.GRAY + "Type " + ChatColor.GREEN + "## "
                + ChatColor.GRAY + "at any time to end this conversation."
                + "\n" + ChatColor.GOLD + "What would you like to do?"
                + "\n" + ChatColor.WHITE + "  1.  " + ChatColor.GRAY + "Download");
        return "";
    }

    @Override
    protected Prompt handleInput(final ConversationContext context, final String input) {
        if (input.equals("1")) {
            return new DownloadPrompt(plugin, sender);
        }
        return this;
    }
}
