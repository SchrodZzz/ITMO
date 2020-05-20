package ru.ifmo.ivshin.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Person extends Remote {
    String getName() throws RemoteException;
    String getSurname() throws RemoteException;
    String getPassportId() throws RemoteException;
}
