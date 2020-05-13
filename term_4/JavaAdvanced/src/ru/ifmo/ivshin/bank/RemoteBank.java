package ru.ifmo.ivshin.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class RemoteBank extends UnicastRemoteObject implements Bank {
    private final int port;
    private final ConcurrentMap<String, Person> persons = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Set<String>> accountByPassport = new ConcurrentHashMap<>();

    public RemoteBank(int port) throws RemoteException {
        super(port);
        this.port = port;
    }

    @Override
    public Person getLocalPerson(String passport) throws RemoteException {
        Person person;
        if (passport == null || (person = persons.get(passport)) == null ) {
            return null;
        }

        Set<String> accountNames = getPersonAccounts(person);
        Map<String, LocalAccount> accounts = new ConcurrentHashMap<>();
        for (String name: accountNames) {
            try {
                Account curRemote = getAccount(person, name);
                accounts.put(name, new LocalAccount(curRemote.getId(), curRemote.getAmount()));
            } catch (RemoteException e) {
                System.err.println("ERR: creating local accounts failed: " + e.getMessage());
            }
        }
        return new LocalPerson(person.getName(), person.getSurname(), person.getPassportId(), accounts);
    }

    @Override
    public Person getRemotePerson(String passport) {
        if (passport == null) {
            return null;
        }
        return persons.get(passport);
    }

    @Override
    public Set<String> getPersonAccounts(Person person) throws RemoteException {
        if (person == null) {
            return null;
        }
        System.out.println("Retrieving person '" + person.getPassportId() + "' accounts");
        if (person instanceof LocalPerson) {
            return ((LocalPerson) person).getAccounts();
        }
        return accountByPassport.get(person.getPassportId());
    }

    @Override
    public boolean createPerson(String name, String surname, String passport) throws RemoteException {
        if (name == null || surname == null || passport == null || persons.get(passport) != null) {
            return false;
        }
        System.out.println("Creating person " + name + " " + surname + " with pasport " + passport);
        persons.put(passport, new RemotePerson(name, surname, passport, port));
        accountByPassport.put(passport, new ConcurrentSkipListSet<>());
        return true;
    }

    @Override
    public boolean checkPerson(String name, String surname, String passport) throws RemoteException {
        if (name == null || surname == null || passport == null) {
            return false;
        }
        System.out.println("Checking " + name + " " + surname);
        Person person = persons.get(passport);
        return person != null && person.getName().equals(name) && person.getSurname().equals(surname);
    }

    @Override
    public boolean createAccount(Person person, String id) throws RemoteException {
        if (person == null || id == null) {
            return false;
        }

        String accountId = person.getPassportId() + ":" + id;
        if (accounts.containsKey(accountId)) {
            return false;
        }

        System.out.println("Creating account " + id + " by passport " + person.getPassportId());
        final Account account = new RemoteAccount(id, port);

        accounts.put(accountId, account);
        if (accountByPassport.get(person.getPassportId()) == null) {
            accountByPassport.put(person.getPassportId(), new ConcurrentSkipListSet<>());
        }
        accountByPassport.get(person.getPassportId()).add(id);

        return true;
    }


    public Account getAccount(Person person, String id) throws RemoteException {
        if (person == null || id == null) {
            return null;
        }

        String accountId = person.getPassportId() + ":" + id;
        Account account = accounts.get(accountId);

        if (account == null) {
            return null;
        }

        System.out.println("Retrieving account " + accountId + " with value " + account.getAmount());

        if (person instanceof LocalPerson) {
            return ((LocalPerson) person).getAccount(id);
        }
        return account;
    }
}
