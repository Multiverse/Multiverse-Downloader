package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.api.FileLink;
import com.mvplugin.downloader.api.MultiverseDownloader;
import com.mvplugin.downloader.api.SiteLink;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

public class VersionSelectPrompt extends DownloaderPrompt {

    private final SiteLink siteLink;
    private final String choices;

    public VersionSelectPrompt(final MultiverseDownloader plugin, final CommandSender sender,
                               final SiteLink siteLink) {
        super(plugin, sender);
        this.siteLink = siteLink;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < siteLink.getFiles().size(); i++) {
            final FileLink file = siteLink.getFiles().get(i);
            builder.append("\n  ").append(ChatColor.WHITE).append(i + 1).append(". ")
                    .append(ChatColor.GRAY).append(file.getType().getName()).append(": ")
                    .append(file.getVersion()).append(" ").append(ChatColor.DARK_GRAY).append(" for ")
                    .append(file.getGameVersion());
        }
        this.choices = builder.toString();
    }

    @Override
    protected String getText(final ConversationContext context) {
        sender.sendMessage(ChatColor.BOLD.toString() + ChatColor.DARK_AQUA + siteLink.getPluginName() + ChatColor.RESET
                + "\n" + ChatColor.AQUA + "The following versions are available for download: " + choices
                + "\n" + ChatColor.AQUA + "Enter the number for the version you would link to download.");
        return "";
    }

    @Override
    protected Prompt handleInput(final ConversationContext context, final String input) {
        return END_OF_CONVERSATION;
    }
}
