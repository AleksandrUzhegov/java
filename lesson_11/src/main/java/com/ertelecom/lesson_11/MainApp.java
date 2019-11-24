package com.ertelecom.lesson_11;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws IOException {
        //TaskService list = new TaskRepository();      // коллекция
        TaskService list = new TaskRepositoryDB();      // база данных

        list.print();

        System.out.println("Создание списка");
        //заполнение списка с попыткой переполнения
        for (int i = 1; i < 12; i++) {
            try {
                list.addTask(i, "Тест" + i, "alex", "bob", "Задача " + i);

            } catch (TaskServiceException e) {
                System.out.println( e.getMessage() );
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i%5 == 0) list.closeTaskById(i);
            if (i%3 == 0) list.rejectTaskById(i);

        }
        list.print();
        //попытка повторного добавления
 /*       try {
            list.addTask(1,"Тест", "alex", "bob", "Задача ");
        } catch (TaskServiceException e) {
            System.out.println( e.getMessage() );
        } catch (Exception e) {
            e.printStackTrace();
        } */

        System.out.println("Удаление id 1");
        list.delTaskById(1);
        list.print();

        System.out.println("Удаление Тест4");
        list.delTaskByName("Тест4");
        list.print();

        System.out.println("-------------- Задача 8 ---------------------");

        // a. Получение списка задач по выбранному статусу;
        System.out.println("Список задач по статусу");
        System.out.println(list.getTaskByStatus(Task.Status.CREATED));

        // b. Проверка наличия задачи с указанным ID;
        System.out.println("Наличие задачи с ID");
        System.out.println("id=1: " + list.existsTaskById(1) );
        System.out.println("id=2: " + list.existsTaskById(2) );

        //c. Получение списка задач в отсортированном по статусу виде: открыта, в работе,
        //        закрыта (можете выбирать любой статус и любой порядок, главное чтобы было 3
        //                разных статуса);
        System.out.println("Сортированный список по статусу");
        System.out.println(list.getSortedTaskByStatus());

        // d. Подсчет количества задач по определенному статусу;
        System.out.println("Количество по статусу");
        System.out.println(list.getCountByStatus(Task.Status.CREATED));

/*
        System.out.println("-------------- Задача 9 ---------------------");

        System.out.println("Исходные данные:");
        list.print();

        // сохранение списка в файл
        list.saveToFile("1.txt");

        // чтение списка из файла
        list.loadFromFile("1.txt");

        System.out.println("Прочитанные данные:");
        list.print();
*/


    }
}
