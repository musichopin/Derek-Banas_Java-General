import java.io.*;

import javax.swing.*;


public class Lesson31 extends JFrame{
	
	static String filePath,parentDirectory;
	
	static File randomDir, randomFile, randomFile2;
	
	public static void main(String[] args){
		
		// Creates a File object in memory (creates in memory)
		
		randomDir = new File("/Users/Sinan/Desktop/random");
		
		// Make a directory (creates in hard-drive)
		
		randomDir.mkdir();
		
		// Make a file named random.txt (creates in memory)
		
		randomFile = new File("random.txt");
		
		// Make a file and define where to save it in the file system
		
		randomFile2 = new File("/Users/Sinan/Desktop/random/random2.txt");
		
		// createNewFile and getCanonicalPath have to be called in 
		// a try block to catch IOException
		
		try {
			
			// createNewFile creates the file in the file system (creates in hard-drive)
			
			randomFile.createNewFile();
			
			randomFile2.createNewFile();
			
			// Returns the path for the file
			
			filePath = randomFile2.getCanonicalPath();
			
		} catch (IOException e) {
			
			// You have to catch the IOException
			e.printStackTrace();
			
		}
		
		// Check to see if the file exists in the current directory
		
		if (randomFile.exists()){
			
			System.out.println("File Exists");
			System.out.println("File is readable: " + randomFile.canRead());
			System.out.println("File is writable: " + randomFile.canWrite());
			System.out.println("File location: " + filePath);
			System.out.println("File name: " + randomFile.getName());
			
			// Since you created the file without defining a path this returns null
			
			System.out.println("File One Parent directory: " + randomFile.getParent());
			
			// This returns the parent because it was defined
			
			parentDirectory = randomFile2.getParent();
			
			System.out.println("File Two Parent Directory: " + parentDirectory);
			
			System.out.println("Is this a directory: " + randomDir.isDirectory());
			
			System.out.println("Is this a directory too: " + randomFile.isDirectory());
			
			
			System.out.println("\nFiles in Random Directory:");
			
			// list provides a string array containing all the files
			
			String[] filesInDir = randomDir.list();
			
			// Use the enhanced for loop to cycle through the files
			
			for(String fileName : filesInDir){
				System.out.println(fileName + "\n");
			}
			
			
			System.out.println("Is this a file: " + randomFile.isFile());
			
			System.out.println("Is this hidden: " + randomFile.isHidden());
			
			// Milliseconds since Jan 1, 1970 when modified
//			we can change the date to sth that makes sense to us
			System.out.println("Last modified: " + randomFile.lastModified());
			
			// Return size of file
			
			System.out.println("Size of file: " + randomFile.length());
			
			// Changes the name of the file
			
			randomFile2.renameTo(new File(randomDir + "/random3.txt"));
			
			// Output the full path for the file unless the path wasn't
			// defined when the File was created
			
			System.out.println("New Name: " + randomFile2.toString());
			

			new Lesson31();
			
		} else {
			
			System.out.println("File Doesn't Exist");
			
		}
		
		
		
		// You call delete to delete a file
//		randomFile eclipse içinde
		if(randomFile.delete()){
			System.out.println("File Deleted");
		}
		
		
		
		// I could get an array of File objects from the directory
//		randomDir desktopta
		File[] filesInDir = randomDir.listFiles();
		
		for(File fileName : filesInDir){
			fileName.delete();
		}
		
		// You can only delete a directory if it is empty
		
		if(randomDir.delete()){
			System.out.println("Directory Deleted");
		}
		
		
	}
	
	
	public Lesson31(){
		
		// Creates a file chooser at the location specified
//		(that is why we extended jframe)
		JFileChooser fileChooser = new JFileChooser(randomDir);
		
		// Opens the file chooser
		
		fileChooser.showOpenDialog(this);
		
		
	}
	
}

/*
File Exists
File is readable: true
File is writable: true
File location: C:\Users\Sinan\Desktop\random\random2.txt
File name: random.txt
File One Parent directory: null
File Two Parent Directory: \Users\Sinan\Desktop\random
Is this a directory: true
Is this a directory too: false

Files in Random Directory:
random2.txt

Is this a file: true
Is this hidden: false
Last modified: 1470838557824
Size of file: 0
New Name: \Users\Sinan\Desktop\random\random2.txt
File Deleted
Directory Deleted
*/