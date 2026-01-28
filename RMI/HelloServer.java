import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloServer {
    public static void main(String[] args) {
        try {
            // Create the remote object
            HelloService service = new HelloServiceImpl();

            // Create (or get) registry on port 1099 (default)
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind (publish) it with a name
            registry.rebind("HelloService", service);

            System.out.println("Server ready! Remote object bound as 'HelloService'");
            System.out.println("Waiting for clients... (press Ctrl+C to stop)");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}