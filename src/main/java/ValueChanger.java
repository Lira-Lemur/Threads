
/**
 * 1. Create 3 threads. Threads must access and change one primitive variable, one Atomic variable, synchronized and unsynchronized method.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ValueChanger {

    private int primitive;
    private AtomicInteger atomic;
    private ExecutorService executor = Executors.newCachedThreadPool();

    private String name;

    private ValueChanger() {
    }

    private class PrimitiveRunner implements Runnable {
        public void run() {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignore) {
            }

            primitive++;
            primitive--;
        }
    }

    private class AtomicRunner implements Runnable {
        public void run() {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignore) {
            }

            atomic.addAndGet(1);
            atomic.addAndGet(-1);

        }
    }

    private void changePrimitive() throws InterruptedException {

        primitive = 0;

        for (int i = 0; i < 100; i++) {
            executor.execute(new PrimitiveRunner());
        }
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.SECONDS);

        System.out.println((char) 27 + "[32m Primitive Value = " + primitive + (char) 27 + "[0m");

    }

    private void changeAtomic() throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        atomic = new AtomicInteger(0);

        for (int i = 0; i < 10; i++) {
            executor.execute(new AtomicRunner());
        }
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.SECONDS);

        System.out.println((char) 27 + "[34m Atomic Value = " + atomic.get() + (char) 27 + "[0m");
    }

    //method not synchronized
    void printNumbers(String name) {
        for (int i = 1; i <= 5; i++) {
            System.out.println((char) 27 + "[35m " + name + ": Non Synchronized: " + 5 * i + (char) 27 + "[0m");
            try {
                Thread.sleep(400);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //method synchronized
    synchronized void printNumbersSync(String name) {
        for (int i = 1; i <= 5; i++) {
            System.out.println((char) 27 + "[31m " + name + ": Synchronized: " + 5 * i + (char) 27 + "[0m");
            try {
                Thread.sleep(400);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    public static void main(String[] arg) throws InterruptedException {
        ValueChanger valueChanger = new ValueChanger();
        valueChanger.changePrimitive();
        valueChanger.changeAtomic();

        new MethodsRunner(valueChanger, "Test1");
        new MethodsRunner(valueChanger, "Test2");
        new MethodsRunner(valueChanger, "Test3");

    }
}


