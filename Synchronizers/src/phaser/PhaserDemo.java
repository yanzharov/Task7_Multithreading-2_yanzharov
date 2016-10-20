/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phaser;

import java.util.concurrent.Phaser;

/**
 *
 * @author admin
 */
public class PhaserDemo {
    
    public static void main(String[] args) {
        Phaser phaser = new Phaser();//создаем объект класса Phaser
        //создаем потоки и передаем в конструктор объект phaser и время сна
        Thread t1 = new MyThread(phaser,1000);
        Thread t2 = new MyThread(phaser,3000);
        Thread t3 = new MyThread(phaser,10000);
        //запускаем потоки
        t1.start();
        t2.start();
        t3.start();
   }
    
}
 
class MyThread extends Thread {
    Phaser phaser;
    int sleep;//время сна потока
 
    MyThread(Phaser phaser, int sleep){
        this.phaser=phaser;
        this.sleep=sleep;
    }
 
    public void run() {
        phaser.register();//регистрируем нового участника
        System.out.println(this.getName() + " begin");
        try {
            Thread.sleep(sleep);//поток спит заданное количесво времени
        } 
        catch (Exception e) { 
        }
        phaser.arriveAndAwaitAdvance();//указывает, что поток завершил выполнение фазы
        //приостанавливается до момента, пока все остальные потоки не завершат выполнение фазы
        System.out.println(this.getName() + " middle");
        try {
            Thread.sleep(sleep);//поток спит заданное количесво времени
        } 
        catch (Exception e) { 
        }
        System.out.println(this.getName() + " end");
   }
}
