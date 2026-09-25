package ro.florinradu.concurrentjava.extra.completablefuture;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class WeatherApiUtils {
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    private static CompletableFuture<TemperatureResult> getTemperatureAsync(City city) {
        URI target = URI.create(BASE_URL.formatted(city.getLat(), city.getLng()));

        HttpRequest request = HttpRequest.newBuilder(target)
                .GET()
                .build();

        return HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        throw new RuntimeException("Getting temperature for " + city.getDisplayName() + "; invalid response code: " + response.statusCode());
                    }

                    TemperatureModel model = GSON.fromJson(response.body(), TemperatureModel.class);

                    return new TemperatureResult(
                            city,
                            model.current().temperature(),
                            model.currentUnits().temperatureUnit());
                });
    }

    public static CompletableFuture<List<TemperatureResult>> getTemperaturesAsync() {
        List<CompletableFuture<TemperatureResult>> futures = Arrays.stream(City.values())
                .map(WeatherApiUtils::getTemperatureAsync)
                .toList();

        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return all.thenApply(ignored ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .sorted(Comparator.comparing(result -> result.city().getDisplayName()))
                        .toList());
    }
}
