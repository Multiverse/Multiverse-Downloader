package com.mvplugin.downloader.tasks;

import com.mvplugin.downloader.DownloadException;
import com.mvplugin.downloader.api.FileLink;
import com.mvplugin.downloader.api.MultiverseDownloader;
import com.mvplugin.downloader.api.SiteLink;
import com.mvplugin.downloader.prompts.DownloadPrompt;
import com.mvplugin.downloader.prompts.VersionSelectPrompt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DownloadTask implements Runnable {

    private final MultiverseDownloader plugin;
    private final CommandSender sender;
    private final FileLink fileLink;

    public DownloadTask(final MultiverseDownloader plugin, final CommandSender sender, final FileLink fileLink) {
        this.plugin = plugin;
        this.sender = sender;
        this.fileLink = fileLink;

        sender.sendMessage(ChatColor.GRAY.toString() + "Downloading file.  This could take some time...");

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, this);
    }

    @Override
    public void run() {
        try {
            plugin.downloadPlugin(fileLink);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    sender.sendMessage(ChatColor.GREEN + "Successfully downloaded:  \n"
                            + ChatColor.AQUA + fileLink.getPluginName() + " "
                            + ChatColor.GRAY + fileLink.getType().getName() + ": " + fileLink.getVersion()
                            + ChatColor.DARK_GRAY + " for " + fileLink.getGameVersion()
                            + "\n\n" + ChatColor.DARK_AQUA + ChatColor.ITALIC + "(will be installed on next restart)");
                }
            });
        } catch (final DownloadException e) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    sender.sendMessage(e.getMessage());
                }
            });
        }
    }
}
