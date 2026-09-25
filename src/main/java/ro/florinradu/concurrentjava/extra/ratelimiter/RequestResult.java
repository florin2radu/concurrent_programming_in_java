package ro.florinradu.concurrentjava.extra.ratelimiter;

public record RequestResult(int request, String status, long duration) {
}
