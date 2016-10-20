/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    //создаем объект класса ReentrantReadWriteLock
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    //сообщение, которое будем читать и записывать
    private static String message = "a";
    
    public static void main(String[] args) throws InterruptedException{
        //создаем и запускаем потоки
        Thread t1 = new Thread(new Read());
        Thread t2 = new Thread(new WriteA());
        Thread t3 = new Thread(new WriteB());
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        
    }

    static class Read implements Runnable {
        //класс, читающий сообщение
        public void run() {
            for(int i = 0; i<= 10; i++) {
                if(lock.isWriteLocked()) {
                    System.out.println("I'll take the lock from Write");
                }
                lock.readLock().lock();//устанавливаем блокировку на чтение и выводим сообщение
                System.out.println("ReadThread " + Thread.currentThread().getId() + " ---> Message is " + message );
                lock.readLock().unlock();//снимаем блокировку
            }
        }
        
    }

    static class WriteA implements Runnable {
        //класс, добавляющий к сообщению символ 'a'
        public void run() {
            for(int i = 0; i<= 10; i++) {
                try {
                    //устанавливаем блокировку на запись и добавляем символ 'a'
                    lock.writeLock().lock();
                    message = message.concat("a");
                } 
                finally {
                    lock.writeLock().unlock();//снимаем блокировку
                }
            }
        }

    }

    static class WriteB implements Runnable {
        //класс, добавляющий к сообщению символ 'b'
        public void run() {
            for(int i = 0; i<= 10; i++) {
                try {
                    //устанавливаем блокировку на запись и добавляем символ 'b'
                    lock.writeLock().lock();
                    message = message.concat("b");
                } 
                finally {
                    lock.writeLock().unlock();//снимаем блокировку
                }
            }
        }

    }

}
