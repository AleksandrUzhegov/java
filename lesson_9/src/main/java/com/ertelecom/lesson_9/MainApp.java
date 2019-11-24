package com.ertelecom.lesson_9;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws IOException {
        TaskService list = new TaskRepository();
        System.out.println("Создание списка");
        //заполнение списка с попыткой переполнения
        for (int i = 1; i < 12; i++) {
            try {
                list.addTask(i, "Тест" + i, "alex", "bob", "Задача " + i);

                if (i%5 == 0) list.closeTaskByID(i);
                if (i%3 == 0) list.rejectTaskByID(i);

            } catch (TaskServiceException e) {
                System.out.println( e.getMessage() );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
/*        list.print();
        //попытка повторного добавления
        try {
            list.addTask(1,"Тест", "alex", "bob", "Задача ");
        } catch (TaskServiceException e) {
            System.out.println( e.getMessage() );
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Удаление id 1");
        list.delTaskById(1);
        list.print();

        System.out.println("Удаление Тест4");
        list.delTaskByName("Тест4");
        list.print(); */

        System.out.println("-------------- Задача 9 ---------------------");

        System.out.println("Исходные данные:");
        list.print();

        // сохранение списка в файл
        list.saveToFile("1.txt");

        // чтение списка из файла
        list.loadFromFile("1.txt");

        System.out.println("Прочитанные данные:");
        list.print();


    }
}
