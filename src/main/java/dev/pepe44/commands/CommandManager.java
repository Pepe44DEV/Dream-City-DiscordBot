package dev.pepe44.commands;

import dev.pepe44.commands.types.ServerCommand;
import dev.pepe44.music.PlayCommand;
import dev.pepe44.music.StopCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager() {
        this.commands = new ConcurrentHashMap<>();

        this.commands.put("clear", new ClearCommand());
        this.commands.put("play", new PlayCommand());
        this.commands.put("stop", new StopCommand());
        this.commands.put("help", new HelpCommand());
        this.commands.put("status", new StatusCommand());
        this.commands.put("setup", new InfoCommand());
        this.commands.put("time", new TimeCommand());
        this.commands.put("issue", new IssueCommand());
        this.commands.put("voice", new VoiceCommand());
    }
    public boolean perform(String command, Member m, TextChannel channel, Message message){
        ServerCommand cmd;
        if((cmd = this.commands.get(command.toLowerCase())) != null){
            cmd.performCommand(m, channel, message);
            return true;
        }

        return false;
    }
}
