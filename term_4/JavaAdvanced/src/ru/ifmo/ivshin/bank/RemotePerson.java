package ru.ifmo.ivshin.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemotePerson extends UnicastRemoteObject implements Person {
    private String name, surname;
    private String passportId;

    public RemotePerson(String name, String surname, String passportId, int port) throws RemoteException {
        super(port);
        this.name = name;
        this.surname = surname;
        this.passportId = passportId;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public String getSurname() throws RemoteException {
        return surname;
    }

    @Override
    public String getPassportId() throws RemoteException {
        return passportId;
    }
}
