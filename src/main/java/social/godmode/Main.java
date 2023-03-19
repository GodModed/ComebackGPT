package social.godmode;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static String token;
    private static String apiKey;
    @Getter
    private static GPT gpt;
    public static void main(String[] args) throws InterruptedException {
        loadConfig();
        JDA jda = JDABuilder.createDefault(token).build();
        jda.awaitReady();
        gpt = new GPT(apiKey);
        // register eventHandler as a listener
        jda.addEventListener(new EventHandler());
    }

    private static void loadConfig() {
        try {
            FileReader reader = new FileReader("./config.json");
            JSONObject config = new JSONObject(new JSONTokener(reader));
            reader.close();
            token = config.getString("discord_token");
            apiKey = config.getString("openai_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}