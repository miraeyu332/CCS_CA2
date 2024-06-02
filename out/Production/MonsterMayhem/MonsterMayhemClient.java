//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MonsterMayhemClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public MonsterMayhemClient() {
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 12345);

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                try {
                    Scanner scanner = new Scanner(System.in);

                    try {
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
                        scanner.nextLine();

                        while(true) {
                            System.out.println("Enter command: ");
                            String command = scanner.nextLine();
                            out.writeUTF(command);
                            out.flush();
                            if (command.equals("QUIT")) {
                                break;
                            }

                            try {
                                String gameState = in.readUTF();
                                System.out.println(gameState);
                            } catch (EOFException var14) {
                                System.out.println("Server disconnected.");
                                break;
                            }
                        }
                    } catch (Throwable var15) {
                        try {
                            scanner.close();
                        } catch (Throwable var13) {
                            var15.addSuppressed(var13);
                        }

                        throw var15;
                    }

                    scanner.close();
                } catch (Throwable var16) {
                    try {
                        in.close();
                    } catch (Throwable var12) {
                        var16.addSuppressed(var12);
                    }

                    throw var16;
                }

                in.close();
            } catch (Throwable var17) {
                try {
                    out.close();
                } catch (Throwable var11) {
                    var17.addSuppressed(var11);
                }

                throw var17;
            }

            out.close();
        } catch (Throwable var18) {
            try {
                socket.close();
            } catch (Throwable var10) {
                var18.addSuppressed(var10);
            }

            throw var18;
        }

        socket.close();
    }
}
