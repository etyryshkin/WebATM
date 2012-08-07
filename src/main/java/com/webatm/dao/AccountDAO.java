package com.webatm.dao;

import com.webatm.domain.Account;
import com.webatm.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/5/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AccountDAO {
    List<Account> getAccounts(User user) throws SQLException;

    Account insert(Account account) throws SQLException;

    void delete(Account account) throws SQLException;

    Account getAccountById(int id) throws SQLException;

    List<Account> getAccountsForTransfer(User user, Account accountFrom) throws SQLException;

    Account update(Account account) throws SQLException;
}
