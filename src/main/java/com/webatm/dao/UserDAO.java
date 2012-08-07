package com.webatm.dao;

import com.webatm.domain.User;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/5/12
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {
    User getByName(String name) throws SQLException;

    User getById(int id) throws SQLException;
}
