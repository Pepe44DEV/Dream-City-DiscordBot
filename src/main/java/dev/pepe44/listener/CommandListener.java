package dev.pepe44.listener;

import dev.pepe44.Dream;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

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
                                ch.sendMessage(m).queue();
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
                        m.delete().queue();
                        System.out.println("[INFO] Vorschlag von " + event.getUser().getName() + " abgelehnt!");
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
}
