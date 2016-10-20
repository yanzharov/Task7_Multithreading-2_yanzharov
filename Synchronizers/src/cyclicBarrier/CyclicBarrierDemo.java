package cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
 
    public static void main(String args[]) throws InterruptedException,BrokenBarrierException {
      CyclicBarrier barrier=new CyclicBarrier(3);//создаем объект класса CyclicBarrier 
 
      //создаем потоки и передаем в конструктор имя потока, время сна и объект barrier
      Thread T1 = new Thread(new MyRunnable("T1",3000,barrier));
      Thread T2 = new Thread(new MyRunnable("T2",2000,barrier));
      Thread T3 = new Thread(new MyRunnable("T3",1000,barrier));
 
      //запускаем потоки
      T1.start(); 
      T2.start();
      T3.start();
 
   }
}
 
class MyRunnable implements Runnable {
    private String name;//имя потока
    private int sleep;//время сна
    private CyclicBarrier barrier;//объект класса CyclicBarrier
 
    public MyRunnable(String name,int sleep,CyclicBarrier barrier) {
        this.name=name;
        this.sleep=sleep;
        this.barrier=barrier;
    }
 
    public void run() {
        try {
            System.out.println(name + ": started");
            System.out.println(name + ": 1st time sleeping " + sleep);
            Thread.sleep(sleep);//поток засыпает на заданное количество времени
 

            System.out.println(name + ": 1st time waiting");
            barrier.await();//поток должен ждать, пока все остальные потоки не дойдут до этого барьера
 
            System.out.println(name + ": 2nd time sleeping " + sleep);
            Thread.sleep(sleep);//поток снова спит
            
            System.out.println(name + ": 2nd time waiting");
            barrier.await();//и снова поток должен ждать, пока все остальные потоки не дойдут до этого барьера
 
            System.out.println(name + ": finished");
 
        } 
        catch (Exception e) {
        }
    }
}
