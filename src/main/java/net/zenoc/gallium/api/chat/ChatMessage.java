package net.zenoc.gallium.api.chat;

import net.kyori.adventure.text.Component;

import java.util.StringJoiner;

public class ChatMessage {
    String content;
    public ChatMessage(String content) {
        this.content = content;
    }

    /**
     * Gets content as a String
     * @return Text as String
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets content as a Component
     * @return Text as component
     */
    public Component getAsComponent() {
        return Component.text(content);
    }

    /**
     * Send a string to the caller
     * @param content the message
     * @return ChatMessage
     */
    public static ChatMessage from(String content) {
        return new ChatMessage(content);
    }

    /**
     * Send a string joiner to the caller
     */
    public static ChatMessage from(StringJoiner joiner) {
        return new ChatMessage(joiner.toString());
    }

    /**
     * Send a message to the caller
     * @deprecated use from(String)
     * @param content the message
     * @return ChatMessage
     */
    @Deprecated
    public static ChatMessage fromString(String content) {
        return from(content);
    }
}
