import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducerConsumerWithSemaphores {

    // Shared bounded buffer (using Queue for FIFO behavior)
    private static final int BUFFER_SIZE = 5;
    private static Queue<Integer> buffer = new LinkedList<>();

    // Semaphores
    private static Semaphore empty = new Semaphore(BUFFER_SIZE);   // initially all slots empty
    private static Semaphore full  = new Semaphore(0);             // initially nothing to consume
    private static Semaphore mutex = new Semaphore(1);             // for mutual exclusion

    // Producer thread
    static class Producer implements Runnable {
        private final String name;

        Producer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 12; i++) {   // produce 12 items
                    Thread.sleep(400);            // simulate baking time

                    empty.acquire();              // wait for empty slot
                    mutex.acquire();              // lock the buffer

                    buffer.add(i);
                    System.out.printf("[%s] Produced → %d    (buffer size now: %d)\n",
                            name, i, buffer.size());

                    mutex.release();              // unlock
                    full.release();               // signal: one more item available

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Consumer thread
    static class Consumer implements Runnable {
        private final String name;

        Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 12; i++) {   // consume 12 items
                    Thread.sleep(600);            // simulate eating/serving time (slower)

                    full.acquire();               // wait until there's something to consume
                    mutex.acquire();              // lock

                    int item = buffer.poll();
                    System.out.printf("[%s] Consumed → %d    (buffer size now: %d)\n",
                            name, item, buffer.size());

                    mutex.release();              // unlock
                    empty.release();              // signal: one more empty slot

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread producer1 = new Thread(new Producer("Baker-A"), "Baker-A");
        Thread producer2 = new Thread(new Producer("Baker-B"), "Baker-B");
        Thread consumer1 = new Thread(new Consumer("Waiter-X"), "Waiter-X");
        Thread consumer2 = new Thread(new Consumer("Waiter-Y"), "Waiter-Y");

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        // Optional: wait for all to finish (in real program you might not join)
        try {
            producer1.join();
            producer2.join();
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nAll production and consumption completed.");
    }
}