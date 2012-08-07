package com.webatm.dao.jdbc;

import com.webatm.domain.Account;
import com.webatm.domain.Transaction;
import com.webatm.dao.TransactionDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/5/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionJdbcDAO extends GenericJdbcDAO implements TransactionDAO {

    @Override
    public List<Transaction> getAccountTransactions(Account account) throws SQLException {
        ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT ID, DATE, AMOUNT FROM TRANSACTION WHERE ACCOUNT_ID = ?");
            statement.setInt(1, account.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("ID"));
                transaction.setDate(resultSet.getDate("DATE"));
                transaction.setAccount(account);
                transaction.setAmount(resultSet.getDouble("AMOUNT"));
                transactionsList.add(transaction);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return transactionsList;
    }

    @Override
    public Transaction insert(Transaction transaction) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO TRANSACTION (DATE, AMOUNT, ACCOUNT_ID) VALUES (?, ?, ?)");
            statement.setTimestamp(1, new Timestamp(transaction.getDate().getTime()));
            statement.setDouble(2, transaction.getAmount());
            statement.setInt(3, transaction.getAccount().getId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            // get generated id
            resultSet.next();
            transaction.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return transaction;
    }
}
