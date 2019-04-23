package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.display.ConsoleVisualiser;
import kalah.engine.GameEngine;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	private static final int NUM_PITS = 6;
	private static final int NUM_SEEDS = 4;

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		GameEngine engine = new GameEngine(new ConsoleVisualiser(io), NUM_PITS, NUM_SEEDS);
		engine.initialise();
		engine.play();
	}
}
