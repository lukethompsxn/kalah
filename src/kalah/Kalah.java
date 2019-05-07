package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.engine.KalahEngine;
import kalah.io.ConsoleManager;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {

    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        KalahEngine engine = new KalahEngine(new ConsoleManager(io));
        engine.play();
    }
}
