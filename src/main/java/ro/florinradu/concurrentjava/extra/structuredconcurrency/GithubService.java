package ro.florinradu.concurrentjava.extra.structuredconcurrency;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public class GithubService {
    private static final String BASE_URL = "https://api.github.com/users/";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    private GithubService() {
        /* This utility class should not be instantiated */
    }

    public static GithubDashboard loadDashboard(String username) throws InterruptedException {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAllSuccessfulOrThrow())) {
            var profileTask = scope.fork(() -> loadProfile(username));
            var repositoriesTask = scope.fork(() -> loadRepositories(username));
            var eventsTask = scope.fork(() -> loadEvents(username));

            scope.join();

            return new GithubDashboard(profileTask.get(), repositoriesTask.get(), eventsTask.get());
        }
    }

    private static Profile loadProfile(String username) throws IOException, InterruptedException {
        String json = get(BASE_URL + username);
        return GSON.fromJson(json, Profile.class);
    }

    private static List<RepositoryInfo> loadRepositories(String username) throws IOException, InterruptedException {
        String json = get(BASE_URL + username + "/repos?sort=updated&per_page=5");
        RepositoryInfo[] repositories = GSON.fromJson(json, RepositoryInfo[].class);
        return Arrays.asList(repositories);
    }

    private static List<EventInfo> loadEvents(String username) throws IOException, InterruptedException {
        String json = get(BASE_URL + username + "/events/public?per_page=5");
        EventInfo[] events = GSON.fromJson(json, EventInfo[].class);
        return Arrays.asList(events);
    }

    private static String get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", "ConcurrentProgrammingInJava")
                .GET().build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("GitHub API returned HTTP " + response.statusCode());
        }

        return response.body();
    }
}
