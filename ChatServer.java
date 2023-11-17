import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatInterface {
    private List<ClientInterface> clients;

    public ChatServer() throws RemoteException {
        clients = new ArrayList<>();
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println("Received message: " + message);
        for (ClientInterface client : clients) {
            client.receiveMessage(message);
        }
    }

    @Override
    public void registerClient(ClientInterface client) throws RemoteException {
        clients.add(client);
        System.out.println("Client registered.");
    }

    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer();
            java.rmi.registry.LocateRegistry.createRegistry(1099); // Default RMI registry port
            java.rmi.Naming.rebind("//localhost/ChatServer", server);
            System.out.println("ChatServer running...");
        } catch (Exception e) {
            System.err.println("ChatServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}