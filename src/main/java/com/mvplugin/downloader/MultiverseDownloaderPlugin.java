package com.mvplugin.downloader;

import com.mvplugin.downloader.api.FileLink;
import com.mvplugin.downloader.api.MultiverseDownloader;
import com.mvplugin.downloader.api.SiteLink;
import com.mvplugin.downloader.prompts.DownloadPrompt;
import com.mvplugin.downloader.prompts.MainPrompt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The main plugin class for Multiverse-Downloader.
 *
 * All of this plugin's specific functionality is cleanly available via {@link MultiverseDownloader}.
 */
public class MultiverseDownloaderPlugin extends JavaPlugin implements MultiverseDownloader {

    private static final int BUFFER_SIZE = 1024;

    private final String updateFolderName = YamlConfiguration
            .loadConfiguration(new File("bukkit.yml")).getString("settings.update-folder");

    @Override
    public void onEnable() {
        //setupParentPermission();
        /*for (Provider prov : Security.getProviders()) {
            System.out.println(prov);
            for (Provider.Service srv : prov.getServices()) {
                System.out.println(" " + srv.getAlgorithm());
            }
        }*/
    }

    private void setupParentPermission() {
        Permission multiversePermission = Bukkit.getPluginManager().getPermission("multiverse.*");
        if (multiversePermission == null) {
            multiversePermission = new Permission("multiverse.*");
            Bukkit.getPluginManager().addPermission(multiversePermission);
        }
        Permission downloadPermission = Bukkit.getPluginManager().getPermission("multiverse.download");
        if (downloadPermission == null) {
            downloadPermission = new Permission("multiverse.download");
            Bukkit.getPluginManager().addPermission(downloadPermission);
        }
        downloadPermission.addParent(multiversePermission, true);
        Bukkit.getPluginManager().recalculatePermissionDefaults(multiversePermission);
        Bukkit.getPluginManager().recalculatePermissionDefaults(downloadPermission);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
                             final String label, final String[] args) {
        if (!(sender instanceof Conversable)) {
            sender.sendMessage("Sorry, but Bukkit won't allow us to start a conversation with you!");
            return true;
        }

        if (args.length == 0) {
            startConversation(sender, new MainPrompt(this, sender));
            return true;
        } else {
            if (args[0].equalsIgnoreCase("download") || args[0].equalsIgnoreCase("dl")) {
                startConversation(sender, new DownloadPrompt(this, sender));
            }
            // Handle argument based, non-conversation, command.
            return false;
        }
    }

    public void startConversation(final CommandSender sender, final Prompt initialPrompt) {
        final Conversable conversable = (Conversable) sender;
        final Conversation conversation = new ConversationFactory(this)
                .withFirstPrompt(initialPrompt)
                .withEscapeSequence("##")
                // TODO config for modality
                .withModality(false).buildConversation(conversable);
        conversation.begin();
    }

    @Override
    public SiteLink getSiteLink(final String pluginName) throws IOException, XMLStreamException {
        // TODO Get mapped plugin slugs if available
        return new DefaultSiteLink(this, pluginName, pluginName);
    }

    @Override
    public void downloadPlugin(final FileLink fileLink) throws DownloadException {
        final File file;
        if (Bukkit.getPluginManager().getPlugin(fileLink.getPluginName()) == null) {
            file = new File("plugins", fileLink.getFileName());
        } else {
            file = new File("plugins/" + updateFolderName, fileLink.getFileName());
        }
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        InputStream in = null;
        OutputStream fout = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            in = new BufferedInputStream(fileLink.getDownloadLink().openStream());
            if (md != null) {
                in = new DigestInputStream(in, md);
            }
            fout = new FileOutputStream(file);
            streamCopy(in, fout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) { }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException ignore) { }
            }
        }

        if (md != null) {
            byte[] remoteMD5 = hexStringToByteArray(fileLink.getMD5CheckSum());
            byte[] localMD5 = md.digest();
            boolean valid = true;
            if (remoteMD5.length != localMD5.length) {
                valid = false;
            }
            for (int i = 0; i < remoteMD5.length && valid; i++) {
                if (remoteMD5[i] != localMD5[i]) {
                    valid = false;
                }
            }
            if (!valid) {
                try {
                    throw new DownloadException("The downloaded file seems to be corrupt!  Deleted corrupt download.");
                } finally {
                    file.delete();
                }
            }
        }
    }

    private static byte[] hexStringToByteArray(final String s) {
        byte data[] = new byte[s.length() / 2];
        for(int i = 0; i < s.length();i += 2) {
            data[i / 2] = (Integer.decode("0x" + s.charAt(i) + s.charAt(i + 1))).byteValue();
        }
        return data;
    }

    private void hotSwapPlugin() {
        /**
        final Plugin plugin = Bukkit.getPluginManager().getPlugin(fileLink.getPluginName());
        if (plugin == null && hotSwap) {
            hotSwap = false;
        }
        if (hotSwap) {
            //folder = new File("plugins");
            try {
                file = new File(plugin.getClass().getProtectionDomain()
                        .getCodeSource().getLocation().toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
         **/
    }

    private void streamCopy(final InputStream in, final OutputStream out) throws IOException {
        byte[] data = new byte[BUFFER_SIZE];
        int count;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
        {
            out.write(data, 0, count);
        }
    }
}
