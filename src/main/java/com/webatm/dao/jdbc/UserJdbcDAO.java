package com.webatm.dao.jdbc;

import com.webatm.domain.User;
import com.webatm.dao.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/5/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserJdbcDAO extends GenericJdbcDAO implements UserDAO {

    @Override
    public User getByName(String name) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT ID FROM USER WHERE NAME = ?");
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("ID"));
                user.setName(name);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return user;
    }

    @Override
    public User getById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT NAME FROM USER WHERE ID = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setName(resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return user;
    }
}
