package dev.pepe44.music;


import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import dev.pepe44.Dream;
import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        GuildVoiceState state;
        if((state = m.getVoiceState()) != null) {
            VoiceChannel vc;
            if ((vc = state.getChannel()) != null){
                MusicContorller contorller = Dream.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                AudioManager manager = vc.getGuild().getAudioManager();
                AudioPlayer player = contorller.getPlayer();
                player.stopTrack();
                manager.closeAudioConnection();
                message.addReaction("U+1F44C").queue();

            }
        }
    }
}
