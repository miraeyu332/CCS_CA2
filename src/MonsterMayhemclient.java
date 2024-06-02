import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MonsterMayhemclient {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Enter your name: ");
            String name = sc.nextLine();
            out.writeUTF(name);
            out.flush();

            System.out.println("Enter Game ID (-1 to create new game): ");
            int gameID = sc.nextInt();
            out.writeInt(gameID);
            out.flush();

            String response = in.readUTF();
            System.out.println(response);

            //Game loop
            scanner.nextLine(); // consume newLine
            while (true) {
                System.out.println("Enter command");
                String command = sc.nextLine();
                out.writeUTF(command);
                out.flush();

                if (command.equals("QUIT")) {

                    break;
                }

                //Handle server response
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














