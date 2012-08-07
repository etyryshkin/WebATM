package com.webatm.dao.jdbc;

import com.webatm.domain.Account;
import com.webatm.domain.Currency;
import com.webatm.domain.User;
import com.webatm.dao.AccountDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/5/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountJdbcDAO extends GenericJdbcDAO implements AccountDAO {
    @Override
    public List<Account> getAccounts(User user) throws SQLException {
        ArrayList<Account> accountsList = new ArrayList<Account>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT ID, CURRENCY, AMOUNT FROM ACCOUNT WHERE USER_ID = ?");
            statement.setInt(1, user.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("ID"));
                account.setOwner(user);
                account.setCurrency(Currency.values()[resultSet.getInt("CURRENCY")]);
                account.setAmount(resultSet.getDouble("AMOUNT"));
                accountsList.add(account);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return accountsList;
    }

    @Override
    public List<Account> getAccountsForTransfer(User user, Account accountFrom) throws SQLException {
        ArrayList<Account> accountsList = new ArrayList<Account>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT ID, AMOUNT FROM ACCOUNT WHERE USER_ID = ? AND CURRENCY = ? AND ID <> ?");
            statement.setInt(1, user.getId());
            statement.setInt(2, accountFrom.getCurrency().ordinal());
            statement.setInt(3, accountFrom.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("ID"));
                account.setOwner(user);
                account.setCurrency(accountFrom.getCurrency());
                account.setAmount(resultSet.getDouble("AMOUNT"));
                accountsList.add(account);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return accountsList;
    }

    @Override
    public Account insert(Account account) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO ACCOUNT (USER_ID, CURRENCY, AMOUNT) VALUES (?, ?, ?)");
            statement.setInt(1, account.getOwner().getId());
            statement.setInt(2, account.getCurrency().ordinal());
            statement.setDouble(3, account.getAmount());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            // get generated id
            resultSet.next();
            account.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return account;
    }

    @Override
    public void delete(Account account) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM ACCOUNT WHERE ID = ?");
            statement.setInt(1, account.getId());
            statement.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, null);
        }
    }

    @Override
    public Account getAccountById(int id) throws SQLException {
        Account account = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT USER_ID, CURRENCY, AMOUNT FROM ACCOUNT WHERE ID = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setId(id);
                account.setOwner(new User(resultSet.getInt("USER_ID")));
                account.setCurrency(Currency.values()[resultSet.getInt("CURRENCY")]);
                account.setAmount(resultSet.getDouble("AMOUNT"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return account;
    }

    @Override
    public Account update(Account account) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE ACCOUNT SET USER_ID = ?, CURRENCY = ?, AMOUNT = ? WHERE ID = ?");
            statement.setInt(1, account.getOwner().getId());
            statement.setInt(2, account.getCurrency().ordinal());
            statement.setDouble(3, account.getAmount());
            statement.setInt(4, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, null);
        }
        return account;
    }
}
