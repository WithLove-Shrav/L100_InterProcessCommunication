import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloClient {
    public static void main(String[] args) {
        try {
            // Connect to registry on localhost:1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup the remote object by name
            HelloService service = (HelloService) registry.lookup("HelloService");

            // Call the remote method!
            String response = service.sayHello("Shravya from Bengaluru");

            System.out.println("Response from server: " + response);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}