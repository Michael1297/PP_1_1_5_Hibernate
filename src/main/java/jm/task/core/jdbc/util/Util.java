package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection(){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                if (!connection.isClosed()) {
                    System.out.println("Connection OK");
                }

            } catch (SQLException e){
                System.err.println("Connection Error");
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, DRIVER);
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USERNAME);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

                config.setProperties(properties);
                config.addAnnotatedClass(User.class);

                sessionFactory = config.buildSessionFactory();
            } catch (HibernateException e) {
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }
}
