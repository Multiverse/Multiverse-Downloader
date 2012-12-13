package com.mvplugin.downloader.api;

import java.util.List;

/**
 * This represents a link to the site where a plugin's downloads are available.
 *
 * From here you can check the available versions, get change-logs, and download any of the available versions.
 */
public interface SiteLink {

    /**
     * Retrieves the available files from this plugin site in date order.
     *
     * The most recent file will be the first element of the list and the oldest file the last.
     * This list is unmodifiable.
     *
     * @return the available files from this plugin site.
     */
    List<FileLink> getFiles();
}
