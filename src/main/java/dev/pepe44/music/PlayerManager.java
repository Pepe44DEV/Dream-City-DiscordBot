package dev.pepe44.music;

import dev.pepe44.Dream;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    public ConcurrentHashMap<Long, MusicContorller> controller;

    public PlayerManager(){
        this.controller = new ConcurrentHashMap<Long, MusicContorller>();
    }

    public MusicContorller getController(long guildid) {
        MusicContorller mc = null;

        if (this.controller.containsKey(guildid)) {
            mc = this.controller.get(guildid);
        }else{
            mc = new MusicContorller((Dream.INSTANCE.bot.getGuildById(guildid)));

            this.controller.put(guildid, mc);

        }

        return mc;
    }

}
