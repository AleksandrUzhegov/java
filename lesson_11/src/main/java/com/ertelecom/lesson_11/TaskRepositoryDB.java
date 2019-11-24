package com.ertelecom.lesson_11;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryDB implements TaskService {
    private SessionFactory factory;

    public TaskRepositoryDB() {
        prepare();
    }

    @Override
    public void prepare()  {
        factory = new Configuration().configure( "hibernate.cfg.xml" ).buildSessionFactory();
    }

    @Override
    protected void finalize() throws Throwable {
        factory.close();
        super.finalize();
    }

    // создать и сохранить задачу
    @Override
    public boolean addTask(Task obj)  {
        EntityManager em = null;
        boolean result = false;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
            result = true;
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    // возвращаоем задачу по ID
    @Override
    public Task getTaskById(long id) {
        EntityManager em = null;
        Task result = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            result = em.find(Task.class, id );
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    // удаление задачи по ID
    @Override
    public void delTaskById(long id) {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(Task.class, id));
            session.getTransaction().commit();
        } finally {
            if (session != null) session.close();
        }
    }

    // удаление задачи по названию
    @Override
    public void delTaskByName(String name) {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("DELETE Task WHERE taskName = :name").setParameter("name", name).executeUpdate();
            session.getTransaction().commit();
        } finally {
            if (session != null) session.close();
        }
    }

    // изменить статус у записи в базе
    @Override
    public void setStatusById(long id, Task.Status status) {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            Task task = session.get(Task.class, id);
            task.setStatus(status);
            session.update(task);
            session.getTransaction().commit();
        } finally {
            if (session != null) session.close();
        }
    }

    // создать список по статусом или все
    @Override
    public List<Task> getTaskByStatus(Task.Status status) {
        List<Task> list = new ArrayList();

        int statusId = 0;
        if (status != null) statusId = status.getStatusId();

        String sql = null;
        if (statusId > 0) sql = "SELECT a FROM Task a where a.statusId = " + statusId;      // отбор по статусу
            else sql = "SELECT a FROM Task a";                                              // отбор всех

        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            List<Task> tasks = session.createQuery(sql, Task.class).getResultList();
            list.addAll(tasks);
            session.getTransaction().commit();
        } finally {
            if (session != null) session.close();
        }

        return list;
    }

    // очистить базу
    @Override
    public void clear() {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("DELETE Task").executeUpdate();
            session.getTransaction().commit();
        } finally {
            if (session != null) session.close();
        }
    }

    // вернуть весь список
    @Override
    public List<Task> getList() {
        return getTaskByStatus(null);
    }


}
