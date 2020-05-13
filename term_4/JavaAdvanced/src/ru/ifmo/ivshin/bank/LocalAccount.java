package ru.ifmo.ivshin.bank;

import java.io.Serializable;
import java.rmi.RemoteException;

public class LocalAccount implements Account, Serializable {
    private final String id;
    private int amount;

    LocalAccount(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public synchronized int getAmount() {
        System.out.println("Getting amount of money for account " + id);
        return amount;
    }

    public synchronized void setAmount(final int amount) {
        System.out.println("Setting amount of money for account " + id);
        this.amount = amount;
    }
}
