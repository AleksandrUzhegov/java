package com.ertelecom.lesson_10;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepositoryDB implements TaskService {
    private static Connection connection;
    private static Statement stmt;

    public TaskRepositoryDB() {
        prepare();
    }

    @Override
    public void prepare()  {
        try {
            connect();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    // создание соединения
    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }

    // закрытие соединения
    public static void disconnect() {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        disconnect();
        super.finalize();
    }

    // соответствие статусу ID в базе
    private int getStatusID(Task.Status status) {
        if (status == null) return 0;

        int result = 0;
        switch (status) {
            case CREATED: result = 1; break;
            case CLOSSED: result = 2; break;
            case REJECTED: result = 3; break;
        }
        return result;
    }

    // создать и сохранить задачу
    @Override
    public boolean addTask(Task obj)  {
        String sql = "INSERT INTO tasks(task_id, task_name, owner_name, executor_name, info, status_id) VALUES(?, ?, ?, ?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql) ) {
            ps.setLong( 1 , obj.getId());
            ps.setString( 2 , obj.getTaskName());
            ps.setString( 3 , obj.getOwnerName());
            ps.setString( 4 , obj.getExecutorName());
            ps.setString( 5 , obj.getInfo());
            ps.setInt( 6, getStatusID( obj.getStatus() ));

            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // заполнить задачу из набора
    private Task getTask(ResultSet rs) throws SQLException {
        if (rs == null) return null;

        Task obj = null;
        if (rs.next()) {
           obj = new Task( rs.getLong("task_id")
                   ,rs.getString("task_name")
                   ,rs.getString("owner_name")
                   ,rs.getString("executor_name")
                   ,rs.getString("info")
           );
            switch (rs.getInt("status_id")) {
                case 1: obj.setStatus(Task.Status.CREATED); break;
                case 2: obj.setStatus(Task.Status.CLOSSED); break;
                case 3: obj.setStatus(Task.Status.REJECTED); break;
           }
        }
        return obj;
    }

    // возвращаоем задачу по ID
    @Override
    public Task getTaskById(long id) {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM tasks where task_id = " + id + ";") ){
            return getTask(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // удаление задачи по ID
    @Override
    public void delTaskById(long id) {
        try {
            stmt.executeUpdate("DELETE FROM tasks where task_id = " + id + ";");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // удаление задачи по названию
    @Override
    public void delTaskByName(String name) {
        try {
            stmt.executeUpdate("DELETE FROM tasks where task_name = '" + name + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // печать списка
    @Override
    public void print() {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM tasks;") ) {
            while (true) {
                Task obj = getTask(rs);
                if (obj == null) break;
                System.out.println(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // изменить статус у записи в базе
    private void setStatusById(long id, Task.Status status) {
        int status_id = getStatusID(status);
        try {
            stmt.executeUpdate("UPDATE tasks SET status_id = " + status_id + " where task_id = " + id + ";");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeTaskByID(long id) {
        setStatusById(id, Task.Status.CLOSSED);
    }

    @Override
    public void rejectTaskByID(long id) {
        setStatusById(id, Task.Status.REJECTED);
    }

    @Override
    public List<Task> getTaskByStatus(Task.Status status) {
        List<Task> list = new ArrayList();

        int status_id = getStatusID(status);
        String sql = null;
        if (status_id > 0) sql = "SELECT * FROM tasks where status_id = " + status_id + ";";
                else sql = "SELECT * FROM tasks;";

        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (true) {
                Task obj = getTask(rs);
                if (obj == null) break;
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean existsTaskById(long id) {
        return getTaskById(id) != null;
    }

    @Override
    public List<Task> getSortedTaskByStatus() {
        return getTaskByStatus(null).stream().sorted((o1,o2) -> o1.compareTo(o2) ).collect(Collectors.toList());
    }

    @Override
    public long getCountByStatus(Task.Status status) {
        return getTaskByStatus(status).stream().count();
    }

    @Override
    public void clear() {
        try {
            stmt.executeUpdate("DELETE FROM tasks;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getList() {
        return getTaskByStatus(null);
    }


}
