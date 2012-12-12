package com.mvplugin.downloader.prompts;

import com.mvplugin.downloader.api.MultiverseDownloader;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 * A abstract conversation prompt designed to make creating new prompts for Multiverse-Downloader easier.
 */
abstract class DownloaderPrompt implements Prompt {

    protected final MultiverseDownloader plugin;
    protected final CommandSender sender;

    protected DownloaderPrompt(final MultiverseDownloader plugin, final CommandSender sender) {
        this.plugin = plugin;
        this.sender = sender;
    }

    @Override
    public boolean blocksForInput(final ConversationContext conversationContext) {
        return true;
    }

    @Override
    public final String getPromptText(final ConversationContext conversationContext) {
        // In case we need global hooks for each prompt..
        return getText(conversationContext);
    }

    @Override
    public final Prompt acceptInput(final ConversationContext conversationContext, final String input) {
        // In case we need global hooks for each prompt..
        return handleInput(conversationContext, input);
    }

    /**
     * See {@link #getPromptText(org.bukkit.conversations.ConversationContext)}.
     *
     * @param context The conversation context for this prompt.
     * @return The text to be displayed to the conversable.
     */
    protected abstract String getText(final ConversationContext context);

    /**
     * See {@link #acceptInput(org.bukkit.conversations.ConversationContext, String)}.
     *
     * @param context The conversation context for this prompt.
     * @param input The input given by the conversable.
     * @return The next prompt to prompt the conversable with.
     */
    protected abstract Prompt handleInput(final ConversationContext context, final String input);
}
