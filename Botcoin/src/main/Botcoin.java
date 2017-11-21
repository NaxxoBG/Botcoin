package main;

import commands.CheckCoinCmd;
import commands.ClearCmd;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.Discord4JHandler;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

public class Botcoin {

	private static final String TOKEN = "Mzc2Njk4NTExNjgyNTY4MTk0.DOdsSA.ZTecU4669mbMYO3npXo-EoUIU4A";
	private IDiscordClient client;
	private ClientBuilder builder;
	private static Botcoin bot;

	private Botcoin() {
		createClient();
	}

	private void createClient() {
		builder = new ClientBuilder();
		builder.withToken(TOKEN);
		builder.setMaxMessageCacheCount(250);
		builder.setMaxReconnectAttempts(3);
		builder.online();
	}

	public void activate() {
		this.client = this.builder.login();
		CommandHandler cmdHandler = new Discord4JHandler(client);
		cmdHandler.registerCommand(new CheckCoinCmd());
		cmdHandler.registerCommand(new ClearCmd());
	}

	public static Botcoin getBotInstance() {
		if (bot == null) {
			bot = new Botcoin();
		}
		return bot;
	}

	public IDiscordClient getClient() {
		return client;
	}
}