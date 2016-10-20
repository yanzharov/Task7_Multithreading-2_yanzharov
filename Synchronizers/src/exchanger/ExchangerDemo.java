/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchanger;

import java.util.concurrent.Exchanger;

/**
 *
 * @author admin
 */
public class ExchangerDemo {
    
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<String>();//создаем объект класса Exchanger
        //создаем потоки и передаем в конструктор объект exchanger и сообщение
        Thread t1 = new MyThread(exchanger, "I like coffee.");
        Thread t2 = new MyThread(exchanger, "I like tea");
        //запускаем потоки
        t1.start();
        t2.start();
   }
    
}
 
class MyThread extends Thread {
 
    Exchanger<String> exchanger;
    String message;
 
    MyThread(Exchanger<String> exchanger, String message) {
        this.exchanger = exchanger;
        this.message = message;
    }
 
    public void run() {
        try {
            System.out.println(this.getName() + " message: " + message);
 
            //поток блокируется до тех пор, пока другой поток не вызовет свой метод exchange
            //после объекты message меняются местами
            message = exchanger.exchange(message);
 
            System.out.println(this.getName() + " message: " + message);
        } 
        catch (Exception e) {
        }
    }
}

