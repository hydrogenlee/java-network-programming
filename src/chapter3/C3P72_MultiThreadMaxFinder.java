package chapter3;

import java.util.concurrent.*;

public class C3P72_MultiThreadMaxFinder {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = {1, 4, 5, 6, 7, 1, 2, 4};
        C3P72_MultiThreadMaxFinder finder = new C3P72_MultiThreadMaxFinder();
        System.out.println(finder.max(arr));
    }

    public int max(int[] arr) throws ExecutionException, InterruptedException {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        if (arr.length == 1) {
            return arr[0];
        }

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> maxLeft = executor.submit(new FindMaxTask(arr, 0, arr.length / 2));
        Future<Integer> maxRight = executor.submit(new FindMaxTask(arr, arr.length / 2, arr.length));
        int max = Math.max(maxLeft.get(), maxRight.get());
        executor.shutdown();
        return max;
    }
}


class FindMaxTask implements Callable<Integer> {
    private int start;
    private int end;
    private int[] arr;

    public FindMaxTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }
    @Override
    public Integer call() throws Exception {
        int max = Integer.MIN_VALUE;
        for (int i = start; i < end; i++) {
            max = Math.max(max, arr[i]);
        }

        return max;
    }
}
