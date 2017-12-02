package commands;

import java.util.stream.Collectors;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageHistory;
import sx.blah.discord.util.RequestBuffer;

public class ClearCmd implements CommandExecutor {

	@Command(aliases = {"$clear"}, description = "clears all previous messages of the bot.", usage = "$clear")
	public void clearBotMessages(IChannel channel, IMessage message) {
		RequestBuffer.request(() -> {
			new MessageHistory(channel.getMessageHistoryFrom(message.getLongID())
					.stream()
					.filter(m -> m.getAuthor().getName().equalsIgnoreCase("Botcoin"))
					.collect(Collectors.toList()))
			.bulkDelete();
		});
	}
}