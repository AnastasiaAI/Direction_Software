package make;

/* You MAY add public @Test methods to this class.  You may also add
 * additional public classes containing "UnitTest" in their name. These
 * may not be part of your make package per se (that is, it must be
 * possible to remove them and still have your package work). */

import org.junit.Test;
import ucb.junit.textui;
import static org.junit.Assert.*;

/** Unit tests for the make package. */
public class UnitTest {

    /** Run all JUnit tests in the make package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(make.UnitTest.class));
    }

    @Test
    public void correctCurrentTime() {
        Maker maker = new Maker();
        System.out.println("XXXXXX" + System.getProperty("user.dir"));
        maker.readFileAges("make01.dir");
        maker.readMakefile("make01.mk");
        assertEquals(100, maker.getCurrentTime());
    }

    @Test
    public void correctInitialAge() {
        Maker maker = new Maker();
        maker.readFileAges("make01.dir");
        maker.readMakefile("make01.mk");
        assertEquals(90, (int) maker.getInitialAge("foo"));
        assertEquals(50, (int) maker.getInitialAge("foo.y"));
        assertEquals(10, (int) maker.getInitialAge("foo.h"));
    }

    @Test
    public void correctVertices() {
        Maker maker = new Maker();
        maker.readFileAges("make01.dir");
        maker.readMakefile("make01.mk");
        Depends graph = maker.getGraph();
        assertEquals(5, graph.vertexSize());
    }

    @Test
    public void correctEdges() {
        Maker maker = new Maker();
        maker.readFileAges("make01.dir");
        maker.readMakefile("make01.mk");
        Depends graph = maker.getGraph();
        assertEquals(4, graph.edgeSize());
    }

}
