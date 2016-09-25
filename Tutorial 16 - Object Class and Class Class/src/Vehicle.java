public class Vehicle extends Crashable implements Drivable, Cloneable{
	
	int numOfWheels = 2;
	double theSpeed = 0;
	
	int carStrength = 0;
	
	public int getWheels(){
		return this.numOfWheels;
	}
	
	public void setWheels(int numWheels){
		this.numOfWheels = numWheels;
	}
	
	public double getSpeed(){
		return this.theSpeed;
	}
	
	public void setSpeed(double speed){
		this.theSpeed = speed;
	}
	
	public Vehicle(){
		
	}
	
	public Vehicle(int wheels, double speed){
		this.numOfWheels = wheels;
		this.theSpeed = speed;
	}
	
	public void setCarStrength(int carStrength){
		this.carStrength = carStrength;
	}
	
	public int getCarStrength(){
		return this.carStrength;
	}
	
	public String toString(){
		return "Num of Wheels: " + this.numOfWheels;
	}
	
	public Object clone(){
		Vehicle car;
		
		try{
			car = (Vehicle) super.clone();
			// clone un return ettiðini Vehicle a cast ediyoruz
		}
//		alt:
//		Object car;
//		
//		try{
//			car = super.clone();
//	// 	alt: car = (Vehicle) super.clone();
//		}
		
		
		catch(CloneNotSupportedException e){
			return null;
		}
		return car;
//		***clone method returns of type object even though we wrote "Vehicle car"
//		(since Vehicle implicitly extends object class it is legal)***
	}
	
}