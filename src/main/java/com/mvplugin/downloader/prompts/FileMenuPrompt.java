package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.api.FileLink;
import com.mvplugin.downloader.api.MultiverseDownloader;
import com.mvplugin.downloader.api.SiteLink;
import com.mvplugin.downloader.tasks.DownloadTask;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

public class FileMenuPrompt extends DownloaderPrompt {

    private final FileLink fileLink;
    private final SiteLink siteLink;

    public FileMenuPrompt(final MultiverseDownloader plugin, final CommandSender sender, final SiteLink siteLink, final FileLink fileLink) {
        super(plugin, sender);
        this.siteLink = siteLink;
        this.fileLink = fileLink;
    }

    @Override
    protected String getText(final ConversationContext context) {
        // send file information
        sender.sendMessage(ChatColor.BOLD.toString() + ChatColor.DARK_AQUA + fileLink.getPluginName() + ChatColor.RESET
                + "\n" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Version: " + ChatColor.RESET + ChatColor.GRAY + fileLink.getVersion()
                + "\n" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Game Version: " + ChatColor.RESET + ChatColor.GRAY + fileLink.getGameVersion()
                + "\n" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Type: " + ChatColor.RESET + ChatColor.GRAY + fileLink.getType().getName()
                + "\n" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Uploaded on: " + ChatColor.RESET + ChatColor.GRAY + fileLink.getUploadedDate()
                + "\n" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Change log: " + ChatColor.RESET + ChatColor.GRAY + fileLink.getChangeLog()
                + "\n" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Known Caveats: " + ChatColor.RESET + ChatColor.GRAY + fileLink.getKnownCaveats()
        );
        // send menu separator
        sender.sendMessage(ChatColor.BOLD.toString() + ChatColor.BLACK + "=============================================");
        // send menu
        sender.sendMessage(ChatColor.WHITE + "  1. " + ChatColor.GRAY + ChatColor.BOLD + "download" + ChatColor.RESET
                + "\n" + ChatColor.WHITE + "  2. " + ChatColor.GRAY + "download and hot swap (coming soon..)"
                + "\n" + ChatColor.WHITE + "  3. " + ChatColor.GRAY + "go back"
                + "\n" + ChatColor.GREEN + "  ##" + ChatColor.WHITE + ". " + ChatColor.GRAY + "quit"
                + "\n" + ChatColor.GOLD + "Please make a selection..."
        );
        return "";
    }

    @Override
    protected Prompt handleInput(final ConversationContext context, final String input) {
        if (input.equals("1")) {
            new DownloadTask(plugin, sender, fileLink);
            return END_OF_CONVERSATION;
        } else if (input.equals("3")) {
            return new VersionSelectPrompt(plugin, sender, siteLink);
        }
        return this;
    }
}
