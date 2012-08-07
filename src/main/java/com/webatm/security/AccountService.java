package com.webatm.security;

import com.webatm.domain.Account;
import com.webatm.domain.Transaction;
import com.webatm.domain.User;
import com.webatm.dao.AccountDAO;
import com.webatm.dao.jdbc.AccountJdbcDAO;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/4/12
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountService {

    private volatile static AccountService instance;
    private AccountDAO accountDAO;

    public static AccountService getInstance() {
        if (instance == null) {
            synchronized (AccountService.class) {
                if (instance == null) {
                    instance = new AccountService();
                }
            }
        }
        return instance;
    }

    private AccountDAO getAccountDAO() {
        if (accountDAO == null) {
            accountDAO = new AccountJdbcDAO();
        }
        return accountDAO;
    }

    public List<Account> getAccounts(User user) throws SQLException {
        return getAccountDAO().getAccounts(user);
    }

    public List<Account> getAccountsForTransfer(User user, Account accountFrom) throws SQLException {
        return getAccountDAO().getAccountsForTransfer(user, accountFrom);
    }

    public void createAccount(Account account) throws SQLException {
        getAccountDAO().insert(account);
    }

    public void deleteAccount(Account account) throws SQLException {
        getAccountDAO().delete(account);
    }

    public Account getAccountById(int id) throws SQLException {
        Account account = getAccountDAO().getAccountById(id);
        User owner = UserService.getInstance().getUserById(account.getOwner().getId());
        account.setOwner(owner);
        return account;
    }

    public Account addMoneyToAccount(Account account, double amount) throws SQLException {
        if (amount > 0) {
            account.setAmount(account.getAmount() + amount);
            account = getAccountDAO().update(account);
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setAmount(amount);
            transaction.setDate(new GregorianCalendar().getTime());
            TransactionService.getInstance().insert(transaction);
        }
        return account;
    }

    public Account takeMoneyFromAccount(Account account, double amount) throws SQLException {
        if (amount > 0 && amount <= account.getAmount()) {
            account.setAmount(account.getAmount() - amount);
            account = getAccountDAO().update(account);
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setAmount(-amount);
            transaction.setDate(new GregorianCalendar().getTime());
            TransactionService.getInstance().insert(transaction);
        }
        return account;
    }

    public void transferMoney(Account origin, Account destination, double amount) throws SQLException {
        if (amount > 0 && amount <= origin.getAmount()) {
            origin.setAmount(origin.getAmount() - amount);
            destination.setAmount(destination.getAmount() + amount);
            origin = getAccountDAO().update(origin);
            Transaction transaction = new Transaction();
            transaction.setAccount(origin);
            transaction.setAmount(-amount);
            transaction.setDate(new GregorianCalendar().getTime());
            TransactionService.getInstance().insert(transaction);
            destination = getAccountDAO().update(destination);
            transaction = new Transaction();
            transaction.setAccount(destination);
            transaction.setAmount(amount);
            transaction.setDate(new GregorianCalendar().getTime());
            TransactionService.getInstance().insert(transaction);
        }
    }
}
