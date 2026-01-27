
import java.io.*;

public class Child {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out, true);

        try {
            String line;
            System.out.println("Child ready to receive orders...");
            while ((line = reader.readLine()) != null) {
                System.out.println("Child received: " + line);
                writer.println("Child confirms: " + line + " prepared!");
                if (line.equalsIgnoreCase("quit"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}