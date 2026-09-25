package ro.florinradu.concurrentjava.extra.future;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CoinsApiUtils {
    private static final String BASE_URL = "https://api.coinpaprika.com/v1/tickers/";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    private static final List<String> COINS =
            List.of("btc-bitcoin", "eth-ethereum", "usdt-tether", "xrp-xrp", "bnb-binance-coin");

    private static CoinModel getCoin(String coinName) throws Exception {
        URI target = new URI(BASE_URL + coinName);
        HttpRequest request = HttpRequest.newBuilder(target).GET().build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String coinDataJson = response.body();
            return GSON.fromJson(coinDataJson, CoinModel.class);
        } else {
            throw new RuntimeException("Getting coin " + coinName + "; " + "Invalid response code: " + response.statusCode());
        }
    }

    public static List<CoinModel> getCoins() {
        List<CoinModel> coins = new ArrayList<>();
        List<Future<CoinModel>> futureCoins = new ArrayList<>();

        try (ExecutorService executor = Executors.newFixedThreadPool(5)) {
            COINS.forEach(coin -> futureCoins.add(executor.submit(() -> getCoin(coin))));

            futureCoins.forEach(futureCoin -> {
                try {
                    coins.add(futureCoin.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        coins.sort(Comparator.comparing(CoinModel::rank));
        return coins;
    }
}
