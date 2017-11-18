package main;

import org.apache.log4j.BasicConfigurator;

import commands.CheckCoinCmd;
import commands.ClearCmd;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.Discord4JHandler;

public class BotcoinMain {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Botcoin bot = Botcoin.getBotInstance();
		//bot.getEvDispatcher().registerListener(new AnnotationListeners());
		CommandHandler cmdHandler = new Discord4JHandler(bot.getClient());
		cmdHandler.registerCommand(new CheckCoinCmd());
		cmdHandler.registerCommand(new ClearCmd());
	}
}