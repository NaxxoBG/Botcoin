package commands;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import main.Botcoin;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;

public class HelpCmd implements CommandExecutor {

	@Command(aliases = { "$help" }, description = "Shows information about the commands of the bot.", usage = "$help")
	public void help(IChannel channel) {
		EmbedObject helpMsg = new EmbedBuilder()
				.withAuthorName("Botcoin")
				.withColor(ThreadLocalRandom.current().nextInt(188290, 16777216))
				.appendField("Let's see the list of available commands", "```1) $help -> I guess you know what it does...\n"
						+ "\n2) $check [coin] [currency] -> Shows you information about the specified coin in that currency\n"
						+ "\n3) $clear -> clears all previous messages of the bot```", false)
				.withThumbnail("https://cdn.pixabay.com/photo/2016/10/18/18/19/question-mark-1750942_960_720.png")
				.withTimestamp(LocalDateTime.now())
				.withFooterText("⏰")
				.build();
		new MessageBuilder(Botcoin.getBotInstance().getClient()).withChannel(channel).withEmbed(helpMsg).build();
	}
}