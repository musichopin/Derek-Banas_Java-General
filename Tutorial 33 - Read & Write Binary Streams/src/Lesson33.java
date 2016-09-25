import java.io.*;

// A binary stream is a series of data type values
// To read and write to them you use different methods
// based on the type of data that you are using

public class Lesson33{
	
	public static void main(String[] args){
		
		// Create an array of type Customer
		
		Customer[] customers = getCustomers();
		
		// A DataOutputStream allows you to print 
		// primitive data types to a file
		
		DataOutputStream custOutput = createFile(
				"/Users/Sinan/Documents/GitHub/Derek-Banas_Java-General/Tutorial 33 - Read & Write Binary Streams/src/random.txt");
//		"random.txt" denebilirdi
		
		// Enhanced for loop for arrays
		// * Cycles through all of the people objects in the customers array
//		" customers[0] = new Customer("John Smith", 21, 12.25, true, 'M'); " kýsmý *
		for(Customer person : customers){
			
			createCustomers(person, custOutput);
//			writes on file
		}
		
		// Closes the connection to the DataOutputStream
		
		try {
			custOutput.close();
		} catch (IOException e) {
			
			System.out.println("An I/O Error Occurred");
			
			// Closes the program
			
			System.exit(0);
		}
		
		getFileInfo();
//		prints on console
		
	}
	
	// class that defines all the fields for my customers
	
	private static class Customer{
		
		public String custName;
		public int custAge; 
		public double custDebt;
		public boolean oweMoney;
		public char custSex;
		
		// constructor that's called when a customer is made
		
		public Customer(String custName, int custAge, double custDebt, boolean oweMoney, char custSex){
			
			this.custName = custName; // String
			this.custAge = custAge; // Integer
			this.custDebt = custDebt; // Double
			this.oweMoney = oweMoney; // Boolean
			this.custSex = custSex; // Character
			
		}
		
	}
	
	// Creates an array of Customer Objects
	
	private static Customer[] getCustomers(){

//		array created
		Customer[] customers = new Customer[5];
		
//		array members initialized
		customers[0] = new Customer("John Smith", 21, 12.25, true, 'M');
		customers[1] = new Customer("Sally Smith", 30, 2.25, true, 'F');
		customers[2] = new Customer("Paul Ryan", 21, 0, false, 'M');
		customers[3] = new Customer("Mark Jacobs", 21, 3.25, true, 'M');
		customers[4] = new Customer("Steve Nash", 21, 5.25, true, 'M');
		
		return customers;
//		returns array full of customer objects
	}
	
	// Create the file and the DataOutputStream that will write to the file
	
	private static DataOutputStream createFile(String fileName){
		
		try{
			
			// Creates a File object that allows you to work with files 
			// on the hard drive. There is no difference between File
			// for character or binary stream writing, or reading
			
			File listOfNames = new File(fileName);
			
			// FileOutputStream is used to write streams of data to a file
			// You define whether a new file is created versus appended
			// to based on if you add a boolean to the FileOutputStream
			// FileOutputStream(file, true) : Appends to the file
			// FileOutputStream(file, false) : Creates a new file
//			FileOutputStream(file): creates a new file
			
			// BufferedOutputStream gathers all the data and then writes
			// it all at one time (Speeds up the Program)
			// DataOutputStream is used to write primitive data to the file
	
			DataOutputStream infoToWrite = new DataOutputStream(
			new BufferedOutputStream(
					new FileOutputStream(listOfNames)));
			return infoToWrite;
		}
	
		// You have to catch this when you call FileWriter
		
		catch(IOException e){
		
			System.out.println("An I/O Error Occurred");
			
			// Closes the program
			
			System.exit(0);
		
		}
		return null;
		
	}
	
	// Create a string with the customer info and write it to the file
	
	private static void createCustomers(Customer customer, DataOutputStream custOutput){
		
//		we use very specific methods this time
//		*order of methods must be same as making objects within array*
		try{
		// Write primitive data to the file 
//			***objectlere ait tüm propertyleri yan yana dizer, lesson 32'nun aksine
//			(println, \n vs olmadýðýndan)***
		
		// Writes a String in UTF format
		custOutput.writeUTF(customer.custName); 
		
		// Writes an Integer 
		custOutput.writeInt(customer.custAge); 
		
		// Writes a Double
		custOutput.writeDouble(customer.custDebt); 
		
		// Writes a Boolean 
		custOutput.writeBoolean(customer.oweMoney); 
				
		// Writes a Character
		custOutput.writeChar(customer.custSex);
		
		// You also have writeByte, writeFloat, writeLong
		// and writeShort
		
//		file'a yazdýðýmýzý print etmek isteseydik (normalde gereksiz)
		System.out.println(customer.custName);
		System.out.println(customer.custAge+"\n");
		}
		
		catch(IOException e){
			
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		
		}
		
	}
	
	// Read info from the file and write it to the screen
	
	private static void getFileInfo(){
		
		System.out.println("Info Written to File\n");
		
		DataInputStream getInfo = null;
		
		// Open a new connection to the file
		
		File listOfNames = new File(
				"/Users/Sinan/Documents/GitHub/Derek-Banas_Java-General/Tutorial 33 - Read & Write Binary Streams/src/random.txt");
		
		boolean eof = false;
		
		try {
			
			// A DataInputStream object has the methods for reading the data
			// The BufferedInputStream gathers the data in blocks
			// FileInputStream gets data from the file
			
			getInfo = new DataInputStream(
					new BufferedInputStream(
					new FileInputStream(listOfNames)));
			
			// Using a while loop that pulls data until EOFException is thrown
			
			while (!eof){
//				*exception beklemeden daha doðru yazýlmýþ loop:*
//				while (getInfo.available() > 0)
				
				// You have to read data in the exact order it was put in the file
//				***lesson32'de line line okunurken binary'de yazýldýðý sýraya göre okunuyor***
				String custName = getInfo.readUTF();
				int custAge = getInfo.readInt(); 
				double custDebt = getInfo.readDouble();
				boolean oweMoney = getInfo.readBoolean();
				char custSex = getInfo.readChar();
				
				System.out.println(custName);
				System.out.println(custAge);
				System.out.println(custDebt);
				System.out.println(oweMoney);
				System.out.println(custSex + "\n");
				
			}	
			
		} // END OF TRY
		
		catch (EOFException e) {
//			*thrown whenever the end of the file is reached*
			eof = true;
		}
		
		// Can be thrown by FileInputStream
		
		catch (FileNotFoundException e) {
			
			System.out.println("Couldn't Find the File");
			System.exit(0);
		}
		
		catch(IOException e){
			
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		
		}
		
		finally{
			
			try {
				getInfo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
/*
John Smith
21

Sally Smith
30

Paul Ryan
21

Mark Jacobs
21

Steve Nash
21

Info Written to File

John Smith
21
12.25
true
M

Sally Smith
30
2.25
true
F

Paul Ryan
21
0.0
false
M

Mark Jacobs
21
3.25
true
M

Steve Nash
21
5.25
true
M
*/