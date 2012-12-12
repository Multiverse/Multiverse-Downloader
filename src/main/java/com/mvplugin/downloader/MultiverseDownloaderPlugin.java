package com.mvplugin.downloader;

import com.mvplugin.downloader.prompts.InitialPrompt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public class MultiverseDownloaderPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        setupParentPermission();
    }

    private void setupParentPermission() {
        Permission multiversePermission = Bukkit.getPluginManager().getPermission("multiverse.*");
        if (multiversePermission == null) {
            multiversePermission = new Permission("multiverse.*");
            Bukkit.getPluginManager().addPermission(multiversePermission);
        }
        final Permission downloadPermission = Bukkit.getPluginManager().getPermission("multiverse.download");
        downloadPermission.addParent(multiversePermission, true);
        Bukkit.getPluginManager().recalculatePermissionDefaults(multiversePermission);
        Bukkit.getPluginManager().recalculatePermissionDefaults(downloadPermission);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Conversable)) {
            sender.sendMessage("Sorry, but Bukkit won't allow us to start a conversation with you!");
            return true;
        }

        Conversable conversable = (Conversable) sender;
        Conversation conversation = new ConversationFactory(this)
                .withFirstPrompt(new InitialPrompt(this, sender))
                .withEscapeSequence("##")
                .withModality(false).buildConversation(conversable);
        conversation.begin();
        return true;
    }
}
