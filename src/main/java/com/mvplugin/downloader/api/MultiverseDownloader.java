package com.mvplugin.downloader.api;

import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.Plugin;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Describes all the methods available via the Multiverse-Downloader's plugin api.
 */
public interface MultiverseDownloader extends Plugin {

    /**
     * Retrieves a site link for the given plugin name.
     *
     * @param pluginName The plugin name to retrieve the site link for.
     * @return a site link for the given plugin name.
     */
    SiteLink getSiteLink(String pluginName) throws IOException, XMLStreamException;

    void downloadPlugin(FileLink fileLink);

    void startConversation(final CommandSender sender, final Prompt initialPrompt);
}
