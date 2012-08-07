package com.webatm.security;

import com.webatm.domain.User;
import com.webatm.dao.UserDAO;
import com.webatm.dao.jdbc.UserJdbcDAO;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/13/12
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserService {

    private volatile static UserService instance;
    private UserDAO userDAO;

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    private UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserJdbcDAO();
        }
        return userDAO;
    }

    public User getUserByName(String name) throws SQLException {
        return getUserDAO().getByName(name);
    }

    public User getUserById(int id) throws SQLException {
        return getUserDAO().getById(id);
    }
}
