package ru.ifmo.ivshin.bank;

import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.*;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class BankTest {

    private static Bank bank;

    private static final int TESTS = 99;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Naming.rebind("//localhost/bank", new RemoteBank(8888));
        bank = (Bank) Naming.lookup("//localhost/bank");

        System.out.println("Bank creation complete");
    }

    @Test
    public void getPerson() throws RemoteException {
        assertNull(bank.getLocalPerson(String.valueOf(-TESTS - 1)));
        assertNull(bank.getRemotePerson(String.valueOf(-TESTS - 1)));
        String bestName = "Nikolay";
        for (int i = 0; i < TESTS; ++i) {
            bank.createPerson(bestName + i, "", bestName + i);
            Person remotePerson = bank.getRemotePerson(bestName + i);
            assertEquals(bestName + i, remotePerson.getName());
            assertEquals("", remotePerson.getSurname());
            assertEquals(bestName + i, remotePerson.getPassportId());

            Person localPerson = bank.getLocalPerson(bestName + i);
            assertEquals(bestName + i, localPerson.getName());
            assertEquals("", localPerson.getSurname());
            assertEquals(bestName + i, localPerson.getPassportId());

        }
    }

    @Test
    public void getAccountIds() throws RemoteException {
        String bestName = "Nikolya";
        for (int i = 0; i < TESTS; ++i) {
            assertTrue(bank.createPerson(bestName, "" + i, bestName + i));

            int cnt = 0;
            int opTodo = (int) (Math.random() * 1000 % TESTS);
            Person remote = bank.getRemotePerson(bestName + i);
            for (int j = 0; j < opTodo; j++) {
                if (bank.createAccount(remote, "" + (int) (Math.random() * 1e9)))
                    cnt++;
            }

            Set<String> ids = bank.getPersonAccounts(remote);
            assertNotNull(ids);
            assertEquals(cnt, ids.size());
        }
    }

    @Test
    public void checkCheckAndCreatePerson() throws RemoteException {
        String bestName = "Nikolas";
        for (int i = 0; i < TESTS; ++i) {
            assertFalse(bank.checkPerson(bestName + i, "", bestName + i));
            assertTrue(bank.createPerson(bestName + i, "", bestName + i));
            assertTrue(bank.checkPerson(bestName + i, "", bestName + i));
        }
    }

    @Test
    public void checkCreatingAccount() throws RemoteException {
        String bestName = "Banan";
        String bestSurname = "PoDM";
        String bestId = "99";
        String bestPassport = bestName + bestSurname;
        bank.createPerson(bestName, bestSurname, bestPassport);
        Person remote = bank.getRemotePerson(bestPassport);
        Person local = bank.getLocalPerson(bestPassport);

        bank.createAccount(remote, bestId);
        assertNull(bank.getAccount(local, bestId));
        assertEquals(1, bank.getPersonAccounts(remote).size());
        assertNotNull(bank.getAccount(remote, bestId));
        assertNotEquals(bank.getPersonAccounts(local), bank.getPersonAccounts(remote));
    }

    @Test
    public void checkRemoteAfterLocal() throws RemoteException {
        String bestName = "Kolyan";
        String bestSurname = "V";
        String bestId = "7071";
        bank.createPerson(bestName, bestSurname, bestName + bestSurname);
        Person remote = bank.getRemotePerson(bestName + bestSurname);
        assertNotNull(remote);
        assertTrue(bank.createAccount(remote,  bestId));
        Person local = bank.getLocalPerson(bestName + bestSurname);
        assertNotNull(local);

        Account localAccount = bank.getAccount(local, bestId);
        localAccount.setAmount(localAccount.getAmount() + 1337);
        Account remoteAccount = bank.getAccount(remote, bestId);
        assertEquals(1337, localAccount.getAmount());
        assertEquals(0, remoteAccount.getAmount());
    }

    @Test
    public void checkLocalAfterRemote() throws RemoteException {
        String bestName = "Vedernikov";
        String bestSurname = "NV";
        String bestId = "1482";
        String bestPassport = bestName + bestSurname;
        bank.createPerson(bestName, bestSurname, bestPassport);
        Person remote = bank.getRemotePerson(bestPassport);

        assertNotNull(remote);
        assertTrue(bank.createAccount(remote,  bestId));
        Account remoteAccount = bank.getAccount(remote, bestId);

        Person local2 = bank.getLocalPerson(bestPassport);
        remoteAccount.setAmount(remoteAccount.getAmount() + 123);
        Person local = bank.getLocalPerson(bestPassport);
        assertNotNull(local);

        Account localAccount = bank.getAccount(local, bestId);
        Account localAccount2 = bank.getAccount(local2, bestId);
        assertEquals(localAccount.getAmount(), remoteAccount.getAmount());
        assertEquals(localAccount2.getAmount() + 123, localAccount.getAmount());
    }

    @Test
    public void checkRemoteRemote() throws RemoteException {
        String bestName = "Barrel";
        String bestSurname = "WithBear";
        String bestId1 = "42";
        String bestId2 = "24";
        String bestPassport = bestName + bestSurname;
        bank.createPerson(bestName, bestSurname, bestPassport);

        Person remote1 = bank.getRemotePerson(bestPassport);
        Person remote2 = bank.getRemotePerson(bestPassport);
        bank.createAccount(remote1, bestId1);
        bank.createAccount(remote2, bestId2);

        assertEquals(2, bank.getPersonAccounts(remote1).size());
        assertEquals(bank.getPersonAccounts(remote1).size(), bank.getPersonAccounts(remote2).size());
    }

    @Test
    public void checkLocalLocal() throws RemoteException {
        String bestName = "Roach";
        String bestSurname = "Nibber";
        String bestId1 = "65";
        String bestId2 = "321";
        String bestPassport = bestName + bestSurname;
        bank.createPerson(bestName, bestSurname, bestPassport);

        Person local1 = bank.getLocalPerson(bestPassport);
        Person local2 = bank.getLocalPerson(bestPassport);
        bank.createAccount(local1, bestId1);
        bank.createAccount(local2, bestId2);

        Person local3 = bank.getLocalPerson(bestPassport);
        assertEquals(2, bank.getPersonAccounts(local3).size());
        assertEquals(0, bank.getPersonAccounts(local1).size());
        assertEquals(bank.getPersonAccounts(local1).size(), bank.getPersonAccounts(local2).size());
    }
}