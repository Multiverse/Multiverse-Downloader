package com.mvplugin.downloader;

import com.mvplugin.downloader.api.FileLink;
import com.mvplugin.downloader.api.SiteLink;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Our default implementation of a SiteLink.
 */
class DefaultSiteLink implements SiteLink {

    // TODO make this configurable.
    private static final int FILE_LIMIT = 5;

    private static final String DBO_URL = "http://dev.bukkit.org/server-mods/";

    // Strings for reading RSS
    //private static final String TITLE = "title";
    private static final String LINK = "link";
    //private static final String ITEM = "item";

    private final MultiverseDownloaderPlugin plugin;
    private final String slug;
    private final List<FileLink> fileLinks;

    DefaultSiteLink(final MultiverseDownloaderPlugin plugin, final String slug) throws MalformedURLException, IOException, XMLStreamException {
        this.plugin = plugin;
        this.slug = slug;

        final List<FileLink> fileLinks = new ArrayList<FileLink>();
        for (final String link : readFilesFeed(new URL(DBO_URL + slug + "/files.rss"))) {
            try {
                final FileLink fileLink = new DefaultFileLink(plugin, link);
                fileLinks.add(fileLink);
            } catch (IOException ignore) {

            }
        }
        this.fileLinks = Collections.unmodifiableList(fileLinks);
    }

    @Override
    public List<FileLink> getFiles() {
        return fileLinks;
    }

    /**
     * Part of RSS Reader by Vogella, modified by H31IX and dumptruckman for use with Bukkit
     */
    private List<String> readFilesFeed(final URL rssURL) throws IOException, XMLStreamException {
        // We'll store all the file urls here.
        final List<String> filePageLinks = new ArrayList<String>();
        // First create a new XMLInputFactory
        final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // Setup a new eventReader
        InputStream in = null;
        try {
            in = rssURL.openStream();
            final XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            int filesFound = 0;
            // Read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equals(LINK)) {
                        event = eventReader.nextEvent();
                        final String link = event.asCharacters().getData();
                        if (!link.equalsIgnoreCase(DBO_URL + slug + "/files/")) {
                            filePageLinks.add(link);
                            filesFound++;
                        }
                        if (filesFound == FILE_LIMIT) {
                            break;
                        }
                    }
                } /*else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart().equals(ITEM)) {
                        // Store the title and link of the first entry we get - the first file on the list is all we need
                        versionTitle = title;
                        versionLink = link;
                        // All done, we don't need to know about older files.
                        break;
                    }
                }*/
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return filePageLinks;
    }
}
