package ro.florinradu.concurrentjava.chapter02.section06;

// Book section: 2.6.3 Asynchronous Model
// Adapted into a standalone runnable example.

import java.util.concurrent.CompletableFuture;

public class AsyncPipelineExample {
    private static String getDataFromApi() {
        return "java";
    }

    private static String process(String data) {
        return data.toUpperCase();
    }

    public static void main(String[] args) {
        CompletableFuture<Void> pipeline =
                CompletableFuture.supplyAsync(AsyncPipelineExample::getDataFromApi)
                        .thenApply(AsyncPipelineExample::process)
                        .thenAccept(result ->
                                System.out.println("Result: " + result));

        pipeline.join(); // keep main alive until the pipeline completes
    }
}
