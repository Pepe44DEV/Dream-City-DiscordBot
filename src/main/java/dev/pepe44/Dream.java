package dev.pepe44;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import dev.pepe44.commands.CommandManager;
import dev.pepe44.listener.CommandListener;
import dev.pepe44.manager.MYSQL;
import dev.pepe44.music.PlayerManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dream {

    public static Dream INSTANCE;


    public ShardManager shardMan;
    private CommandManager cmdMan;
    public AudioPlayerManager audioPlayerManager;
    public PlayerManager playerManager;
    public static MYSQL mysql;

    public static void main(String[] args) throws LoginException {
        new Dream();
    }
    public Dream() throws LoginException {
        INSTANCE = this;

         mysql = new MYSQL();

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken("");

        builder.setActivity(Activity.playing("#help"));
        builder.setStatus(OnlineStatus.ONLINE);

        this.audioPlayerManager = new DefaultAudioPlayerManager();
        this.playerManager = new PlayerManager();


        this.cmdMan = new CommandManager();
        builder.addEventListeners(new CommandListener());


        shardMan = builder.build();
        System.out.println("Bot online");

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);




        shutdown();



    }
    public void shutdown(){
        new Thread(() -> {
            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while((line = reader.readLine()) != null) {

                    if (line.equalsIgnoreCase("exit")) {
                        if (shardMan != null) {
                            shardMan.setStatus(OnlineStatus.OFFLINE);
                            shardMan.shutdown();
                            System.out.println("Bot offline");
                        }

                        reader.close();

                    }
                    else{
                        System.out.println("Use 'exit' to shutdown");
                    }

                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public CommandManager getCmdMan() {
        return cmdMan;
    }
}
