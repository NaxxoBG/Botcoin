package test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
	
	public void testRegex() {
		System.out.println(Arrays.toString("$check bitcoin usd".split(" ")));
	}
}