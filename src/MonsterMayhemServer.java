import java.io.*;
import java.net.*;
import java.util.*;

public class MonsterMayhemServer {
    private static int nextGameId = 1;
    private static Map<Integer, GameSession> games = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started. Waiting for clients to connect...");
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        }catch (IOException e) {
            e.printStackTrace();
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
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                String playerName = in.readUTF();
                int gameId = in.readInt();

                if (gameId == -1) {
                    gameId = nextGameId++;
                    gameSession = new GameSession(gameId);
                    games.put(gameId, gameSession);
                } else {
                    gameSession = games.get(gameId);
                }

                player = new Player(playerName, gameSession.players.size());
                gameSession.addPlayer(player);

                out.writeUTF("Joined Game: " + gameId);
                out.flush();

                while (true) {
                    String command = in.readUTF();
                    if (command.equals("QUIT")) {
                        break;
                    }

                    String response = gameSession.processCommand(player, command);
                    out.writeUTF(response);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
