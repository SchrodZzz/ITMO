package ru.ifmo.ivshin.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface Bank extends Remote {
    /* Create person (return value indicates creation success) */
    boolean createPerson(String name, String surname, String passport) throws RemoteException;

    /* Check person existence by passport */
    boolean checkPerson(String name, String surname, String passport) throws RemoteException;

    /* Get serializable person */
    Person getLocalPerson(String passport) throws RemoteException;

    /* Get person */
    Person getRemotePerson(String passport) throws RemoteException;

    /* Get person' accounts by name */
    Set<String> getPersonAccounts(Person person) throws RemoteException;

    /* Create account by passport:id (return value indicates creation success) */
    boolean createAccount(Person person, String id) throws RemoteException;

    /* Get account by passport:id */
    Account getAccount(Person person, String id) throws RemoteException;
}