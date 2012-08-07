package com.webatm.dao;

import com.webatm.domain.Account;
import com.webatm.domain.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/5/12
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TransactionDAO {
    List<Transaction> getAccountTransactions(Account account) throws SQLException;
    Transaction insert(Transaction transaction) throws SQLException;
}
