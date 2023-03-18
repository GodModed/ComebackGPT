package social.godmode;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.ReceivedMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EventHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            super.onMessageReceived(event);
            return;
        }
        if (!event.getMessage().getMentions().isMentioned(event.getJDA().getSelfUser())) {
            super.onMessageReceived(event);
            return;
        }

        if (event.getMessage().getMessageReference() == null) {
            super.onMessageReceived(event);
            return;
        }
//        String content = String.format("%s", message);
        Message.suppressContentIntentWarning();
        String messageID = event.getMessage().getMessageReference().getMessageId();
        Message message = event.getChannel().retrieveMessageById(messageID).complete();
        if (message == null) {
            super.onMessageReceived(event);
            return;
        }
        String content = message.getContentRaw();
        if (content.length() == 0) {
            super.onMessageReceived(event);
            return;
        }
        String response = Main.getGpt().generateResponse(content);
        message.reply(response).queue();
        event.getMessage().delete().queue();
    }

}
