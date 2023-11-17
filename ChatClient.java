import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ClientInterface {
    protected ChatClient() throws RemoteException {
        super();
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println("Received: " + message);
    }

    public static void main(String[] args) {
        try {
            ChatInterface server = (ChatInterface) Naming.lookup("//localhost/ChatServer");
            ChatClient client = new ChatClient();
            server.registerClient(client);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                server.sendMessage(message);
            }
        } catch (Exception e) {
            System.err.println("ChatClient exception: " + e.toString());
            e.printStackTrace();
        }
    }
}