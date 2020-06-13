package dev.pepe44.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import dev.pepe44.Dream;
import net.dv8tion.jda.api.entities.Guild;


public class MusicContorller {



    private Guild guild;
    private AudioPlayer player;

    public MusicContorller(Guild guild) {
        this.guild = guild;
        this.player = Dream.INSTANCE.audioPlayerManager.createPlayer();

        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
        this.player.setVolume(10);


    }

    public Guild getGuild() {
        return guild;
    }

    public AudioPlayer getPlayer() {
        return player;
    }
}
