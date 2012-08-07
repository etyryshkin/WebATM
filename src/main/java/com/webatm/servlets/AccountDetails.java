package com.webatm.servlets;

import com.webatm.domain.Account;
import com.webatm.domain.Transaction;
import com.webatm.domain.User;
import com.webatm.security.AccountService;
import com.webatm.security.TransactionService;
import com.webatm.security.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/13/12
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccountDetails extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserService userService = UserService.getInstance();
        AccountService accountService = AccountService.getInstance();
        TransactionService transactionService = TransactionService.getInstance();

        try {
            //TODO Should be replaced by actual authenticated user
            User user = userService.getUserByName("User1");

            Account account = null;
            String accountId = request.getParameter("accountId");
            if (accountId != null && accountId != "") {
                account = accountService.getAccountById(Integer.parseInt(accountId));
            }

            List<Account> accountsForTransfer = new ArrayList<Account>();
            if (account != null) {
                accountsForTransfer = accountService.getAccountsForTransfer(user, account);
            }

            String actionContext = request.getParameter("actionContext");
            if ("add".equals(actionContext)) {
                String amountToAddString = request.getParameter("amountToAdd");
                double amountToAdd = Double.parseDouble(amountToAddString);
                accountService.addMoneyToAccount(account, amountToAdd);
            } else if ("take".equals(actionContext)) {
                String amountToTakeString = request.getParameter("amountToTake");
                double amountToTake = Double.parseDouble(amountToTakeString);
                accountService.takeMoneyFromAccount(account, amountToTake);
            } else if ("transfer".equals(actionContext)) {
                String accountSelectString = request.getParameter("accountSelect");
                if (!accountSelectString.isEmpty()) {
                    int destinationAccountId = Integer.parseInt(accountSelectString);
                    Account destinationAccount = accountService.getAccountById(destinationAccountId);
                    String amountToTransferString = request.getParameter("amountToTransfer");
                    double amountToTransfer = Double.parseDouble(amountToTransferString);
                    accountService.transferMoney(account, destinationAccount, amountToTransfer);
                }
            }

            List<Transaction> transactionsList = new ArrayList<Transaction>();
            if (account != null) {
                transactionsList = transactionService.getAccountTransactions(account);
            }

            request.setAttribute("account", account);
            request.setAttribute("accountsForTransfer", accountsForTransfer);
            request.setAttribute("transactionsList", transactionsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/jsp/accountDetails.jsp").forward(request, response);
    }
}
