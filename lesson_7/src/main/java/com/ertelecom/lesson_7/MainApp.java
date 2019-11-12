package com.ertelecom.lesson_7;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainApp {
    public static final int CARS_COUNT = 4;
    public static CyclicBarrier cyclicBarrier;     // старт
    public static Semaphore semaphore;             // проезд тоннеля
    public static CountDownLatch countDown;        // ожидание окончания
    public static Lock finish;                     // финиш

    private static final Runnable actionStart = new Runnable() {
        @Override
        public void run() {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        }
    };

    static {
        cyclicBarrier = new CyclicBarrier(CARS_COUNT, actionStart);
        semaphore = new Semaphore(CARS_COUNT/2);
        countDown = new CountDownLatch(CARS_COUNT);
        finish = new ReentrantLock();
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!" );
        Race race = new Race( new Road( 60 ), new Tunnel(), new Road( 40 ));

        Car[] cars = new Car[CARS_COUNT];
        for ( int i = 0 ; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + ( int ) (Math.random() * 10 ));
        }

        for ( int i = 0 ; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        // ждем окончания гонки
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!" );
    }


}
