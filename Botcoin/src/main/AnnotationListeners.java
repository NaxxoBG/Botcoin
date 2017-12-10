package main;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import sx.blah.discord.Discord4J;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MessageHistory;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

/**This class was the previous implementation of the bot commands. The approach used here(using the EventSubscriber annotation) proved
 * to be not that good structure-wise. Also maintainability was something that this structure would hinder if newer commands are added.
 * @author Naxxo
 */
public class AnnotationListeners
{
	private static Botcoin bot = Botcoin.getBotInstance();

	private static final String PREFIX = "$";
	private static final String CLEAR_CMD = PREFIX + "clear";
	private static final String CHECK_COIN_PRICE = PREFIX + "check";

	@EventSubscriber
	public void checkCoin(MessageReceivedEvent event) {
		String message = event.getMessage().getContent();
		if (message.startsWith(CHECK_COIN_PRICE)) {
			String[] args = message.split(" ");

			RequestBuffer.request(() -> {
				IChannel channel = event.getChannel();
				try {
					String res = CoinTicker.checkCoinPrice(args[1], args[2]);
					String imageUrl = "https://api.blockchain.info/charts/preview/market-price.png?lang=bg&h=810&w=1440";
					EmbedBuilder emBuild;

					if (args[1].equalsIgnoreCase("bitcoin") || args[1].equalsIgnoreCase("btc")) {
						emBuild = new EmbedBuilder()
								.withAuthorName("Bitcoin")
								.withColor(188290)
								.withDescription("The market price in " + args[2].toUpperCase())
								.withImage(imageUrl)
								.appendField("Let's see how much you lost", "...and the moment of truth   😱", false)
								.appendField("Information", String.format("```\n%s```", res), false)
								.withThumbnail(String.format("https://files.coinmarketcap.com/static/img/coins/32x32/%s.png", "bitcoin"))
								.withTimestamp(LocalDateTime.now())
								.withFooterText("⏰");
					} else {
						emBuild = new EmbedBuilder()
								.withAuthorName(StringUtils.capitalize(args[1]))
								.withColor(ThreadLocalRandom.current().nextInt(188290, 16777216)) // green is 188290
								.withDescription("The market price in " + args[2].toUpperCase())
								.appendField("Let's see how much you lost", "...and the moment of truth   😱", false)
								.appendField("Information", String.format("```\n%s```", res), false)
								.withThumbnail(String.format("https://files.coinmarketcap.com/static/img/coins/32x32/%s.png", args[1]))
								.withTimestamp(LocalDateTime.now())
								.withFooterText("⏰");
					}

					new MessageBuilder(bot.getClient()).withChannel(channel).withEmbed(emBuild.build()).build();
				} catch (DiscordException | MissingPermissionsException | InterruptedException | ExecutionException | TimeoutException e) {
					Discord4J.LOGGER.debug("Error while getting coin data");
				}
			});
		}
	}

	@EventSubscriber
	public void clearBotMessages(MessageReceivedEvent event) {
		if (event.getMessage().getContent().equals(CLEAR_CMD)) {
			RequestBuffer.request(() -> {
				new MessageHistory(event.getChannel().getMessageHistoryFrom(event.getMessage().getLongID())
						.stream()
						.filter(m -> m.getAuthor().getName().equalsIgnoreCase("Botcoin"))
						.collect(Collectors.toList()))
				.bulkDelete();
			});
		}
	}
}