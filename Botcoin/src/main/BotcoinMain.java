package main;

import org.apache.log4j.BasicConfigurator;

public class BotcoinMain {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Botcoin bot = Botcoin.getBotInstance();
		bot.getEvDispatcher().registerListener(new AnnotationListeners());
	}
}