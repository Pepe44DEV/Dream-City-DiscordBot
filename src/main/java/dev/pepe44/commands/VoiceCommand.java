package dev.pepe44.commands;

import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class VoiceCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("TokoVoip Download");
        //builder.setDescription(help);
        builder.addField("1.2.5", "https://github.com/Itokoyamato/TokoVOIP_TS3/releases/download/v1.2.5-v1.3.3/tokovoip_1_2_5.ts3_plugin", false);
        builder.setColor(Color.decode("#15ad3d"));
        channel.sendMessage(builder.build()).queue();


    }
}
