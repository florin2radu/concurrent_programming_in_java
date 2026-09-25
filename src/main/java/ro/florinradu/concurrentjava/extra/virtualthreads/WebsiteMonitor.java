package ro.florinradu.concurrentjava.extra.virtualthreads;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class WebsiteMonitor {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final List<String> URLS = List.of(
            "https://openjdk.org",
            "https://github.com",
            "https://www.wikipedia.org",
            "https://www.oracle.com",
            "https://www.apache.org",
            "https://stackoverflow.com",
            "https://www.mozilla.org"
    );

    private WebsiteMonitor() {
        /* This utility class should not be instantiated */
    }

    public static int getWebsiteCount() {
        return URLS.size();
    }

    public static void checkWebsites(Consumer<WebsiteResult> onResult, Runnable onFinished) {
        Thread.startVirtualThread(() -> {
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                var completionService = new ExecutorCompletionService<WebsiteResult>(executor);
                URLS.forEach(url -> completionService.submit(() -> checkWebsite(url)));

                for (int i = 0; i < URLS.size(); i++) {
                    WebsiteResult result = completionService.take().get();
                    onResult.accept(result);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                e.getCause().printStackTrace();
            } finally {
                onFinished.run();
            }
        });
    }

    private static WebsiteResult checkWebsite(String url) {
        long start = System.nanoTime();

        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(10)).GET().build();
            HttpResponse<Void> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
            long duration = (System.nanoTime() - start) / 1_000_000;
            return new WebsiteResult(url, String.valueOf(response.statusCode()), duration, Thread.currentThread().toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            long duration = (System.nanoTime() - start) / 1_000_000;
            return new WebsiteResult(url, "INTERRUPTED", duration, Thread.currentThread().toString());
        } catch (IOException e) {
            long duration = (System.nanoTime() - start) / 1_000_000;
            return new WebsiteResult(url, "ERROR", duration, Thread.currentThread().toString());
        }
    }
}
