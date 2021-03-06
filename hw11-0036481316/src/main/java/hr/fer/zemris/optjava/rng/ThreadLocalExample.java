package hr.fer.zemris.optjava.rng;
public class ThreadLocalExample {


    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal =
               new ThreadLocal<Integer>();

        @Override
        public void run() {
        	int ante =  (int) (Math.random() * 100D);
        	System.out.println(Thread.currentThread().getId()+"  "+ante);
            threadLocal.set( ante);
    
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
    
            System.out.println(Thread.currentThread().getId()+"  "+threadLocal.get());
        }
    }


    public static void main(String[] args){
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        try{
        	 thread1.join(); //wait for thread 1 to terminate
             thread2.join(); //wait for thread 2 to terminate
        }catch(InterruptedException ex){
        	
        }
       
    }

}