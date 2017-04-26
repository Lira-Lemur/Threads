/**
 * Write a class called ReverseHello that creates a thread (let's call it Thread 1).
 * Thread 1 creates another thread (Thread 2); Thread 2 creates Thread 3; and so on, up to Thread 50.
 * Each thread should print "Hello from Thread <num>!",
 * but you should structure your program such that the threads print their greetings in reverse order.
 */

public class ReverseHello extends Thread {

    private static int THREAD_NUMBER = 50;
    private int countThreads;

    private ReverseHello(int counter) {
        this.countThreads = counter;
    }

    public void run() {
        if (countThreads < THREAD_NUMBER) {
            createThread(countThreads + 1);
        }
        System.out.println("Hello from " + getName());
    }

    private void createThread(int counter) {
        ReverseHello reverseHello = new ReverseHello(counter);
        reverseHello.start();

        try {
            reverseHello.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        ReverseHello reverseHello = new ReverseHello(1);
        reverseHello.start();
    }

}
