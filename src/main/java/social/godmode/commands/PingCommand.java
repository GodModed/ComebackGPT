package social.godmode.commands;

import me.nickrest.command.Command;
import me.nickrest.command.data.CommandInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "ping", description = "Pings the bot")
public class PingCommand extends Command {

    @Override
    public void handle(@NotNull SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();
        MessageEmbed pingEmbed = new EmbedBuilder()
                .setTitle("Pong!")
                .setDescription("Pong! " + event.getJDA().getGatewayPing() + "ms")
                .build();
        event.getHook().editOriginalEmbeds(pingEmbed).queue();
    }

}
