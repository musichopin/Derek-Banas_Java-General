import java.lang.Math.*;

public class Lesson35{ // only public, abstract and final allowed for (conventional) classes
//	only 1 public class per file allowed
	
	// ERROR 1: Cannot make a static reference to the non-static method
	// SOLVED: You can't call a non static method from a static method
	// private void printSomething(){
	
	private static void printSomething(){
	
	// ERROR 2: Unresolved compilation problem
	// SOLVED: Pay attention to Eclipse Errors
	// Int BigNumber = 100000;
		
		
		// ERROR 3: A string literal isn't properly closed
		// SOLVED: Pay attention to Eclipse
		//String something = "A string error
				//is occurring";
		
		System.out.println("Something");
		
	} // If this is missing you get ERROR 2
	
	// ERROR 4: Exception in thread "main" java.lang.NoSuchMethodError: main
	// SOLVED: Make sure you type the main function correctly
	// public static void main(String args){ // This is Wrong
	
	public static void main(String[] args){
		
		// ERROR 5: Can't be resolved to a variable
		// SOLVED: Pay attention to Eclipse
		// printsomething; // This is Wrong
		
		printSomething();
		
		// ERROR 6: Type mismatch Can't convert from int to String
		// SOLVED: Convert the integer
		
		int number = 12;
		// String anotherNum = number; // This is Wrong
		String anotherNum = Integer.toString(number);
		
//		 int number2 = Integer.parseInt(anotherNum); // Convert from string to int
		
		
		// ERROR 7: Can't be resolved to a type
		// SOLVED: import the dimension library
		// Dimension dim = new Dimension();
		
		// ERROR 8: Method is undefined
		// SOLVED: Make sure methods are in the class
		// and capitalized correctly
		
		double pi = 3.14;
		// long randLong = Lesson34.Round(pi); // The wrong way
		
		// ERROR 9: Can't invoke method
		// SOLVED: Understand how methods work in Java
		
		long randLong = Math.round(pi); // The right way
		// randLong = pi.round(); // Wrong way
		
		// ERROR 11: The method is not applicable for the arguments
		// SOLVED: Provide the right arguments in the right order
		
		// getStuff(1.234, 5); // Wrong Way
		
		getStuff(1,5.0); // Right Way
		
		// ERROR 12: Syntax error on token ",; expected
		// SOLVED: Understand how methods are called in Java vs. other languages
		
		// double sumNum = addThem(Lesson35,1,2); // Wrong Way
		double sumNum = Lesson35.addThem(1,2);
		
		// ERROR 13: Syntax error on token '=='
		// SOLVED: = is different from ==
		// int value == 1;
		
		
	}
	
	// ERROR 10: Can't be resolved to a type
	// SOLVED: Always provide the type in methods
	
	/* public static void getStuff(number1, number2){
		
	} */
	
	// ERROR 14: Return type for method is missing
	// SOLVED: Provide a return type or void
	
	private static double addThem(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void getStuff(int number1, double number2){
		
		// ERROR 15: Syntax error on token ",[ expected
		// SOLVED: Understand how arrays are defined in Java
				
		// int[] intArray = new [10,10]int; // Wrong Way
				
		int[][] intArray = new int[10][10];
		
		// ERROR 16: The method is not visible
		// SOLVED: You can't private methods which are declared in 
		// another class. private static void getFileInfo()
		
		// Lesson33.getFileInfo(); // Wrong Way
		
		// ERROR 17: Local variable may not have been initialized
		// SOLVED: Always give variables default values
		
		String howMany = "10";
		// String howMany; // Wrong Way
		System.out.println(howMany);
		
		// ERROR 18: Cannot be Resolved
		// SOLVED: Understand that arrays and strings use a 
		// different version of length
		
		System.out.println(howMany.length());
		// System.out.println(howMany.length); // Wrong Way
		
		// System.out.println(intArray.length()); // Wrong Way
		System.out.println(intArray.length);
		
		// ERROR 19: Prefix Operator vs. Postfix Operator
		
		int xInt = 1, yInt = 1;
//		postfix operator:
		xInt = yInt++; // Passes the original value of yInt to xInt before incrementing
		
		System.out.println("xInt: " + xInt); 
		
		System.out.println("yInt: " + xInt); 
		
		// ERROR 20: Not calling break at end of case
		
		int day = 1;
		
		switch (day){
			case 1: System.out.println("Monday");
			case 2: System.out.println("Tuesday");
			case 3: System.out.println("Wednesday");
			case 4: System.out.println("Thursday");
			default: System.out.println("Friday");
		}
		
	} // END OF GetStuff method
	
} // END OF Class

/*
Something
10
2
10
xInt: 1
yInt: 1
Monday
Tuesday
Wednesday
Thursday
Friday
*/