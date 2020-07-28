package dev.pepe44.commands;

import dev.pepe44.Dream;
import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.sql.SQLException;
import java.util.Date;

public class TimeCommand implements ServerCommand {
    static Date lastrun;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        if(lastrun == null) {
            lastrun = new Date();
        } else {
            Date now = new Date();
            long forgoing = now.getTime() - lastrun.getTime();
            lastrun = new Date();
            if(forgoing < 20000) {
                EmbedBuilder b = new EmbedBuilder();
                b.setTitle("Fehler");
                b.setColor(Color.RED);
                b.setDescription("Du musst mindestens 20 Sekunden warten um den Befehl neu auszuführen!");
                channel.sendMessage(b.build()).queue();
                return;
            }
        }
        String[] args = message.getContentDisplay().split(" ");
        String name = "";
        for (int i = 1; i < args.length; i++) {
            if(name == "") {
                name = name + args[i];
            } else {
                name = name + " " + args[i];
            }
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Spielzeit");
        try {
            int seconds = Dream.mysql.getData(name);
            if(seconds == -1) {
                builder.setColor(Color.RED);
                builder.setDescription("Spieler nicht gefunden!");
            } else {
                int stunden = seconds / 3600;
                String output = Integer.toString((stunden));
                builder.setDescription(output + " " + "Stunde/n");
                builder.setColor(Color.decode("#660066"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        channel.sendMessage(builder.build()).queue();
    }



}
