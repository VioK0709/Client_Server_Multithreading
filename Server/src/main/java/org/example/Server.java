package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.example.MultiThreadServer.waitMessAndSend;

public class Server {
    static ExecutorService executeIt = Executors.newFixedThreadPool(2);
    private static final Map<Integer, User> users = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Logger.log("Создан серверный сокет, средство чтения командной консоли для сервера");
            while (!server.isClosed()) {
                if (br.ready()) {
                    Logger.log("Главный сервер обнаружил какие-либо сообщения в канале, давайте посмотрим на них");
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        Logger.log("Главный сервер инициирует выход...");
                        server.close();
                        Logger.log("Завершение программы");
                        break;
                    }
                }
                Socket client = server.accept();
                executeIt.execute(new MultiThreadServer(client));
                Logger.log("Соединение принято");
                new Thread(() -> {
                    try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                        User user = new User(client, out);
                        users.put(client.getPort(), user);
                        waitMessAndSend(client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
