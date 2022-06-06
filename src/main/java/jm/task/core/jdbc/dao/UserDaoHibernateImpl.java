package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session sess = sessionFactory.openSession()) {
            Transaction transaction = sess.beginTransaction();
            sess.createSQLQuery("CREATE TABLE IF NOT EXISTS user ("
                    + "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "lastName TEXT NOT NULL,"
                    + "age TINYINT NOT NULL,"
                    + "PRIMARY KEY (id))").executeUpdate();
            try {
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session sess = sessionFactory.openSession()) {
            Transaction transaction = sess.beginTransaction();
            sess.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();

            try {
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session sess = sessionFactory.openSession()) {
            Transaction transaction = sess.beginTransaction();
            sess.save(new User(name, lastName, age));

            try {
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }


    @Override
    public void removeUserById(long id) {
        try(Session sess = sessionFactory.openSession()) {
            Transaction transaction = sess.beginTransaction();
            sess.remove(sess.find(User.class, id));
            try {
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session sess = sessionFactory.openSession()) {
            Transaction transaction = sess.beginTransaction();
            userList = sess.createQuery("FROM User").list();
            try {
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session sess = sessionFactory.openSession()) {
            Transaction transaction = sess.beginTransaction();
            sess.createSQLQuery("TRUNCATE TABLE User").executeUpdate();
            try {
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
}
