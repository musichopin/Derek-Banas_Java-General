public class LessonSixteen{
	
	public static void main(String[] args){
		
		// Every object (not only object object) inherits all the methods of the Object class
//		***creates superCar as type Vehicle***
		Object superCar = new Vehicle();
//		*You'll normally not refer to objects as Object s in java. 
//		because none of the methods in Vehicle are in Object. 
//		You can't use a class polymorphically if a subclass doesn't 
//		contain methods in the super class*
//		downcasting:
//		Vehicle v = ((Vehicle)superCar);
		
		// superCar inherits all of the Object methods, but an object
		// of class Object can't access the Vehicle methods
		
		// System.out.println(superCar.getSpeed()); * Throws an error
		
		// You can cast from type Object to Vehicle to access those methods
		
		System.out.println(((Vehicle) superCar).getSpeed());
		
		// The methods of the Object class
		
		Vehicle superTruck = new Vehicle();
//		upcasting: 
//		Object o = ((Object)superTruck);
		
		// equals tells you if two objects are equal
		System.out.println(superCar.equals(superTruck)); // false
		
		// hashCode returns a unique identifier for an object
		System.out.println(superCar.hashCode());
		
		// finalize is called by the java garbage collector when an object
		// is no longer of use. If you call it there is no guarantee it will
		// do anything though
		
		// getClass returns the class of the object
//		***superCar ý cast etmeseydik de "class Vehicle" print edilecekti***
		System.out.println(superCar.getClass()); /* ***getClass belongs to OBJECT CLASS*** */
		
		// THE CLASS OBJECT
		// You can use the Class object method getName to get just the class name
//		superCar.getClass().getName() is getting the name of the class for the object superCar
		System.out.println(superCar.getClass().getName()); /* ***getName belongs to CLASS CLASS*** */
		
		// You can check if 2 objects are of the same class with getClass()
		
		if(superCar.getClass() == superTruck.getClass()){ // true
			System.out.println("They are in the same class");
		}
		
		// getSuperclass returns the super class of the class
		
		System.out.println(superCar.getClass().getSuperclass()); // *CLASS CLASS*
		
		// the toString method is often overwritten for an object
		
		System.out.println(superCar.toString()); // alt: superCar
		
		// toString is often used to convert primitives to strings
		
		int randNum = 100;
		System.out.println(Integer.toString(randNum));
		
		// THE CLONE METHOD
		// clone copies the current values of the object and assigns
		// them to another. If changes are made after the clone both
		// objects aren't effected though
		
		superTruck.setWheels(6);
		
		Vehicle superTruck2 = (Vehicle)superTruck.clone();
//		we cast it because superTruck.clone() returns an object of type object
		
		System.out.println(superTruck.getWheels());
		
		System.out.println(superTruck2.getWheels());
		
		// They are separate objects and don't have equal hashcodes
		System.out.println(superTruck.hashCode());
		System.out.println(superTruck2.hashCode());
		
		// There are subobjects defined in an object clone won't
		// also clone them. You'd have to do that manually, but this
		// topic will be covered in the future because of complexity
		
	}
	
}
/*
0.0
false
705927765
class Vehicle
Vehicle
They are in the same class
class Crashable
Num of Wheels: 2
100
6
6
366712642
1829164700
*/