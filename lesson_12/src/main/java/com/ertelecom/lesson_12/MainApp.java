package com.ertelecom.lesson_12;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args)  {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainAppConfig.class);
        TaskService taskService = context.getBean("taskService", TaskService.class);

        taskService.print();

        for (int i = 1; i < 12; i++) {
            try {
                taskService.addTask(i, "Тест" + i, "alex", "bob", "Задача " + i);

            } catch (TaskServiceException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        taskService.print();

        System.out.println("Удаление id 1");
        taskService.delTaskById(1);
        taskService.print();

        context.close();
    }
}
