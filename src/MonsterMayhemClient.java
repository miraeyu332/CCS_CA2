import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MonsterMayhemClient {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // Update this to the server's IP address if needed
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            out.writeUTF(name);
            out.flush();

            System.out.print("Enter Game ID (-1 to create new game): ");
            int gameId = scanner.nextInt();
            out.writeInt(gameId);
            out.flush();

            String response = in.readUTF();
            System.out.println(response);

            // Game loop
            scanner.nextLine(); // consume newline
            while (true) {
                System.out.println("Enter command: ");
                String command = scanner.nextLine();
                out.writeUTF(command);
                out.flush();

                if (command.equals("QUIT")) {
                    break;
                }

                // Handle server response
                try {
                    String gameState = in.readUTF();
                    System.out.println(gameState);
                } catch (EOFException e) {
                    System.out.println("Server disconnected.");
                    break;
                }
            }
        }
    }
}
