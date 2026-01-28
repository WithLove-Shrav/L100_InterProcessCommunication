import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    // Required constructor
    public HelloServiceImpl() throws RemoteException {
        super(); // exports the object
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        System.out.println("Server received request from: " + name);
        return "Hello, " + name + "! Greetings from the remote server at "
                + java.time.LocalDateTime.now();
    }
}