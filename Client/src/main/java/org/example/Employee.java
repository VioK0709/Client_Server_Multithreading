package org.example;

public class Employee {
    public int port;
    public String host;

    public Employee() {
        // Пустой конструктор
    }

    public Employee(int port, String host) {
        this.port = port;
        this.host = host;

    }

    @Override
    public String toString() {
        return "{" +
                "port: " + port +
                ", host: " + host + "}";
    }
}