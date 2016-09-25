import java.util.Arrays;

// Basic class definition
// public means this class can be used by other classes
// Class names should begin with a capital letter
// A file can't contain two public classes. It can contain classes that are not public
// If you place class files in the same folder the java compiler will be able to find them

public class MonsterTwo{
	
	static char[][] battleBoard = new char[10][10];
	
	public static void buildBattleBoard(){

//		char: cells, row: stars to be placed in cells, battleBoard: array itself
		for(char[] row : battleBoard)
		{
			
			Arrays.fill(row, '*');
			// we modify the array with for-each loop
			
		}
		
	}
	
//	// mantýksýz biçimi:
	
//static char[][] battleBoard = new char[10][21];
//	
//	public static void buildBattleBoard(){
//
////		char: cells, row: stars to be placed in cells, battleBoard: array itself
//		for(int i = 0; i < battleBoard.length; i++)
//		{
//			
//			for(int j = 1; j < battleBoard[i].length; j+=2)
//			{
//				
//				battleBoard[i][j] = '*';
//				
//			}
//			
//			for(int j = 0; j < battleBoard[i].length; j+=2)
//			{
//				
//				battleBoard[i][j] = '|';
//				
//			}
//			
//			
//		}
//		
//	}
	
	public static void redrawBoard()
	{
		
//		draw dashes
		int k = 1;
		while(k <= 31){ System.out.print('-'); k++; }
		System.out.println();
		
//		aþaðý doðru
		for (int i = 0; i < battleBoard.length; i++)
		{
			
//			saða doðru
			for(int j = 0; j < battleBoard[i].length; j++)
			{
				
				System.out.print("|" + battleBoard[i][j]);
				// we print the array with for loop

			}
			System.out.println("|");
			
		}
		k = 1;
		while(k <= 31){ System.out.print('-'); k++; }
		System.out.println();
		
		
	}
	
	// Class Variables or Fields
	// You declare constants with final
	
	public final String TOMBSTONE = "Here Lies a Dead monster";
	
	// private fields are not visible outside of the class
	private int health = 500;
	private int attack = 20;
	private int movement = 2;
	
	private boolean alive = true;
	
	// public variables are visible outside of the class
	// You should have as few as possible public fields
	public String name = "Big Monster";
	public char nameChar1 = 'B'; // default monster initial
	public int xPosition = 0;
	public int yPosition = 0;
	public static int numOfMonsters = 0;
	
	// Class Methods
	// Accessor Methods are used to get and set the values of private fields
	
	public int getAttack()
	{
		return attack;
	}
	
	public int getMovement()
	{
		return movement;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public boolean getAlive()
	{
		return alive;
	}
	
	// You can create multiple versions using the same method name
	// Now setHealth can except an attack that contains decimals
	// When overloading a method you can't just change the return type
	// Focus on creating methods that except different parameters
	
	public void setHealth(int decreaseHealth)
	{
		health = health - decreaseHealth;
		if (health < 0)
		{
			alive = false;
		}
	}
	
	public void setHealth(double decreaseHealth)
	{
		int intDecreaseHealth = (int) decreaseHealth;
		health = health - intDecreaseHealth;
		if (health < 0)
		{
			alive = false;
		}
	}
	
	/* The Constructor
	 * Code that is executed when an object is created from this class definition
	 * The method name is the same as the class
	 * The constructor is only executed once per object
	 * The constructor can't return a value
	 */
	
	public MonsterTwo(int health, int attack, int movement, String name)
	{
		this.health = health;
		this.attack = attack;
		this.movement = movement;
		this.name = name;
		/* If the attributes had the same names as the class health, attack, movement
		 * You could refer to the objects fields by proceeding them with this
		 * this.health = health;
		 * this.attack = attack;
		 * objectFieldName = attributeFieldName;
		 */
		
//		because of index 0 we subtract 1
//		normalde aþaðý doðru uzunluðu ölçer ama aþaðýda
//		battleBoard[this.yPosition][this.xPosition] yazýldýðýndan
//		arrayin x kýsmýný ölçüyor denir (semantic kayýp yerine definitive kayýp)
//		(battleBoard md array kare olmasaydý bu taktik iþe yaramayabilirdi)
		int maxXBoardSpace = battleBoard.length - 1;
//		***board kare olmasaydý aþaðýdaki gibi denmeliydi:***
//		int maxXBoardSpace = battleBoard.length[0] - 1;

		
//		normalde saða doðru uzunluðu ölçer ama aþaðýda
//		battleBoard[this.yPosition][this.xPosition] yazýldýðýndan
//		arrayin y kýsmýný ölçüyor denir
//		(battleBoard md array kare olmasaydý bu taktik iþe yaramayabilirdi)
		int maxYBoardSpace = battleBoard[0].length - 1;
//		***board kare olmasaydý aþaðýdaki gibi denmeliydi:***
//		int maxYBoardSpace = battleBoard.length - 1;
		
		// designates where the monster begins initially
		int randNumX, randNumY;
		
//		we use do-loop bc we always want to define a space
//		for our monster no matter what
		do {
			
			randNumX = (int) (Math.random() * (maxXBoardSpace+1)); // 9-0+1 (int olduðundan)
			randNumY = (int) (Math.random() * (maxYBoardSpace+1));
			
		} while(battleBoard[randNumY][randNumX] != '*');
//		loop sayesinde  harflerin birbirini override etme riski ortadan kalkýyor
		
		this.xPosition = randNumX;
		this.yPosition = randNumY;
		
		this.nameChar1 = name.charAt(0); // take the initial
		
		battleBoard[this.yPosition][this.xPosition] = this.nameChar1;
		
		numOfMonsters++;
		
		System.out.println("numOfMonsters: " + numOfMonsters);
	}
	
	// You can overload constructors like any other method
	// The following constructor is the one provided by default if you don't create any other constructors
	// Default Constructor
//	not used
	public MonsterTwo()
	{
		numOfMonsters++;
	}
	
	public static void main(String[] args)
	{
		
		
	}
	
}