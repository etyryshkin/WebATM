package com.webatm.security;

import com.webatm.domain.Account;
import com.webatm.domain.Transaction;
import com.webatm.dao.TransactionDAO;
import com.webatm.dao.jdbc.TransactionJdbcDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/13/12
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionService {

    private volatile static TransactionService instance;
    private TransactionDAO transactionDAO;

    public static TransactionService getInstance() {
        if (instance == null) {
            synchronized (TransactionService.class) {
                if (instance == null) {
                    instance = new TransactionService();
                }
            }
        }
        return instance;
    }

    private TransactionDAO getTransactionDAO() {
        if (transactionDAO == null) {
            transactionDAO = new TransactionJdbcDAO();
        }
        return transactionDAO;
    }

    public List<Transaction> getAccountTransactions(Account account) throws SQLException {
        return getTransactionDAO().getAccountTransactions(account);
    }

    public void insert(Transaction transaction) throws SQLException {
        getTransactionDAO().insert(transaction);
    }
}
