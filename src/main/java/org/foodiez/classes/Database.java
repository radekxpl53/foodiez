package org.foodiez.classes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Database {
    private static Configuration config;
    private static Session session;
    private static SessionFactory factory;
    private static Transaction transaction;

    public static Configuration getConfig() {
        return config;
    }

    public static void setConfig(Configuration config) {
        Database.config = config;
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        Database.session = session;
    }

    public static SessionFactory getFactory() {
        return factory;
    }

    public static void setFactory(SessionFactory factory) {
        Database.factory = factory;
    }

    public static Transaction getTransaction() {
        return transaction;
    }

    public static void setTransaction(Transaction transaction) {
        Database.transaction = transaction;
    }

    public static void connect() {
        config = new Configuration()
                .configure()
                .addAnnotatedClass(Dish.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Order.class);

        factory = config.buildSessionFactory();

        session = factory.openSession();
    }

    public static void close() {
        factory.close();
        session.close();
    }

    public static void addToDatabase(Object object) {
        transaction = session.beginTransaction();

        session.persist(object);

        transaction.commit();
    }

    public static void removeFromDatabase(Object object) {
        transaction = session.beginTransaction();

        session.remove(object);

        transaction.commit();
    }

    public static void editItemDatabase(Object object) {
        transaction = session.beginTransaction();

        session.merge(object);

        transaction.commit();
    }
}
