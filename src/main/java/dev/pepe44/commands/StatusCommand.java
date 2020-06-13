package dev.pepe44.commands;

import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;

public class StatusCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        EmbedBuilder builder = new EmbedBuilder();


        try {
            InetAddress geek = InetAddress.getByName("fivem.null-point.de");
            if (geek.isReachable(5000))
                builder.setDescription("Server ist Online");
                builder.setColor(Color.decode("#15ad3d"));
                channel.sendMessage(builder.build()).queue();
        }catch (IOException e) {
            builder.setDescription("Server ist Offline");
            builder.setColor(Color.decode("#FF0000"));
            channel.sendMessage(builder.build()).queue();

        }






    }
}
