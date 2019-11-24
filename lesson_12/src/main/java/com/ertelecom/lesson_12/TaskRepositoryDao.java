package com.ertelecom.lesson_12;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
public class TaskRepositoryDao implements TaskRepository {
    @PostConstruct
    public void init() {
    }

    public TaskRepositoryDao() {
        prepare();
    }

    @Override
    public void prepare()  {
    }

    // создать и сохранить задачу
    @Override
    public boolean addTask(Task obj)  {
        EntityManager em = null;
        boolean result = false;
        try {
            em = EMF.getEntityManager();
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
            em = EMF.getEntityManager();
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
            session = EMF.getCurrentSession();
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
            session = EMF.getCurrentSession();
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
            session = EMF.getCurrentSession();
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
        List<Task> list = Collections.emptyList();

        int statusId = 0;
        if (status != null) statusId = status.getStatusId();

        String sql = null;
        if (statusId > 0) sql = "SELECT a FROM Task a where a.statusId = " + statusId;      // отбор по статусу
            else sql = "SELECT a FROM Task a";                                              // отбор всех

        Session session = null;
        try {
            session = EMF.getCurrentSession();
            session.beginTransaction();
            list = session.createQuery(sql, Task.class).getResultList();
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
            session = EMF.getCurrentSession();
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
