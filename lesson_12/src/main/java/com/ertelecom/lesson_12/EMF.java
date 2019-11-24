package com.ertelecom.lesson_12;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class EMF {
    private static final SessionFactory factory;

    static {
        factory = new Configuration().configure( "hibernate.cfg.xml" ).buildSessionFactory();
    }

    public static void close() {
        if (factory != null) factory.close();
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static Session getCurrentSession() {
        return factory.getCurrentSession();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
