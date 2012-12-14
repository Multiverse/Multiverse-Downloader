package com.mvplugin.downloader.api;

import java.net.URL;
import java.util.Date;

/**
 * Represents an available version of a plugin retrieved from a SiteLink.
 *
 * All information obtained here is as it is represented on the website where this file is located.
 */
public interface FileLink {

    /**
     * Gets the name of the plugin this file belongs to.
     *
     * @return the name of the plugin this file belongs to.
     */
    String getPluginName();

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
     * Gets the plugin file size in bytes.
     *
     * @return the plugin file size in bytes.
     */
    long getFileSize();

    /**
     * Gets the type of version this plugin file is.
     *
     * @return the type of version this plugin file is.
     */
    VersionType getType();

    /**
     * Gets the MD5 check sum for the file in hexidecimal.
     *
     * @return the MD5 check sum for the file in hexidecimal.
     */
    String getMD5CheckSum();

    /**
     * Gets the number of downloads for this plugin file.
     *
     * @return the number of downloads for this plugin file.
     */
    int getDownloadCount();

    /**
     * Gets the date that the file was uploaded.
     *
     * @return the date that the file was uploaded.
     */
    Date getUploadedDate();

    /**
     * Gets the change log for this file.
     *
     * @return the change log for this file.
     */
    String getChangeLog();

    /**
     * Gets the known caveats for this file.
     *
     * @return the known caveats for this file.
     */
    String getKnownCaveats();

    URL getDownloadLink();
}
