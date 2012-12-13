package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.api.FileLink;
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
    protected Prompt handleInput(final ConversationContext context, String input) {
        if (input.startsWith("-a ")) {
            input = input.replace("-a ", "");
        } else {
            input = "Multiverse-" + input;
        }
        FileLink link = plugin.getSiteLink(input).getFiles().get(0);
        sender.sendMessage("Version: " + link.getVersion());
        sender.sendMessage("Game Version: " + link.getGameVersion());
        sender.sendMessage("File Name: " + link.getFileName());
        sender.sendMessage("File URL: " + link.getDownloadLink());
        sender.sendMessage("File Size: " + link.getFileSize());
        sender.sendMessage("Downloads: " + link.getDownloadCount());
        sender.sendMessage("MD5 length: " + link.getMD5CheckSum().length);
        sender.sendMessage("Type: " + link.getType().getName());
        sender.sendMessage("Uploaded on: " + link.getUploadedDate());
        sender.sendMessage("Change Log: " + link.getChangeLog());
        sender.sendMessage("Known Caveats: " + link.getKnownCaveats());
        return END_OF_CONVERSATION;
    }
}
