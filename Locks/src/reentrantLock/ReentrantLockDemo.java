/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author admin
 */
public class ReentrantLockDemo{
  
    public static void main(String[] args) {          
        CommonResource commonResource= new CommonResource();//создаем объект класса CommonResource
        ReentrantLock locker = new ReentrantLock(); //создаем объект класса ReentrantLock
        for (int i = 1; i < 6; i++){
            //создаем и запускаем потоки 
            Thread t = new Thread(new CountThread(commonResource, locker));
            t.setName("Поток "+ i);
            t.start();
        }
    }
    
}

//ресурс,разделяемый потоками
class CommonResource{
    int x=0;
}
  
class CountThread implements Runnable{
    CommonResource res;//разделяемый ресурс
    ReentrantLock locker;
    
    CountThread(CommonResource res, ReentrantLock lock){
        this.res=res;
        locker = lock;
    }
    
    public void run(){
        try{
            locker.lock(); // устанавливаем блокировку
            //пока блокировка захвачена, остальные потоки сюда зайти не могут
            res.x=1;//сбрасываем значение
            for (int i = 1; i < 5; i++){
                System.out.printf("%s %d \n", Thread.currentThread().getName(), res.x);
                res.x++;
                Thread.sleep(100);
            }
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        finally{
            locker.unlock(); // снимаем блокировку
        }
    }
    
}
