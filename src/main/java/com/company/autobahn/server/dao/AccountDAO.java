package com.company.autobahn.server.dao;

import com.company.autobahn.server.model.Account;

/**
 * Created by Кочмарский on 01.10.2016.
 */
public interface AccountDAO {
    public Account getAccountOf(int driverId);
    public void addAccount(Account account);
    public void deleteAccount(Account account);
}
