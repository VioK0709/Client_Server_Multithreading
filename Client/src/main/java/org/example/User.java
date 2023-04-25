package org.example;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
    private Socket client;
    private final PrintWriter outMess;
    private String name;

    public User(Socket client, PrintWriter outMess) {
        this.client = client;
        this.outMess = outMess;
    }

    public void sendMsg(String msg) {
        outMess.println(msg);
        outMess.flush();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}