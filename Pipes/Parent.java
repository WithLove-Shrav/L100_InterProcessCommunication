
import java.io.*;

public class Parent {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Launch child process
        ProcessBuilder pb = new ProcessBuilder("java", "Child.java");
        Process child = pb.start();

        // Get output stream to write to child's stdin (our write end of pipe)
        PrintWriter toChild = new PrintWriter(child.getOutputStream(), true);

        // Get input stream to read from child's stdout (our read end of pipe)
        BufferedReader fromChild = new BufferedReader(
                new InputStreamReader(child.getInputStream()));

        // Send some orders
        String[] orders = { "Make pasta", "Prepare salad", "Serve dessert", "quit" };

        for (String order : orders) {
            System.out.println("Parent sending: " + order);
            toChild.println(order);

            if (!order.equals("quit")) {
                String reply = fromChild.readLine();
                System.out.println("Parent heard: " + reply);
            }
        }

        child.waitFor(); // Wait for child to finish
        System.out.println("Communication ended.");
    }
}