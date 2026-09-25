package ro.florinradu.concurrentjava.extra.future;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public record CoinModel(Integer rank, String name, Quotes quotes) {
}

record Quotes(@SerializedName("USD") UsdQuote usd) {
}

record UsdQuote(BigDecimal price, @SerializedName("market_cap") BigDecimal marketCap) {
}
