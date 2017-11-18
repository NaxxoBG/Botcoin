package commands;

import java.util.stream.Collectors;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.util.MessageHistory;
import sx.blah.discord.util.RequestBuffer;

public class ClearCmd implements CommandExecutor {

	@Command(aliases = {"$clear"}, description = "Clears the bot's messages", usage = "$clear")
	public void clearBotMessages(Channel channel, Message message) {
		RequestBuffer.request(() -> {
			new MessageHistory(channel.getMessageHistoryFrom(message.getLongID())
					.stream()
					.filter(m -> m.getAuthor().getName().equalsIgnoreCase("Botcoin"))
					.collect(Collectors.toList()))
			.bulkDelete();
		});
	}
}