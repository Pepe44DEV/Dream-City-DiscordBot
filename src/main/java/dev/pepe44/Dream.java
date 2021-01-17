package dev.pepe44;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import dev.pepe44.commands.CommandManager;
import dev.pepe44.listener.CommandListener;
import dev.pepe44.manager.MYSQL;
import dev.pepe44.music.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
    public JDA bot;


    //public ShardManager shardMan;
    private CommandManager cmdMan;
    public AudioPlayerManager audioPlayerManager;
    public PlayerManager playerManager;
    public static MYSQL mysql;

    public static String youtrack;

    public static final String GUILD = "679392951159226368";
    public static final String CHANNEL_VERBESSERUNG = "782612502650945546";
    public static final String CHANNEL_TODO = "775787300356358146";

    public static void main(String[] args) throws LoginException {
        if(args.length == 4) {
            new Dream(args[0], args[1], args[2], args[3]);
        } else {
            System.out.println("Parameter: 1: Token, 2:DBUser, 3:DBPW, 4:YouTrack Token");
            System.exit(1);
        }
    }
    public Dream(String token, String dbuser, String dbpw, String youtrack) throws LoginException {
        INSTANCE = this;
        this.youtrack = youtrack;

        mysql = new MYSQL("localhost", "3306", "essentialmode", dbuser, dbpw);


        JDABuilder b = JDABuilder.createDefault(token);


        b.setActivity(Activity.playing("#help"));
        b.setStatus(OnlineStatus.ONLINE);

        this.audioPlayerManager = new DefaultAudioPlayerManager();
        this.playerManager = new PlayerManager();


        this.cmdMan = new CommandManager();
        b.addEventListeners(new CommandListener());


        bot = b.build();
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
                        if (bot != null) {
                            bot.shutdown();
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
