package com.mvplugin.downloader.tasks;

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

/**
 * This task creates a new Async task with bukkit that will attempt to get the plugin file data from dev.bukkit.org
 * after which it will report success or failure to the player and continue the download conversation.
 */
public class SiteLinkTask implements Runnable {

    private final MultiverseDownloader plugin;
    private final CommandSender sender;
    private final String pluginName;

    public SiteLinkTask(final MultiverseDownloader plugin, final CommandSender sender, final String pluginName) {
        this.plugin = plugin;
        this.sender = sender;
        this.pluginName = pluginName;

        sender.sendMessage(ChatColor.GRAY.toString() + "Fetching versions.  This will take a moment...");

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, this);
    }

    @Override
    public void run() {
        try {
            final SiteLink link = plugin.getSiteLink(pluginName);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.startConversation(sender, new VersionSelectPrompt(plugin, sender, link));
                }
            });
        } catch (final FileNotFoundException e) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    sender.sendMessage(ChatColor.RED + "Sorry, but I can't find the website for '" + pluginName
                            + "'.  It could be that its slug is not the same as the plugin name.");
                    plugin.startConversation(sender, new DownloadPrompt(plugin, sender));
                }
            });
        } catch (final IOException e) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    sender.sendMessage(ChatColor.RED + "Some kind of error occurred while connecting to the website for '"
                            + pluginName + "'.  Check the console output for details.");
                    e.printStackTrace();
                    plugin.startConversation(sender, new DownloadPrompt(plugin, sender));
                }
            });
        } catch (final XMLStreamException e) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    sender.sendMessage(ChatColor.RED + "Some kind of error occurred while connecting to the website for '"
                            + pluginName + "'.  Check the console output for details.");
                    e.printStackTrace();
                    plugin.startConversation(sender, new DownloadPrompt(plugin, sender));
                }
            });
        }
    }
}
