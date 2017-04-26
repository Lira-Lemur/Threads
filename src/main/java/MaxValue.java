/**
* Write a class called MaxValue that finds the maximum value in an array of ints using ThreadPool
* of 4 threads (use the executor to manage them). Your main should be similar as the one in the
* below SumThread example, though you should construct your array of random numbers instead of
* increasing numbers. You may assume in your threaded code that the array has at least 4 elements.
*/

import java.util.*;
import java.util.concurrent.*;

public class MaxValue implements Callable {
    private int[] array;
    private int lo, hi;
    private int maxValue = 0;

    private MaxValue(int[] arr, int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        this.array = arr;
    }

    public Integer call() throws Exception {
        array[0] = maxValue;

        for (int i = lo; i < hi; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    private static Object findMaxValue(int[] array) throws InterruptedException {

        int arrayLength = array.length;
        Object answer;

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Set<Future<Integer>> set = new HashSet<Future<Integer>>();
        for (int i = 0; i < 4; i++) {
            MaxValue maxValue = new MaxValue(array, (i * arrayLength) / 4, ((i + 1) * arrayLength / 4));
            Future<Integer> future = threadPoolExecutor.submit(maxValue);
            set.add(future);
        }

        threadPoolExecutor.shutdown();

        List results = new ArrayList<Integer>();
        for (Future<Integer> future : set) {
            try {
                results.add(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Array of max elements from threads = " + results);

        answer = Collections.max(results);

        return answer;
    }

    public static void main(String[] args) throws InterruptedException {
        int[] array = new int[40];

        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 50 + 1);
        }

        System.out.println("Array = " + Arrays.toString(array));

        Object max = findMaxValue(array);
        System.out.println("Max value in array: " + max);
    }

}