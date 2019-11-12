package com.ertelecom.lesson_7;

public class Tunnel extends Stage {
    public Tunnel () {
        this .length = 80 ;
        this .description = "Тоннель " + length + " метров" ;
    }
    @Override
    public void go (Car c) {
        try {
            try {
                MainApp.semaphore.acquire();
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " +  description);
                MainApp.semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}