package com.webatm.servlets;

import com.webatm.domain.Currency;
import com.webatm.domain.User;
import com.webatm.security.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.webatm.domain.Account;
import com.webatm.security.UserService;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/4/12
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountsList extends HttpServlet{

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

        try {
            //TODO Should be replaced by actual authenticated user
            User user = userService.getUserByName("User1");

            Account account = new Account();
            account.setOwner(user);
            String accountId = request.getParameter("accountId");
            if (accountId != null && accountId != "") {
                account.setId(Integer.parseInt(accountId));
            }
            String accountCurrency = request.getParameter("accountCurrency");
            if (accountCurrency != null && accountCurrency != "") {
                account.setCurrency(Currency.valueOf(accountCurrency));
            }
            String actionContext = request.getParameter("actionContext");
            if ("insert".equals(actionContext)) {
                accountService.createAccount(account);
            } else if ("delete".equals(actionContext)) {
                accountService.deleteAccount(account);
            }
            List<Account> accountsList = accountService.getAccounts(user);
            request.setAttribute("accountsList", accountsList);
            request.setAttribute("user", user);
            request.setAttribute("currencies", Currency.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/jsp/accountsList.jsp").forward(request, response);
    }
}
