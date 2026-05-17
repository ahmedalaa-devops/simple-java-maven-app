package com.mycompany.app;

public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("App started successfully!");
        while (true) {
            System.out.println("Hello World! App is running...");
            Thread.sleep(5000);
        }
    }
}
