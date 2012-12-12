package com.mvplugin.downloader.api;

/**
 * Represents an available version of a plugin retrieved from a SiteLink.
 */
public interface FileLink {

    /**
     * Gets the plugin version.
     *
     * @return the plugin version.
     */
    String getVersion();

    /**
     * Gets the version of the game this plugin is intended for.
     *
     * @return the version of the game this plugin is intended for.
     */
    String getGameVersion();

    /**
     * Gets the name of the plugin file.
     *
     * @return the name of the plugin file.
     */
    String getFileName();

    /**
     * Gets the plugin file size.
     *
     * @return the plugin file size.
     */
    String getFileSize();

    /**
     * Gets the type of version this plugin file is.
     *
     * @return the type of version this plugin file is.
     */
    VersionType getType();

}
