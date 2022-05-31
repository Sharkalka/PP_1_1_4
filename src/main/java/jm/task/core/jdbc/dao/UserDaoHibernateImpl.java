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
        Session sess = sessionFactory.openSession();
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

        sess.close();
    }

    @Override
    public void dropUsersTable() {
        Session sess = sessionFactory.openSession();
        Transaction transaction = sess.beginTransaction();
        sess.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();

        try {
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        sess.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session sess = sessionFactory.openSession();
        Transaction transaction = sess.beginTransaction();
        sess.save(new User(name, lastName, age));

        try {
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        sess.close();
    }


    @Override
    public void removeUserById(long id) {
        Session sess = sessionFactory.openSession();
        Transaction transaction = sess.beginTransaction();
        sess.remove(sess.find(User.class, id));
        try {
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        sess.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> userList;
        Session sess = sessionFactory.openSession();
        Transaction transaction = sess.beginTransaction();
        userList = sess.createQuery("from User").list();
        try {
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        sess.close();

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session sess = sessionFactory.openSession();
        Transaction transaction = sess.beginTransaction();
        sess.createSQLQuery("truncate table user").executeUpdate();
        try {
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        sess.close();
    }
}
