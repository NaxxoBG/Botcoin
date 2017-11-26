package main;

public interface ICoinTicker {
	//Urls used for the HTTP request for different coins.
	final String ETH_URL = "https://ethereumprice.org/wp-content/themes/theme/inc/exchanges/price-data.php?coin=eth&cur=ethusd&ex=waex&dec=2&start_time=24%20Hour";
	final String ALT_COINS_URL = "https://graphs.coinmarketcap.com/currencies/holder/";
	final String BTC_URL = "https://realtimebitcoin.info/stats";
	final String BTC_URL_2 = "https://www.coingecko.com/coins/currency_exchange_rates.json";
	final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

	//Delimiters used for the ASCI table printed in the EmbedObject. Since the informations for the different coins varies,
	//the delimiters have different dimensions.
	final String BTC_DELIMITER = "+---------+----------+\n";
	final String BTC_DELIMITER_2 = "+-------+----------+\n";
	final String ETH_DELIMITER = "+-----------------+-----------+\n";
	final String ALT_COINS_DELIMITER = "+--------------+----------+\n";
}