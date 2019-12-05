package com.ertelecom.lesson_13;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class TaskRepositoryDao implements TaskRepository  {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TaskRepositoryDao() {
        prepare();
    }

    @Override
    public void prepare()  {
    }

    // создать и сохранить задачу
    @Override
    public void addTask(Task task)  {
        Session session = sessionFactory.getCurrentSession();
        session.save(task);
    }

    // сохранить задачу
    @Override
    public void updTask(Task task)  {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(task);
    }

    // возвращаоем задачу по ID
    @Override
    public Task getTaskById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Task.class, id );
    }

    // удаление задачи по ID
    @Override
    public void delTaskById(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Task.class, id));
    }

    // изменить статус у записи в базе
    @Override
    public void setStatusById(long id, Task.Status status) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.get(Task.class, id);
        task.setStatus(status);
        session.update(task);
    }

    // вернуть весь список
    @Override
    public List<Task> getAllTasks() {
        Session session = sessionFactory.getCurrentSession();
        List<Task> tasks = session.createQuery("SELECT i FROM Task i").getResultList();
        return tasks;
    }


}
