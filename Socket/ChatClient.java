import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9999);
        System.out.println("Connected to server!");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        new Thread(() -> {
            try {
                String serverMsg;
                while ((serverMsg = in.readLine()) != null) {
                    System.out.println("Server: " + serverMsg);
                }
            } catch (IOException e) {
            }
        }).start();

        String userInput;
        while ((userInput = console.readLine()) != null) {
            out.println(userInput);
            if (userInput.equalsIgnoreCase("bye"))
                break;
        }

        socket.close();
    }
}
