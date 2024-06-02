import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MonsterMayhemServer {
    private static int nextGameId = 1;
    private static Map<Integer, GameSession> games = new HashMap();

    public MonsterMayhemServer() {
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);

        try {
            System.out.println("Server started. Waiting for clients to connect...");

            while(true) {
                (new ClientHandler(serverSocket.accept())).start();
            }
        } catch (Throwable var5) {
            try {
                serverSocket.close();
            } catch (Throwable var4) {
                var5.addSuppressed(var4);
            }

            throw var5;
        }
    }


}
