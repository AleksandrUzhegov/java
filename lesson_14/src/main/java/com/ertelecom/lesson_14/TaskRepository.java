package com.ertelecom.lesson_14;

import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class TaskRepository  {
    @PersistenceContext
    private EntityManager entityManager;

    // создать и сохранить задачу
    public void addTask(Task task)  {
        entityManager.persist(task);
    }

    // сохранить задачу
    public void updTask(Task task)  {
        entityManager.merge(task);
    }

    // возвращаоем задачу по ID
    public Task getTaskById(long id) {
        return entityManager.find(Task.class, id );
    }

    // удаление задачи по ID
    public void delTaskById(long id) {
        entityManager.remove( entityManager.find(Task.class, id ) );
    }

    // изменить статус у записи в базе
    public void setStatusById(long id, Task.Status status) {
        Task task = entityManager.find(Task.class, id );
        task.setStatus(status);
        entityManager.merge(task);
    }

    // вернуть весь список
    public List<Task> getAllTasks() {
        return getTasksByFilter(null);
    }

    // вернуть список по фильтру статус
    public List<Task> getTasksByFilter(Integer statusId) {
        StringBuffer sql = new StringBuffer("SELECT i FROM Task i");
        if (statusId != null && statusId > 0) sql.append(" where i.statusId = " + statusId.toString() );
        List<Task> tasks = entityManager.createQuery(String.valueOf(sql)).getResultList();
        return tasks;
    }


}
