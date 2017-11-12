package main;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

public class Botcoin {
	public static Botcoin bot;
	private IDiscordClient client;
	private EventDispatcher dispatcher;
	private static final String TOKEN = "Mzc2Njk4NTExNjgyNTY4MTk0.DOdsSA.ZTecU4669mbMYO3npXo-EoUIU4A";

	private Botcoin() {
		createAndLoginClient(TOKEN);
	}

	private void createAndLoginClient(String token) {
		ClientBuilder clientBuilder = new ClientBuilder();
		clientBuilder.withToken(token);
		clientBuilder.setMaxMessageCacheCount(250);
		clientBuilder.setMaxReconnectAttempts(3);
		clientBuilder.online();
		this.client = clientBuilder.login();
		this.dispatcher = client.getDispatcher();
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

	public void setClient(IDiscordClient client) {
		this.client = client;
	}

	public EventDispatcher getEvDispatcher() {
		return this.dispatcher;
	}
}