import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        System.out.println("Server waiting on port 9999...");

        Socket client = server.accept();
        System.out.println("Client connected!");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String input;
        while ((input = in.readLine()) != null) {
            System.out.println("Client: " + input);
            if (input.equalsIgnoreCase("bye"))
                break;

            System.out.print("Server reply: ");
            String reply = console.readLine();
            out.println(reply);
        }

        client.close();
        server.close();
    }
}