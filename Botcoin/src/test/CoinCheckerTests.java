package test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import junit.framework.TestCase;
import main.CoinTicker;

public class CoinCheckerTests extends TestCase {

	public void testBtc() throws InterruptedException, ExecutionException, TimeoutException {
		System.out.println(CoinTicker.checkCoinPrice("btc", "usd"));
	}

	public void testDash() throws InterruptedException, ExecutionException, TimeoutException {
		System.out.println(CoinTicker.checkCoinPrice("dash", "usd"));
	}

	public void testEth() throws InterruptedException, ExecutionException, TimeoutException {
		System.out.println(CoinTicker.checkCoinPrice("eth", "cad"));
	}

	public void testMonero() throws InterruptedException, ExecutionException, TimeoutException {
		System.out.println(CoinTicker.checkCoinPrice("monero", "eur"));
	}

	public void testNeo() throws InterruptedException, ExecutionException, TimeoutException {
		System.out.println(CoinTicker.checkCoinPrice("neo", "jpy"));
	}

	public void testHttpClient() {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet btcInfo = new HttpGet("https://api.coinmarketcap.com/v1/ticker/bitcoin/?convert=EUR");
		try {
			CloseableHttpResponse response = client.execute(btcInfo);
			System.out.println(IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset()));
			response.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}