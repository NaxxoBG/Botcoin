package main;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CoinTicker implements Callable<String> {
	private final short TIMEOUT = 600;
	private String currency;
	private String coin;
	private final String btcUrlString = "https://realtimebitcoin.info/stats";
	private final String btcUrlString2 = "https://www.coingecko.com/coins/currency_exchange_rates.json";
	private String ethUrlString = "https://ethereumprice.org/wp-content/themes/theme/inc/exchanges/price-data.php?coin=eth&cur=ethusd&ex=waex&dec=2&start_time=24%20Hour";
	private String allAvCoins = "https://graphs.coinmarketcap.com/currencies/holder/";
	private final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";
	private final String btcDelimiter = "+---------+----------+\n";
	private final String btcDelimiter2 = "+-------+----------+\n";
	private final String ethDelimiter = "+-----------------+-----------+\n";
	private final String allOthCoinsDelimiter = "+--------------+----------+\n";

	private CoinTicker(String coin, String currency) {
		this.coin = coin;
		this.currency = currency;
	}

	private String processGsonResult(String requestResult, String url) {
		JsonObject obj = new Gson().fromJson(requestResult, JsonObject.class);
		switch (coin.toUpperCase()) {
		case "BTC":
		case "BITCOIN":
			switch (url) {
			case btcUrlString:
				return StringUtils.appendIfMissing(StringUtils.prependIfMissing(obj.getAsJsonObject("ticker").getAsJsonObject(currency).entrySet().parallelStream()
						.map(e -> String.format("|%7.6s  | %9.7s|\n", e.getKey(), e.getValue()))
						.collect(Collectors.joining()), btcDelimiter), btcDelimiter);
			case btcUrlString2:
				return StringUtils.appendIfMissing(
						StringUtils.prependIfMissing(String.format("|Price  |  %7.8s|\n", obj.getAsJsonObject("rates").get(currency.toLowerCase()).toString().replaceAll("\"", "")), btcDelimiter2), btcDelimiter2);
			default:
				return null;
			}
		case "ETH":
		case "ETHEREUM":
			String result = StringUtils.appendIfMissing(StringUtils.prependIfMissing(obj.entrySet().parallelStream()
					.map(e -> String.format("|%-15.15s  | %10.10s|\n", e.getKey(), e.getValue()))
					.collect(Collectors.joining()), ethDelimiter), ethDelimiter);
			if (result.contains("nan")) {
				System.out.println("Try with another currency");
				return "No info :(";
			} else {
				return result;
			}
		default:
			return StringUtils.appendIfMissing(StringUtils.prependIfMissing(String.format("|%12.12s  | %9.7s|\n", coin, obj.getAsJsonArray("price_usd").get(obj.getAsJsonArray("price_usd").size()-1).getAsJsonArray().get(1).toString()), allOthCoinsDelimiter), allOthCoinsDelimiter);
		}
	}

	/**This method accepts a coin(e.g. BTC, ETH) and a currency(e.g. USD, EUR) and prints out the data to the console.
	 * @param coin <p>Currently BTC(Bitcoin) and ETH(Ethereum) are supported.</p>
	 * @param currency  <p>Check the table below to see which currencies can be used.</p>
	 * <table border="1">
	 * 	 <th>Currency Pair BTC</th> <th>Currency Pair ETH</th>
  			<tr>
    			<td colspan="2"> USD </td>
  			</tr>
  			<tr>
    			<td colspan="2"> EUR </td>
  			</tr>
  			<tr>
    			<td colspan="2"> AUD </td>
  			</tr>
  			<tr>
    			<td colspan="2"> CAD </td> 
  			</tr>
  			<tr>
    			<td colspan="2"> GBP </td>
  			</tr>
  			<tr>
    			<td colspan="2"> JPY </td>
  			</tr>
  			<tr>
    			<td> DKK </td> <td> BTC </td>
  			</tr>
  			<tr>
    			<td> CHF </td>
  			</tr>
  			<tr>
  				<td> CNY </td>
  			</tr>
  			<tr>
    			<td> HKD </td>
  			</tr>
  			<tr>
    			<td> NZD </td>
  			</tr>
  			<tr>
    			<td> PLN </td>
  			</tr>
  			<tr>
    			<td> RUB </td>
  			</tr>
  			<tr>
    			<td> SEK </td>
  			</tr>
  			<tr>
    			<td> SGD </td>
  			</tr>
		</table>
	 * @throws TimeoutException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static String checkCoinPrice(String coin, String currency) throws InterruptedException, ExecutionException, TimeoutException {
		String checked;
		if (coin.equalsIgnoreCase("btc") || coin.equalsIgnoreCase("bitcoin") || coin.equalsIgnoreCase("eth") || coin.equalsIgnoreCase("ethereum")) {
			checked = currency.toUpperCase();
			System.out.println(String.format("We are getting the latest stats on %s in %s for you...", coin.toUpperCase(), checked));
		} else {
			checked = "USD";
			System.out.println("For " + coin + " only USD is available");
		}
		ExecutorService exec = Executors.newSingleThreadExecutor();
		return exec.submit(new CoinTicker(coin, currency)).get();
	}

	@Override
	public String call() throws Exception {
		try {
			URLConnection conn;
			switch (coin.toUpperCase()) {
			case "BTC":
			case "BITCOIN":
				this.currency = this.currency.toUpperCase();
				conn = new URL(btcUrlString).openConnection();
				try {
					return processGsonResult(IOUtils.toString(requestAndReturn(conn), "UTF-8"), btcUrlString);
				} catch (IOException e) {
					System.out.println("Switching to another source...");
					conn = new URL(btcUrlString2).openConnection();
					return processGsonResult(IOUtils.toString(requestAndReturn(conn), "UTF-8"), btcUrlString2);
				}
			case "ETH":
			case "ETHEREUM":
				ethUrlString = StringUtils.replace(ethUrlString, "ethusd", "eth".concat(currency.toLowerCase()));
				conn = new URL(ethUrlString).openConnection();
				return processGsonResult(IOUtils.toString(requestAndReturn(conn), "UTF-8"), ethUrlString);
			default:
				allAvCoins = StringUtils.replace(allAvCoins, "holder", coin.toLowerCase());
				conn = new URL(allAvCoins).openConnection();
				return processGsonResult(IOUtils.toString(requestAndReturn(conn), "UTF-8"), allAvCoins);
			}
		} catch (IOException | NullPointerException e) {
			return "The currency you have entered is not available :(";
		}
	}

	private InputStream requestAndReturn(URLConnection conn) throws MalformedURLException, IOException {
		conn.setRequestProperty("User-Agent", userAgent);
		conn.setConnectTimeout(TIMEOUT);
		return conn.getInputStream();
	}
}