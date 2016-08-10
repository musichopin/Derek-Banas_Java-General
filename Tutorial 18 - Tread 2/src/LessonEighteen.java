// In the last tutorial I coordinated threads using
// a timing method. Here I show you how to execute code based
// on a predefined time schedule and much more

// Used to schedule when certain events should be triggered
import java.util.concurrent.ScheduledThreadPoolExecutor;
// this helps us throw all our threads into a pool 
//and be able to access them doing whatever we want to do with them 

// Used to tell Java what unit of time I want to use
import static java.util.concurrent.TimeUnit.*;

public class LessonEighteen{
	
	public static void main(String[] args){
		
		addThreadsToPool();
		
	}
	
	public static void addThreadsToPool(){
		
		// Allows you to schedule code execution at some time in the future
		// You can also have code execute repetitively based on a time period
		// It must be big enough to hold all potential Threads
		
		ScheduledThreadPoolExecutor eventPool = new ScheduledThreadPoolExecutor(5);
//		*** makes it possible to creates 5 sub-threads besides the main thread 
//		(normalde 3 sub-thread yarat�lacak ama garanti olsun diye 5 deniyor) ***
				
		// Adds a Thread to the pool. Tells that thread to start executing
		// after 0 seconds (immediately) and then execute every 2 seconds
				
		eventPool.scheduleAtFixedRate(new CheckSystemTime(), 0, 2, SECONDS);
				
		eventPool.scheduleAtFixedRate(new PerformSystemCheck("Mail"), 5, 5, SECONDS);
				
		eventPool.scheduleAtFixedRate(new PerformSystemCheck("Calendar"), 10,10, SECONDS);
		
		// Thread.activeCount returns the number of threads running 
//		(toplamda 4 thread var: main thread + 3 sub-threads)
		System.out.println("Number of Threads: " +Thread.activeCount());
		
		// Quiz: Why does it say there are 4 threads when we expect 3?
		
		// Create an array of threads with enough spaces for all active threads
		
		Thread[] listOfThreads = new Thread[Thread.activeCount()];
//		null print eder:
//		System.out.println(listOfThreads[0]);
		
		// enumerate fills the Thread array with all active threads
		
		Thread.enumerate(listOfThreads);
//		System.out.println(listOfThreads[0]); // Thread[main,5,main] print eder
//		System.out.println(listOfThreads[1]); // Thread[pool-1-thread-1,5,main] print eder
		
		// Cycle through all the active threads and print out their names
		
		for(Thread i : listOfThreads){
			System.out.println(i.getName());
		}
		
		// Get priority of all the threads (Priority is equal to the thread 
		// that created the new thread. Top priority is 10, lowest priority is 1
		
		for(Thread i : listOfThreads){
			System.out.println(i.getPriority());
		}
		
		// threadName.setPriority can be used to set the priority 
		
		// *** This allows the above threads to run for approximately 20 seconds
//		(main thread 20 saniye boyunca uyuduktan sonra sub-thread'lerin 
//		topland��� havuz main-thread �zerinden kapan�r (?). main-thread'in uyutulmas�n�n
//		main thread �zerinden �nceden yarat�lan sub-thread'lere etkimedi�ini anlamak �nemli).
//		(eskisi gibi sub-threadlerin i�erisinde for loop kullan�lsayd� ve 10 kere print  
//		edilmesi s�ylenseydi 10 * 20/2 kere output edilmi� olacakt�. 
//		dolay�s�yla pool'da sonsuzlu�u �nlemek i�in main thread sleep edilir) ***
		try{
			Thread.sleep(20000);
		}
		catch(InterruptedException e)
		{}
		
		// Shuts down all threads in the pool to avoid infinity
		eventPool.shutdown();
//		main thread'in kendisi kapanmaz (system.exit(0) denirse kapan�r(?))
		
	}
	
}
/*
Number of Threads: 4
main
pool-1-thread-1
pool-1-thread-2
pool-1-thread-3
5
5
5
5
Time: 2:59:26 PM
Time: 2:59:28 PM
Time: 2:59:30 PM
Checking Mail
Time: 2:59:32 PM
Time: 2:59:34 PM
Checking Mail
Checking Calendar
Time: 2:59:36 PM
Time: 2:59:38 PM
Time: 2:59:40 PM
Checking Mail
Time: 2:59:42 PM
Time: 2:59:44 PM
Time: 2:59:46 PM
Checking Mail
Checking Calendar
*/