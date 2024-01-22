package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String command = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name TEXT NOT NULL,\n" +
                "    lastName TEXT NOT NULL,\n" +
                "    age TINYINT NOT NULL\n" +
                ");";

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(command).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw e;
        }
    }
}
