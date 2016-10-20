/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    
    public static void main(String args[]) {
        Semaphore semaphore=new Semaphore(2);//создаем семафор который разрешает лишь двум задачам работать одновременно
        //создаем 4 потока и передаем в качестве параметра в конструктор объект semaphore
        MyThread mt1 = new MyThread(semaphore);
        MyThread mt2 = new MyThread(semaphore);
        MyThread mt3 = new MyThread(semaphore);
        MyThread mt4 = new MyThread(semaphore);
        //запускаем потоки
        mt1.start();
        mt2.start();
        mt3.start();
        mt4.start();
   }
    
}
 
class MyThread extends Thread {
    Semaphore semaphore;
 
    MyThread(Semaphore semaphore){
        this.semaphore = semaphore;
    }
 
    public void run() {
        try {
            semaphore.acquire();//получаем разрешение от семафора и уменьшаем их количество на единицу
            //если разрешение не доступно, поток ждет
            System.out.println("Hello " + this.getName());
            sleep(2000);//спим 2 секунды
        } 
        catch (InterruptedException ie) {
        }
        finally {
            semaphore.release();//возвращает разрешение семафору
            System.out.println("Goodbye " + this.getName());
        }
    }
    
}
