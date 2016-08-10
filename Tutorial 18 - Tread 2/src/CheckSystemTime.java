import java.text.DateFormat;
import java.util.*;

public class CheckSystemTime implements Runnable {

	// In order to stop a Thread of the pool You could surround everything in
	// run with a while loop that runs until a variables value changes to false
	// and then when you are done change that value to false to end the thread
	// (A for wouldn't work unless you put a break in it. 
	// It is best to use a while when you don't know when a loop will end. 
	// For is used when you know when a loop will end.)﻿﻿
	public void run() {
			
			Date rightNow;
			Locale currentLocale;
			DateFormat timeFormatter;
			String timeOutput;
	
			rightNow = new Date();
			currentLocale = new Locale("en");
	
			timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, currentLocale);
			timeOutput = timeFormatter.format(rightNow);
	
			System.out.println("Time: " + timeOutput);
			
	}

}