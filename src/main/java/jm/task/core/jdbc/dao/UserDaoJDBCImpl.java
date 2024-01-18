package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String command = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name TEXT NOT NULL,\n" +
                "    lastName TEXT NOT NULL,\n" +
                "    age TINYINT NOT NULL\n" +
                ");";
        try {
            connection.createStatement().executeUpdate(command);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String command = "DROP TABLE IF EXISTS users;";
        try {
            connection.createStatement().executeUpdate(command);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String command = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String command = "DELETE FROM users WHERE id=?;";
        try {
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String command = "SELECT * from users;";
        List<User> users = new ArrayList<>();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(command);

            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                users.add(new User(id, name, lastName, age));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String command = "TRUNCATE users;";
        try {
            connection.createStatement().executeUpdate(command);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
