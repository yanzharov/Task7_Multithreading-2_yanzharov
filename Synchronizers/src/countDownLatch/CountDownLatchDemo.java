package countDownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    
    public static void main(String args[]) {
        //создаем объект класса CountDownLatch со значением счетчика равным трем
        final CountDownLatch latch = new CountDownLatch(3);
          
        //создаем потоки и передаем имя потока и объект класса CountDownLatch в качестве параметра в конструктор
        MyThread T1 = new MyThread("T1", latch);
        MyThread T2 = new MyThread("T2", latch);
        MyThread T3 = new MyThread("T3", latch);

        //запускаем потоки
        T1.start(); 
        T2.start();
        T3.start();

        try {
           //поток вызывающий await() блокируется до того момента, пока счетчик не станет равным нулю
           latch.await(); 
        } 
        catch(InterruptedException ie) {
        }
        //после того как счетчик станет равным нулю, поток продолжит работу
        System.out.println("All threads are ready");
    }
    
}

class MyThread extends Thread {
    private final String name;//имя потока
    private final CountDownLatch latch;//объект класса CountDownLatch, который мы получаем из главного потока
    
    public MyThread(String name, CountDownLatch latch) {
        this.name = name;
        this.latch = latch;
    }

    public void run(){
        System.out.println( name + " starting");
        try {
           Thread.sleep(1000);//поток засыпает на секунду
        } 
        catch (Exception e){
        }
        System.out.println( name + " ready");
        latch.countDown(); //уменьшаем значение счетчика на единицу
    }
       
}
