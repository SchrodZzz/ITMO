package ru.ifmo.rain.ivshin.bank;

import java.rmi.*;
import java.net.*;

public class Server {
    private final static int PORT = 8080;
    public static void main(final String... args) {
        try {
            Naming.rebind("//localhost/bank", new RemoteBank(PORT));
        } catch (final RemoteException e) {
            System.out.println("Cannot export object: " + e.getMessage());
            e.printStackTrace();
        } catch (final MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        System.out.println("Server started");
    }
}
