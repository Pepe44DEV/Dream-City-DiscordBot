package dev.pepe44.listener;

import dev.pepe44.Dream;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        String message = event.getMessage().getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {
            TextChannel channel = event.getTextChannel();

            if(message.startsWith("#")) {
                String[] args = message.substring(1).split(" ");
                if (args.length > 0) {
                   if(!Dream.INSTANCE.getCmdMan().perform(args[0], event.getMember(), channel, event.getMessage())) {
                       channel.sendMessage("```Unbekanntes Kommando```").queue();
                   }

                }
            }

        }

    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        System.out.println(event.getReactionEmote().getEmoji());
        if(event.getChannel().getId().equals(Dream.CHANNEL_VERBESSERUNG)) {
            if(event.getReactionEmote().getEmoji().equals("\uD83D\uDCCE")) { // :paperclip:
                if(isTeamMember(event.getMember())) {
                    Guild g = event.getJDA().getGuildById(Dream.GUILD);
                    if(g != null) {
                        TextChannel ch = g.getTextChannelById(Dream.CHANNEL_TODO);
                        if(ch != null) {
                            Message m = event.getChannel().retrieveMessageById(event.getMessageId()).complete();
                            if(m != null) {
                                MessageBuilder b = new MessageBuilder();
                                b.append("Vorschlag von: " + m.getAuthor().getName() + "\n");
                                b.append(m.getContentRaw());
                                ch.sendMessage(b.build()).queue();
                                sendUserPrivMessageTodoAdded(m.getAuthor(), m.getContentRaw());
                                m.delete().queue();
                                System.out.println("[INFO] Vorschlag von " + event.getUser().getName() + " angenommen!");
                            } else {
                                System.out.println("[ERROR] Original Message nicht gefunden!");
                            }
                        } else {
                            System.out.println("[ERROR] TODO-Channel nicht gefunden!");
                        }
                    } else {
                        System.out.println("[ERROR] Guild nicht gefunden!");
                    }

                }
            } else if (event.getReactionEmote().getEmoji().equals("❌")) {// :x:
                if(isTeamMember(event.getMember())) {
                    Message m = event.getChannel().retrieveMessageById(event.getMessageId()).complete();
                    if(m != null) {
                        sendUserPrivMessageTodoRemoved(m.getAuthor(), m.getContentRaw());
                        sendToArchive(m.getAuthor(), event.getUser(), m.getContentRaw(), false, event.getJDA());
                        m.delete().queue();
                        System.out.println("[INFO] Vorschlag von " + event.getUser().getName() + " abgelehnt!");
                    } else {
                        System.out.println("[ERROR] Original Message nicht gefunden!");
                    }
                }
            }
        } else if(event.getChannel().getId().equals(Dream.CHANNEL_TODO)) {
            if(event.getReactionEmote().getEmoji().equals("✅")) { // :white_check_mark:
                if(isTeamMember(event.getMember())) {
                    Message m = event.getChannel().retrieveMessageById(event.getMessageId()).complete();
                    if(m != null) {
                        sendToArchive(null, event.getUser(), m.getContentRaw(), true, event.getJDA());
                        m.delete().queue();
                        System.out.println("[INFO] Vorschlag von " + event.getUser().getName() + " umgesetzt!");
                    } else {
                        System.out.println("[ERROR] Original Message nicht gefunden!");
                    }
                }
            }
        }
    }


    private boolean isTeamMember(Member m) {
        for (Role r : m.getRoles()) {
            if(r.getName().equals("Team")) {
                return true;
            }
        }
        return false;
    }

    private void sendUserPrivMessageTodoAdded(User u, String message) {
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Vielen Dank für deinen Vorschlag");
        b.setDescription(message);
        b.setFooter("Dieser Vorschlag wurde akzeptiert und auf der ToDo-Liste vermerkt.");
        PrivateChannel ch = u.openPrivateChannel().complete();
        ch.sendMessage(b.build()).queue(message1 -> ch.close().queue());
    }
    private void sendUserPrivMessageTodoRemoved(User u, String message) {
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Vielen Dank für deinen Vorschlag. Leider wurde der Vorschlag durch ein Teammitglied abgelehnt.");
        b.setDescription(message);
        b.setFooter("Es kann verschiedene Gründe haben, warum dein Vorschlag abgelehnt wurde. Vorschläge werden niemals grundlos abgelehnt.");
        PrivateChannel ch = u.openPrivateChannel().complete();
        ch.sendMessage(b.build()).queue(message1 -> ch.close().queue());
    }
    /*
        accepted true: Nachricht aus #todo wird akzeptiert nachricht verfasst von Bot. -> User u = annehmender
        accepted false: Nachricht wird in #verbesserung abgelehnt. verfasst von Author der Nachricht. -> User u = Author des vorschlages
     */
    private void sendToArchive(User messageowner, User reactor, String message, boolean accepted, JDA jda) {
        EmbedBuilder b = new EmbedBuilder();
        b.setColor(accepted ? Color.GREEN : Color.RED);
        if(accepted) {
            b.setTitle("Vorschlag durch " + reactor.getName() + " angenommen");
        } else {
            b.setTitle("Vorschlag von " + messageowner.getName() + " durch " + reactor.getName() + " abgelehnt.");
        }
        b.setDescription(message);

        Guild g = jda.getGuildById(Dream.GUILD);
        if(g != null) {
            TextChannel ch = g.getTextChannelById(Dream.CHANNEL_ARCHIVE);
            if (ch != null) {
                ch.sendMessage(b.build()).queue();
            } else {
                System.out.println("[ERROR] Archiv nicht gefunden!");
            }
        } else {
            System.out.println("[ERROR] Guild nicht gefunden!");
        }
    }
}
