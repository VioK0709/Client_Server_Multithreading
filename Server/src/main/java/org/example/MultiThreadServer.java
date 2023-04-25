package org.example;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class MultiThreadServer implements Runnable {
    private static Socket socket;

    public MultiThreadServer(Socket socket) {
        MultiThreadServer.socket = socket;
    }

    static LinkedList<String> story = new LinkedList<>();

    @Override
    public void run() {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Logger.log("Канал чтения и канал записи созданы");
            Logger.log("Клиентские запись и чтение инициализированы");
            while (!socket.isClosed()) {
                String entry = in.readLine();
                Logger.log("Запись сделана клиентом " + entry);
                addStoryEl(entry);
                if (entry == null) {
                    Logger.log("Клиент инициализирует выход...");
                    break;
                }
                Logger.log("Сервер пытается выполнить запись в канал...");
                out.write(entry);
                Logger.log("Сервер записал сообщение");
                Logger.log("Сервер переходит к записи данных в файл...");
                saveToFile(entry);
                out.flush();
            }
            Logger.log("Клиент отключен");
            Logger.log("Производится закрытие соединений и каналов");
            socket.close();
            in.close();
            out.close();
            Logger.log("Закрытие соединений и каналов - ГОТОВО.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addStoryEl(String el) {
        if (story.size() >= 1000) {
            story.removeFirst();
            story.add(el);
        } else {
            story.add(el);
        }
    }

    public static void saveToFile(String msg) {
        File file = new File("foo.out");
        if (!(file.exists() && file.canRead() && file.canWrite())) {
            try {
                new File(file.getParent());
                if (file.createNewFile()) {
                    System.out.println("File created");
                } else {
                    return;
                }
            } catch (IOException e) {
                System.out.println("saveToFile: " + e.getMessage());
                return;
            }
        }
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("History messages:" + "\n");

            Logger.log("Данные успешно записаны в файл.");
            for (String vr : story) {
                fw.write(vr + "\n");
            }
            fw.write("/...." + "\n");
            fw.flush();
        } catch (IOException e) {
            System.out.println("saveToFile: " + e.getMessage());
        }
    }

    public static void waitMessAndSend(Socket clientSocket) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        try (Scanner inMess = new Scanner(clientSocket.getInputStream())) {
            while (true) {
                if (inMess.hasNext()) {
                    String mess = inMess.nextLine();
                    Logger.log("Запись сделана клиентом " + mess);
                    MultiThreadServer.addStoryEl(mess);
                    Logger.log("Сервер пытается выполнить запись в канал...");
                    out.write(mess);
                    Logger.log("Сервер записал сообщение");
                    Logger.log("Сервер переходит к записи данных в файл...");
                    saveToFile(mess);
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}