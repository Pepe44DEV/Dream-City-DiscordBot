package dev.pepe44.commands;

import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class HelpCommand implements ServerCommand {

    String help = ("```#play <url>         Speilt Musik \n" +
            "#clear <amount>     Löscht Chanel Nachrichten \n" +
            "#status             Zeigt den Status des Servers```");


    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Commands");
        builder.setDescription(help);
        builder.setColor(Color.decode("#15ad3d"));
        channel.sendMessage(builder.build()).queue();


    }

}

