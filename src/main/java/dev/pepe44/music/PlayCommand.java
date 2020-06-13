package dev.pepe44.music;


import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.pepe44.Dream;
import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class PlayCommand implements ServerCommand {
    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        String[] args = message.getContentDisplay().split(" ");

        if (args.length > 1) {
            GuildVoiceState state;
            if((state = m.getVoiceState()) != null){
                VoiceChannel vc;
                if((vc = state.getChannel()) != null) {
                    MusicContorller controler = Dream.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                    AudioPlayerManager apm = Dream.INSTANCE.audioPlayerManager;
                    AudioManager manager = vc.getGuild().getAudioManager();
                    manager.openAudioConnection(vc);


                    StringBuilder strBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) strBuilder.append(args[i] + " ");

                    String url = strBuilder.toString().trim();
                    if (!url.startsWith("http")){
                        url = "ytsearch" + url;
                    }



                    apm.loadItem(url, new AudioLoadResult(controler, url));
                    message.addReaction("U+1F44C").queue();

                }else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Bitte betrete einen VoiceChanel");
                    builder.setColor(Color.decode("#f55d42"));
                    channel.sendMessage(builder.build()).queue();
                }

            }else{
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("Bitte betrete einen VoiceChanel");
                builder.setColor(Color.decode("#f55d42"));
                channel.sendMessage(builder.build()).queue();
            }
        }else{
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Bitte benutze #play <url/search querry>");
            builder.setColor(Color.decode("#f55d42"));
            channel.sendMessage(builder.build()).queue();
        }
    }
}
