package HF_RandomGenerator;

import java.util.Random;

public class RandomGenerator {
	public static Random rng;
	public static long seed_;
	
	public static int count = 0;
	/*public RandomGenerator(){
		super();
	}*/
	
	public RandomGenerator(long seed){
		rng = new Random(seed);
		seed_ = seed;
	}
	 
	private static void incrementCount(){
		count++;
	}
	
	public static int getCount(){
		return count;
	}
	
	public static Random getRandomGenerator(){
	//	incrementCount();
		
	//	System.out.println("Use my random generator: "+ getCount() + " times.");
		
		return rng;
	}
	public Random resetRandomGenerator(long seed){
		 rng.setSeed(seed);
		 seed_ = seed;
		 return rng;
	}
}
