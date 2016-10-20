package condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo { 
    
    public static void main(String[] args) { 
        //Объект, на котором производитель и потребитель поток будет работать
        ProducerConsumerImpl sharedObject = new ProducerConsumerImpl(); 
        //Создание производителя и потребителя
        Producer p = new Producer(sharedObject); 
        Consumer c = new Consumer(sharedObject);
        //Запуск потоков
        p.start(); 
        c.start();
    } 

} 

class ProducerConsumerImpl { 
    private static final int CAPACITY = 10; 
    private final Queue queue = new LinkedList<>();
    private final Random theRandom = new Random();
    //объекты классов Lock и Condition
    private final Lock aLock = new ReentrantLock(); 
    private final Condition bufferNotFull = aLock.newCondition(); 
    private final Condition bufferNotEmpty = aLock.newCondition(); 
    
    public void put() throws InterruptedException {
        aLock.lock();
        try { 
            while (queue.size() == CAPACITY) { 
                System.out.println(Thread.currentThread().getName() + " : Buffer is full, waiting");
                bufferNotEmpty.await(); 
            }
            int number = theRandom.nextInt();
            boolean isAdded = queue.offer(number);
            if (isAdded) { 
                System.out.printf("%s added %d into queue %n", Thread.currentThread().getName(), number);
                //оповестить потребителя, что в буфере есть элемент
                System.out.println(Thread.currentThread().getName() + " : Signalling that buffer is no more empty now");
                bufferNotFull.signalAll();
            }
        } 
        finally { 
            aLock.unlock(); 
        } 
    } 
    
    public void get() throws InterruptedException { 
        aLock.lock(); 
        try { 
            while (queue.size() == 0) { 
                System.out.println(Thread.currentThread().getName() + " : Buffer is empty, waiting");
                bufferNotFull.await();
            } 
            Integer value =(Integer) queue.poll(); 
            if (value != null) { 
                System.out.printf("%s consumed %d from queue %n", Thread.currentThread().getName(), value);
                //оповестить производителя, что буфер может быть пуст
                System.out.println(Thread.currentThread().getName() + " : Signalling that buffer may be empty now");
                bufferNotEmpty.signalAll(); 
            }
        } 
        finally { 
            aLock.unlock();
        }
    }
} 

class Producer extends Thread {
    ProducerConsumerImpl pc; 
    
    public Producer(ProducerConsumerImpl sharedObject) {
        super("PRODUCER"); 
        this.pc = sharedObject;
    } 
    
    public void run() { 
        try {
            pc.put();
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }
    
} 

class Consumer extends Thread { 
    ProducerConsumerImpl pc; 
    
    public Consumer(ProducerConsumerImpl sharedObject) { 
        super("CONSUMER"); 
        this.pc = sharedObject;
    }

    public void run() { 
        try {
            pc.get();
        } 
        catch (InterruptedException e) { 
            e.printStackTrace();
        } 
    } 
    
}


