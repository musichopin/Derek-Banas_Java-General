import java.io.*;

// A character stream is just a series of characters
// Important information is normally separated by a comma,
// space, or tab.

public class Lesson32{
	
	public static void main(String[] args){
	
		// Create an array of type Customer
		
		Customer[] customers = getCustomers();
		
		// PrintWriter is used to write characters to a file in this situation
//		if the file doesn't exist create it, if it exists override it (ayný þeyleri yazacak olsa bile)
		PrintWriter custOutput = createFile(
				"/Users/Sinan/Documents/GitHub/Derek-Banas_Java-General/Tutorial 32 - Read & Write Files/src/random.txt");
		
		// Enhanced for loop for arrays
		// Cycles through all of the people in the customers array
		
		for(Customer person : customers){
			
			createCustomers(person, custOutput);
//			we write on a file
		}
//		alt: createCustomers() a array pass edilip createCustomers() da for loop kullanýlabilirdi:
//		createCustomers(customers, custOutput); 
//		-> createCustomers(Customer[] customers, PrintWriter custOutput);

		
		// Closes the connection to the PrintWriter
		
		custOutput.close();
		
		getFileInfo();
//		we print on console
	}
	
	// inner class that defines all the fields for my customers
//	You mark it static only so you don't have to create an instance 
//	of the outer class to access this class
	private static class Customer{
//		private Customer class, its variables and method are accessible 
//		within the outer Lesson32 class
		
		private String firstName, lastName;
		private int custAge;
		
		// constructor that's called when a customer is made
		
		private Customer(String firstName, String lastName, int custAge){
			
			this.firstName = firstName;
			this.lastName = lastName;
			this.custAge = custAge;
			
		}

	}
	
	// Creates an array of Customer Objects
	
	private static Customer[] getCustomers(){
		
		Customer[] customers = new Customer[5];
//		alt: outer class ismi yazmak gereksiz (outer class içinde olduðumuzdan)
//		Lesson32.Customer[] customers=new Lesson32.Customer[5];
		
//		her bir customer object bir þablondur
		customers[0] = new Customer("John", "Smith", 21);
		customers[1] = new Customer("Sally", "Smith", 30);
		customers[2] = new Customer("Paul", "Ryan", 21);
		customers[3] = new Customer("Mark", "Jacobs", 21);
		customers[4] = new Customer("Steve", "Nash", 21);

//		alt:
//		Customer cust0 = new Customer("John", "Smith", 21);
//		customers[0] = cust0;
		
//		inner Customer class static olmasaydý:
//		customers[0] = new Lesson32().new Customer("John", "Smith", 21);
//		customers[1] = new Lesson32().new Customer("Sally", "Smith", 30);
//		customers[2] = new Lesson32().new Customer("Paul", "Ryan", 21);
//		customers[3] = new Lesson32().new Customer("Mark", "Jacobs", 21);
//		customers[4] = new Lesson32().new Customer("Steve", "Nash", 21);
		
//		ayrýca alt olarak:
//		Customer[] customers = new Customer[5];
//		yerine
//		Lesson32.Customer[] customers = new Customer[5];
//		yazýlabilirdi.
		
		return customers;
		
	}
	
	// Create the file and the PrintWriter that will write to the file
//	type: PrintWriter, it will allow us to write to a file
//	fileName: location of the text file
	private static PrintWriter createFile(String fileName){
		
		try{
			
			// Creates a File object that allows you to work with files on the hardrive
			
			File listOfNames = new File(fileName);
			
			// FileWriter is used to write streams of characters to a file
			// BufferedWriter gathers a bunch of characters and then writes
			// them all at one time (Speeds up the Program)
			// PrintWriter is used to write characters to the console, file etc
	
			PrintWriter infoToWrite = new PrintWriter(
			new BufferedWriter(
					new FileWriter(listOfNames)));
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
	
	private static void createCustomers(Customer customer, PrintWriter custOutput){
		
		// Create the String that contains the customer info
		
		String custInfo = customer.firstName + " " + customer.lastName + " ";
		custInfo += Integer.toString(customer.custAge);
		
		// Writes the string to the file (not console)
		custOutput.println(custInfo);
		
//		writes to the console 
//		(normally not necessary since we can read what we write opening the file):
		System.out.println(custInfo);
		
	}
	
	
	
	// Read info from the file and write it to the screen (console)
	
	private static void getFileInfo(){
		
		BufferedReader getInfo = null;
		
		System.out.println("\nInfo Written to File\n");
		
		// Open a new connection to the file
		
		File listOfNames = new File("/Users/Sinan/Documents/GitHub/Derek-Banas_Java-General/Tutorial 32 - Read & Write Files/src/random.txt");
		
		try {
			
			// FileReader reads character files
			// BufferedReader reads as many characters as possible
			
			getInfo = new BufferedReader(
					new FileReader(listOfNames));
			
			// Reads a whole line from the file and saves it in a String
			
			String custInfo = getInfo.readLine();
			
			
			// readLine returns null when the end of the file is reached
//			readLine() reads one line at a time and whenever we reach the end of the file
//			the last thing sent to the readLine() becomes null
			while(custInfo != null){ // keep reading until reaching null
				
				// Break lines into pieces
				
				String[] indivCustData = custInfo.split(" ");
				
				// Convert the String into an integer with parseInt
				
				int custAge = Integer.parseInt(indivCustData[2]);
				
				System.out.print("Customer " + indivCustData[0] + " is " + custAge +"\n");
				
				custInfo = getInfo.readLine();
				// go to next line
				
				
//				alt: basic version
				
//				System.out.println(custInfo);

//				custInfo = getInfo.readLine(); 
				
			}
			
			
			
		} 
		
		// Can be thrown by FileReader
		
		catch (FileNotFoundException e) {
			
			System.out.println("Couldn't Find the File");
			System.exit(0);
		}
		
		catch(IOException e){
			
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		
		}
		
		finally
		{
			try {
				getInfo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
/*
John Smith 21
Sally Smith 30
Paul Ryan 21
Mark Jacobs 21
Steve Nash 21

Info Written to File

Customer John is 21
Customer Sally is 30
Customer Paul is 21
Customer Mark is 21
Customer Steve is 21
*/