package main;

public class CoinTickerConstants {
	static final short TIMEOUT = 600;
	static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

	//Urls used for the HTTP request for different coins.
	static final String ETH_URL = "https://ethereumprice.org/wp-content/themes/theme/inc/exchanges/price-data.php?coin=eth&cur=ethusd&ex=waex&dec=2&start_time=24%20Hour";
	static final String ALT_COINS_URL = "https://graphs.coinmarketcap.com/currencies/holder/";
	static final String BTC_URL = "https://realtimebitcoin.info/stats";
	static final String BTC_URL_2 = "https://www.coingecko.com/coins/currency_exchange_rates.json";

	//Delimiters used for the ASCI table printed in the EmbedObject. Since the informations for the different coins varies,
	//the delimiters have different dimensions.
	static final String BTC_DELIMITER = "+---------+----------+\n";
	static final String BTC_DELIMITER_2 = "+-------+----------+\n";
	static final String ETH_DELIMITER = "+-----------------+-----------+\n";
	static final String ALT_COINS_DELIMITER = "+--------------+----------+\n";
}