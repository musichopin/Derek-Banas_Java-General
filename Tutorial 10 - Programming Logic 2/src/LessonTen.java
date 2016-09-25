import org.apache.commons.lang3.ArrayUtils;

// Basic class definition
// public means this class can be used by other classes
// Class names should begin with a capital letter
// A file can't contain two public classes. It can contain classes that are not public
// If you place class files in the same folder the java compiler will be able to find them

/* The Goal of this tutorial is to make a game like this
------------------------------
|*||*||*||*||*||*||*||*||*||*|
|*||*||*||*||*||*||*||*||*||*|
|*||*||*||*||*||*||*||*||*||*|
|*||M||F||*||*||*||*||*||*||*|
|*||*||*||*||*||*||*||*||*||*|
|*||*||*||*||*||*||*||*||*||*|
|*||*||*||*||*||*||*||*||*||*|
|P||*||*||*||*||*||*||*||*||*|
|*||*||*||*||D||*||*||*||*||*|
|*||*||*||*||*||*||*||*||*||*|
------------------------------
[9,9]

 */

public class LessonTen {
	
	public static void main(String[] args)
	{
//		1. board created and stars added onto the board
		MonsterTwo.buildBattleBoard();
//		buildBattleBoard() and redrawBoard() static because
//		building the battle bored isn’t something a monster would be able to do.
//		It is a utility method.
		
		// char[][] tempBattleBoard = new char[10][10];
		
		// ObjectName[] ArrayName(ArrayRefVar) = new ObjectName[4];
//		2. monsters added to the board and they overridden some stars
		MonsterTwo[] Monsters = new MonsterTwo[4];
//		MonsterTwo[] Monsters creates an array of MonsterTwo objects
//		It is an array filled with MonsterTwo objects
		
		// MonsterTwo(int health, int attack, int max_movement, String name)
//		we create the array manually (without loop)
		Monsters[0] = new MonsterTwo(1000, 20, 1, "Frank");
		Monsters[1] = new MonsterTwo(500, 40, 2, "Drac");
		Monsters[2] = new MonsterTwo(1000, 20, 1, "Paul");
		Monsters[3] = new MonsterTwo(1000, 20, 1, "George");
		
//		3. board printed out
		MonsterTwo.redrawBoard();
		
//		4. monsters moved on the board, they didn't fall off the board 
//		(board dýþýna taþarsa initial pozisyon alýnýr) or
//		overriden other monsters (monster override olursa loop tekrarlanýr). 
//		they overriden some new stars and 
//		old starts they had overridden reappeared on the board.
	for (MonsterTwo m : Monsters)
//		we modify/access array elements (objects) with for-each loop
	{
		
//		***m ile Monsters[0]'dan Monsters[3]'e kadarki ref var'lar (objectler) niteleniyor***
		if(m.getAlive())
		{
//			get index of current monster (not its position on board)
			int arrayItemIndex = ArrayUtils.indexOf(Monsters, m);
//			Monsters: array name, m: current monster
			m.moveMonster(Monsters, arrayItemIndex);
//			***m ile Monsters[0]'dan Monsters[3]'e kadarki ref var'lar niteleniyor***
		}
		
	}
//	5. board printed out
	MonsterTwo.redrawBoard();
	
	
	}
}