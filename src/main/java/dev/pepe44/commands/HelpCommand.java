package dev.pepe44.commands;

import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class HelpCommand implements ServerCommand {

    String help = ("```#clear <amount>     Löscht Chanel Nachrichten \n" +
            "#status             Zeigt den Status des Servers \n" +
            "#time <steamname>   Zeigt Deine Aktuelle Server Spielzeit```");


    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Commands");
        //builder.setDescription(help);
        builder.addField("#clear", "Löscht Channel Nachrichten", false);
        builder.addField("#time", "Zeigt Deine Aktuelle Server Spielzeit", false);
        builder.addField("#status", "Zeigt den Status des Servers", false);
        builder.addField("#issue <Nachricht>", "Meldet einen Fehler", false);
        builder.setColor(Color.decode("#15ad3d"));
        channel.sendMessage(builder.build()).queue();


    }

}

