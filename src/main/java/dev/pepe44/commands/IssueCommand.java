package dev.pepe44.commands;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dev.pepe44.Dream;
import dev.pepe44.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

public class IssueCommand implements ServerCommand {
    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        String name = m.getEffectiveName();

        String msg = message.getContentRaw().replaceAll("#issue", "");
        if(msg.length() > 0) {
            try {
                JSONObject object = new JSONObject();
                object.put("summary", "Discord-Meldung von " + name + " (" + m.getUser().getAsTag() + ")");
                object.put("description", filter(msg));

                JSONArray fields = new JSONArray();

                JSONObject prio = new JSONObject();
                prio.put("name", "Priority");
                prio.put("$type", "SingleEnumIssueCustomField");
                JSONObject prio_sub = new JSONObject();
                prio_sub.put("name", getPriority(msg, m));
                prio.put("value", prio_sub);
                fields.put(prio);

                JSONObject meld = new JSONObject();
                meld.put("name", "Meldender");
                meld.put("$type", "SimpleIssueCustomField");
                meld.put("value", m.getUser().getAsTag());
                fields.put(meld);

                JSONObject source = new JSONObject();
                source.put("name", "Quelle");
                source.put("$type", "SingleEnumIssueCustomField");
                JSONObject source_sub = new JSONObject();
                source_sub.put("name", "Discord");
                source.put("value", source_sub);
                fields.put(source);



                object.put("customFields", fields);

                JSONObject project = new JSONObject();
                project.put("id", "0-1");
                object.put("project", project);
                System.out.println(object.toString());
                HttpResponse<String> resp = Unirest.post("https://bug.dream-city.net/api/issues")
                        .header("Authorization", "Bearer " + Dream.youtrack)
                        .header("Content-Type", "application/json")
                        .body(object.toString())
                        .asString();
                if(resp.getStatus() == 200) {
                    channel.sendMessage("Vielen Dank für die Meldung. Der Fehler wurde aufgenommen.").queue();
                } else {
                    channel.sendMessage("Ein Fehler ist beim Erstellen der Meldung aufgetreten." + resp.getStatus()).queue();
                    System.out.println(resp.getBody());
                }
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        } else {
            channel.sendMessage("Um einen Fehler zu melden, nutze #issue <Beschreibung des Fehlers>").queue();
        }
    }

    public String getPriority(String mess, Member m) {
        if(!m.getRoles().contains(m.getGuild().getRoleById("739828792440389736")))
            return "Normal";
        if(mess.contains("!highest")) {
            return "Show-stopper";
        }
        if(mess.contains("!higher")) {
            return "Critical";
        }
        if(mess.contains("!high")) {
            return "Major";
        }
        if(mess.contains("!low")) {
            return "Minor";
        }
        return "Normal";
    }
    public String filter(String mess) {
        String prio = mess.replaceAll("!highest", "").replaceAll("!higher", "").replaceAll("!high", "").replaceAll("!low", "");
        return prio;
    }
}
