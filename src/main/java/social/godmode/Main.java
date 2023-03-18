package social.godmode;

import lombok.Getter;
import me.nickrest.command.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import social.godmode.commands.PingCommand;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    @Getter
    private static GPT gpt;
    public static void main(String[] args) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(getToken()).build();
        CommandManager commandManager = new CommandManager(jda);
        commandManager.register(new PingCommand());
        jda.awaitReady();
        gpt = new GPT(getAPIKey());
        // register eventHandler as a listener
        jda.addEventListener(new EventHandler());
    }
    private static String getToken() {
        StringBuilder sb = new StringBuilder();
        try(FileReader reader = new FileReader("./token.txt")) {
            int c;
            while((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static String getAPIKey() {
        StringBuilder sb = new StringBuilder();
        try(FileReader reader = new FileReader("./api.txt")) {
            int c;
            while((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}