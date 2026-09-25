package ro.florinradu.concurrentjava.chapter07.section02;

// Book section: 7.2.2 Work-Stealing Pattern
// Adapted into a standalone runnable example.

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10_000;

    private final long[] array;
    private final int start;
    private final int end;

    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            long sum = 0;

            for (int i = start; i < end; i++) {
                sum += array[i];
            }

            return sum;
        }

        int mid = start + length / 2;
        SumTask left = new SumTask(array, start, mid);
        SumTask right = new SumTask(array, mid, end);

        // make the left task available in parallel
        left.fork();
        long rightResult = right.compute();
        long leftResult = left.join();
        return leftResult + rightResult;
    }
}

public class WorkStealingPatternExample {
    public static void main(String[] args) {
        long[] array = new long[1_000_000];
        Arrays.fill(array, 1L);

        ForkJoinPool pool = new ForkJoinPool();

        try {
            long result = pool.invoke(new SumTask(array, 0, array.length));
            System.out.println("Sum: " + result);
        } finally {
            pool.shutdown();
        }
    }
}
