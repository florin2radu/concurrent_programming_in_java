package ro.florinradu.concurrentjava.extra.structuredconcurrency;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record GithubDashboard(Profile profile, List<RepositoryInfo> repositories, List<EventInfo> events) {
}

record Profile(String login, String name, @SerializedName("public_repos") int publicRepos, int followers, int following,
               @SerializedName("html_url") String htmlUrl) {
}

record RepositoryInfo(String name, String language, @SerializedName("stargazers_count") int stars,
                      @SerializedName("updated_at") String updatedAt) {
}

record EventInfo(String type, EventRepository repo, @SerializedName("created_at") String createdAt) {
}

record EventRepository(String name) {
}
