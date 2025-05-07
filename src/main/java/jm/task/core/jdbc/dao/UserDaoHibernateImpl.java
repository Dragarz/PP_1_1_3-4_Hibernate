package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getHibernateConnection();
    private Transaction transaction = null; // Объявляем переменную для транзакции

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        try  {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL_CREATE_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL_DROP_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if(user != null) {
                session.remove(user);
                session.flush();
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void close(){
        Util.closeSessionFactory();
    }
}
