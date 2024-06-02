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

    private static class ClientHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private Player player;
        private GameSession gameSession;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                this.out = new ObjectOutputStream(this.socket.getOutputStream());
                this.in = new ObjectInputStream(this.socket.getInputStream());
                String playerName = this.in.readUTF();
                int gameId = this.in.readInt();
                if (gameId == -1) {
                    gameId = MonsterMayhemServer.nextGameId++;
                    this.gameSession = new GameSession(gameId);
                    MonsterMayhemServer.games.put(gameId, this.gameSession);
                } else {
                    this.gameSession = (GameSession)MonsterMayhemServer.games.get(gameId);
                }

                this.player = new Player(playerName, this.gameSession.players.size());
                this.gameSession.addPlayer(this.player);
                this.out.writeUTF("Joined Game: " + gameId);
                this.out.flush();

                while(true) {
                    String command = this.in.readUTF();
                    if (command.equals("QUIT")) {
                        break;
                    }

                    String response = this.gameSession.processCommand(this.player, command);
                    this.out.writeUTF(response);
                    this.out.flush();
                }
            } catch (IOException var13) {
                IOException e = var13;
                e.printStackTrace();
            } finally {
                try {
                    this.socket.close();
                } catch (IOException var12) {
                    IOException e = var12;
                    e.printStackTrace();
                }

            }

        }
    }
}
