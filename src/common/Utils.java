package common;

import java.util.Random;

public class Utils {
	public static Random random = new Random();
	
	public static int nextRandomInt(int lower, int upper) {
		return random.nextInt(upper-lower+1);
	}
}
