package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session sess = sessionFactory.openSession();
        sess.beginTransaction();
        sess.createNativeQuery("CREATE TABLE IF NOT EXISTS user ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "name TEXT NOT NULL,"
                + "lastName TEXT NOT NULL,"
                + "age TINYINT NOT NULL,"
                + "PRIMARY KEY (id));").executeUpdate();

        sess.getTransaction().commit();
        sess.close();
    }

    @Override
    public void dropUsersTable() {
        Session sess = sessionFactory.openSession();
        sess.beginTransaction();
        sess.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
        sess.getTransaction().commit();
        sess.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session sess = sessionFactory.openSession();
        sess.beginTransaction();
        sess.save(new User(name, lastName, age));
        sess.getTransaction().commit();
        sess.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(session.find(User.class, id));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> allUsers;
        Session sess = sessionFactory.openSession();
        sess.beginTransaction();
        allUsers = sess.createNativeQuery("SELECT * FROM user").list();
        sess.getTransaction().commit();
        sess.close();

        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session sess = sessionFactory.openSession();
        sess.beginTransaction();
        sess.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
        sess.getTransaction().commit();
        sess.close();
    }
}
