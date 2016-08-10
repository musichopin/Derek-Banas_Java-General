public class LessonSeventeen{
	
	public static void main(String[] args){
		
		// Create a new Thread that executes the code in GetTime20
		
		Thread getTime = new GetTime20();
		
		// Create a new Thread created using the Runnable interface
		// Execute the code in run after 10 seconds
		
		Runnable getMail = new GetTheMail(5);
		
		Runnable getMailAgain = new GetTheMail(10);
		
		System.out.println("Number of Threads: " +Thread.activeCount()); 
		// 1 thread (main thread)
		
		// Call for the code in the method run to execute

		getTime.start();
		
		// if we called the run method it would run in the main thread of the application.
        // by calling the start method we tell the thread class to go and look for the run 
        // method and run that in its own special thread		
		new Thread(getMail).start();
		
		new Thread(getMailAgain).start();
		
		System.out.println("Number of Threads: " +Thread.activeCount()); 
		// 4 threads (main thread + 3 sub-threads)

//		*** 3 thread de ayný anda harekete baþlar ve 
//		sleep time ile tekrar sayýsý dikkate alýnarak print edilir.
//		thread olmasaydý main thread'deki metodlarýn bitiþ sýrasýna göre sýrayla print edilirdi ***
		
	}
	
}
/*
Number of Threads: 1
Number of Threads: 4
8:24:47 PM
Aug 7, 2016

8:24:49 PM
Aug 7, 2016

8:24:51 PM
Aug 7, 2016

Checking for Mail
8:24:53 PM
Aug 7, 2016

8:24:55 PM
Aug 7, 2016

Checking for Mail
8:24:57 PM
Aug 7, 2016

8:24:59 PM
Aug 7, 2016

8:25:01 PM
Aug 7, 2016

8:25:03 PM
Aug 7, 2016

8:25:05 PM
Aug 7, 2016
*/