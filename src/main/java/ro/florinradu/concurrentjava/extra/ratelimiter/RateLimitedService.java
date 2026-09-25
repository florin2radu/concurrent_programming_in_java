package ro.florinradu.concurrentjava.extra.ratelimiter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class RateLimitedService {
    private static final int REQUEST_COUNT = 10;
    private static final int MAX_CONCURRENT_REQUESTS = 3;
    public static final String BASE_URL = "https://httpbin.org/delay/1?request=";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Semaphore SEMAPHORE = new Semaphore(MAX_CONCURRENT_REQUESTS);

    private RateLimitedService() {
        /* This utility class should not be instantiated */
    }

    public static int getRequestCount() {
        return REQUEST_COUNT;
    }

    public static void execute(Consumer<RequestResult> onResult, Runnable onFinished) {
        new Thread(() -> {
            try (var executor = Executors.newFixedThreadPool(REQUEST_COUNT)) {
                var completionService = new ExecutorCompletionService<RequestResult>(executor);

                for (int i = 1; i <= REQUEST_COUNT; i++) {
                    int requestNumber = i;
                    completionService.submit(() -> callService(requestNumber));
                }

                for (int i = 0; i < REQUEST_COUNT; i++) {
                    onResult.accept(completionService.take().get());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                onFinished.run();
            }
        }, "request-controller").start();
    }

    private static RequestResult callService(int requestNumber) {
        long start = System.nanoTime();

        try {
            SEMAPHORE.acquire();

            try {
                HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + requestNumber)).timeout(Duration.ofSeconds(10)).GET().build();
                HttpResponse<Void> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
                long duration = (System.nanoTime() - start) / 1_000_000;
                return new RequestResult(requestNumber, String.valueOf(response.statusCode()), duration);
            } finally {
                SEMAPHORE.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            long duration = (System.nanoTime() - start) / 1_000_000;
            return new RequestResult(requestNumber, "INTERRUPTED", duration);
        } catch (Exception e) {
            long duration = (System.nanoTime() - start) / 1_000_000;
            return new RequestResult(requestNumber, "ERROR", duration);
        }
    }
}
