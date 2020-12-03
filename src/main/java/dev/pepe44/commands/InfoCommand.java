package dev.pepe44.commands;

import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class InfoCommand implements ServerCommand {

    String Text = ("\uD83C\uDFAB Website: dream-city.net \n" +
            "\n" +
            "\uD83C\uDFA4 Teamspeak: dream-city \n" +
            "\n" +
            "\uD83D\uDDA5 Direct Connect IP: 92.42.45.167");


    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Informations");
        builder.setDescription(Text);
        builder.setColor(Color.decode("#660066"));
        channel.sendMessage(builder.build()).queue();
    }
}
