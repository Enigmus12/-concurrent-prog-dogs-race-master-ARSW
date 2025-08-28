package arsw.threads;

import java.util.Random;

/**
 * Generador de números aleatorios.
 * 
 * @author Juan David Rodríguez
 *
 */

public class RandomGenerator {

	private static Random random=new Random(System.currentTimeMillis());
	
	public static int nextInt(int max){
		return random.nextInt(max);
	}
	
}
