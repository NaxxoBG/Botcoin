package commands;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import main.Botcoin;
import main.CoinTicker;
import sx.blah.discord.Discord4J;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

public class CheckCoinCmd implements CommandExecutor {

	@Command(aliases = {"$check"}, description = "Shows you information about the specified coin in that currency.", usage = "$check [coin] [currency]")
	public void checkCoin(IChannel channel, String[] args) {
		RequestBuffer.request(() -> {
			try {
				String res = CoinTicker.checkCoinPrice(args[0], args[1]);
				String imageUrl = String.format("https://api.blockchain.info/charts/preview/market-price.png?lang=bg&end=%s&h=810&w=1440", Instant.now().getEpochSecond());
				EmbedBuilder emBuild;

				if (args[0].equalsIgnoreCase("bitcoin") || args[0].equalsIgnoreCase("btc")) {
					emBuild = new EmbedBuilder()
							.withAuthorName("Bitcoin")
							.withColor(ThreadLocalRandom.current().nextInt(188290, 16777216))
							.withDescription("The market price in " + args[1].toUpperCase())
							.withImage(imageUrl)
							.appendField("Let's see how much you have earned", "...and the moment of truth   😱", false)
							.appendField("Information", String.format("```\n%s```", res), false)
							.withThumbnail("https://files.coinmarketcap.com/static/img/coins/32x32/bitcoin.png")
							.withTimestamp(LocalDateTime.now())
							.withFooterText("⏰");
				} else {
					emBuild = new EmbedBuilder()
							.withAuthorName(StringUtils.capitalize(args[0]))
							.withColor(ThreadLocalRandom.current().nextInt(188290, 16777216)) // green is 188290
							.withDescription("The market price in " + args[1].toUpperCase())
							.appendField("Let's see how much you lost", "...and the moment of truth   😱", false)
							.appendField("Information", String.format("```\n%s```", res), false)
							.withThumbnail(String.format("https://files.coinmarketcap.com/static/img/coins/32x32/%s.png", args[0]))
							.withTimestamp(LocalDateTime.now())
							.withFooterText("⏰");
				}

				new MessageBuilder(Botcoin.getBotInstance().getClient()).withChannel(channel).withEmbed(emBuild.build()).build();
			} catch (DiscordException | MissingPermissionsException | InterruptedException | ExecutionException | TimeoutException e) {
				Discord4J.LOGGER.debug("Error while getting coin data");
			}
		});
	}
}