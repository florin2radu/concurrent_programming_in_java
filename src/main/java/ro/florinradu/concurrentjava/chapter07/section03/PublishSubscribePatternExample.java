package ro.florinradu.concurrentjava.chapter07.section03;

// Book section: 7.3.2 Publish–Subscribe Pattern
// Adapted into a standalone runnable example.
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class PublishSubscribePatternExample {
    public static void main(
        String[] args) throws InterruptedException {
        CountDownLatch done = new CountDownLatch(1);

        try (SubmissionPublisher<String> publisher = new SubmissionPublisher<>()) {
            Flow.Subscriber<String> subscriber =
                new Flow.Subscriber<>() {
                private Flow.Subscription subscription;

                @Override
                public void onSubscribe(
                    Flow.Subscription subscription) {
                    this.subscription = subscription;
                    subscription.request(1);
                }

                @Override
                public void onNext(String item) {
                    System.out.println("Received: " + item);
                    subscription.request(1);
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                    done.countDown();
                }

                @Override
                public void onComplete() {
                    System.out.println("Completed");
                    done.countDown();
                }
            };

            publisher.subscribe(subscriber);
            publisher.submit("Hello!");
            publisher.submit("Event #1");
            publisher.submit("Event #2");
        }

        done.await();
    }
}
