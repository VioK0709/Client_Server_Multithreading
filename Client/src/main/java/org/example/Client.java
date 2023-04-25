package org.example;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client implements Runnable {
    static Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private static String nickname;
    private static String nickname2;
    private final Integer port;
    private final String host;

    public Client(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("localhost", 8080);
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            pressNickname();
            pressNicknameClientTwo();
            readMsg.start();
            writeMsg.start();
            writeMsgClientTwo.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Thread readMsg = new Thread(() -> {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("stop")) {
                    this.downService();
                    break;
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            this.downService();
        }
    });

    Thread writeMsg = new Thread(() -> {
        while (true) {
            String userWord;
            try {
                Date time = new Date();
                SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss");
                String dTime = dt1.format(time);
                userWord = inputUser.readLine();
                if (userWord.equalsIgnoreCase("stop")) {
                    out.write("stop");
                    Client.this.downService();
                } else {
                    out.write(nickname + " в " + dTime + " -> " + userWord + "\n");
                    Logger.log(nickname + ": " + userWord);
                }
                out.flush();
            } catch (IOException e) {
                this.downService();
                break;
            }
        }
    });
    Thread writeMsgClientTwo = new Thread(() -> {
        while (true) {
            String userWord;
            try {
                Date time = new Date();
                SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss");
                String dTime = dt1.format(time);
                userWord = inputUser.readLine();
                if (userWord.equalsIgnoreCase("stop")) {
                    out.write("stop");
                    Client.this.downService();
                } else {
                    out.write(nickname2 + " в " + dTime + " -> " + userWord + "\n");
                    Logger.log(nickname2 + ": " + userWord);
                }
                out.flush();
            } catch (IOException e) {
                this.downService();
                break;
            }
        }
    });


    private void pressNickname() {
        try {
            System.out.print("Введите свое имя: ");
            nickname = inputUser.readLine();
            Logger.log("Клиент " + nickname + " подключился к сокету");
            System.out.println("Привет, " + nickname + "!");
            out.flush();
        } catch (IOException ignored) {
        }
    }

    private void pressNicknameClientTwo() {
        try {
            System.out.print("Введите свое имя: ");
            nickname2 = inputUser.readLine();
            Logger.log("Клиент " + nickname2 + " подключился к сокету");
            System.out.println("Привет, " + nickname2 + "!");
            System.out.println("Участники чата могут вводить сообщения, или 'stop' для выхода из чата");
            out.flush();
        } catch (IOException ignored) {
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }
}