package ru.ifmo.ivshin.bank;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class LocalPerson implements Person, Serializable {
    private String name, surname;
    private String passportId;
    private Map<String, LocalAccount> accounts;

    LocalPerson(String name, String surname, String passportId, Map<String, LocalAccount> accounts) {
        super();
        this.name = name;
        this.surname = surname;
        this.passportId = passportId;
        this.accounts = accounts;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPassportId() {
        return passportId;
    }

    Set<String> getAccounts() {
        return accounts.keySet();
    }

    Account getAccount(String accountId) {
        return accounts.get(accountId);
    }
}
