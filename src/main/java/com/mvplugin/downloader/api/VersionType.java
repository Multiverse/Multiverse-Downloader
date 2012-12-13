package com.mvplugin.downloader.api;

/**
 * The different version types available.
 */
public enum VersionType {
    ALPHA("Alpha"),
    BETA("Beta"),
    RELEASE("Release");

    private final String name;

    VersionType(final String name) {
        this.name = name;
    }

    /**
     * Gets the human readable name of this version type.
     *
     * @return the human readable name of this version type.
     */
    public String getName() {
        return name;
    }
}
