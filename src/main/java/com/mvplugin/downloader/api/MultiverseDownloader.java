package com.mvplugin.downloader.api;

/**
 * Describes all the methods available via the Multiverse-Downloader's plugin api.
 */
public interface MultiverseDownloader {

    /**
     * Retrieves a site link for the given plugin name.
     *
     * @param pluginName The plugin name to retrieve the site link for.
     * @return a site link for the given plugin name.
     */
    SiteLink getSiteLink(String pluginName);

    void downloadPlugin(FileLink fileLink);
}
