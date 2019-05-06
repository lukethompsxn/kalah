package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.engine.GameEngine;
import kalah.io.ConsoleManager;
import kalah.engine.KalahEngine;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {

    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        GameEngine engine = new KalahEngine(new ConsoleManager(io));
        engine.initialise();
        engine.play();
    }
}
