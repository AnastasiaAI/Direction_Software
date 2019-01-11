package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author A.
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void addingOneVertex() {
        DirectedGraph g = new DirectedGraph();
        int vertexId = g.add();
        assertTrue("graph has vertex", g.contains(vertexId));
        assertEquals("number of vertices is one", 1, g.vertexSize());
        assertEquals("number of edges is zero", 0, g.edgeSize());
        assertEquals("maxvertex is one", 1, g.maxVertex());
        assertEquals("vertex id is one", 1, vertexId);
        assertEquals("indegree of vertexId is zero", 0, g.inDegree(vertexId));
        assertEquals("outdegree of vertexId is zero", 0, g.outDegree(vertexId));
    }

    @Test
    public void addingOneSelfEdge() {
        DirectedGraph g = new DirectedGraph();
        int vertexId = g.add();
        int edgeId = g.add(vertexId, vertexId);
        assertEquals("vertex id is one", 1, vertexId);
        assertTrue("graph has edge", g.contains(vertexId, vertexId));
        assertEquals("edge id is correctly retrieved",
                edgeId, g.edgeId(vertexId, vertexId));
        assertEquals("number of edges is one", 1, g.edgeSize());
        assertEquals("indegree of vertexId is one", 1, g.inDegree(vertexId));
        assertEquals("outdegree of vertexId is one", 1, g.outDegree(vertexId));
    }


    @Test
    public void graphThreeVerticesTwoEdges() {
        DirectedGraph g = new DirectedGraph();
        int vertexOne = g.add();
        int vertexTwo = g.add();
        int vertexThree = g.add();
        int edgeIdOne = g.add(vertexOne, vertexTwo);
        int edgeIdTwo = g.add(vertexTwo, vertexThree);
        assertEquals("vertexOne is one", 1, vertexOne);
        assertEquals("vertexTwo is two", 2, vertexTwo);
        assertEquals("vertexThree is three", 3, vertexThree);
        assertEquals("edgeIdOne is one", 1, edgeIdOne);
        assertEquals("edgeIdTwo is two", 2, edgeIdTwo);
        assertTrue("graph has edge one", g.contains(vertexOne, vertexTwo));
        assertFalse("graph has no back edge", g.contains(vertexTwo, vertexOne));
        assertTrue("graph has edge two", g.contains(vertexTwo, vertexThree));
        assertFalse("graph has no back edge",
                g.contains(vertexThree, vertexTwo));
        assertEquals("edge id is correctly retrieved",
                edgeIdOne, g.edgeId(vertexOne, vertexTwo));
        assertEquals("edge id is correctly retrieved",
                edgeIdTwo, g.edgeId(vertexTwo, vertexThree));
        assertEquals("number of edges is two",
                2, g.edgeSize());
        assertEquals("indegree of vertexOne is zero", 0, g.inDegree(vertexOne));
        assertEquals("outdegree of vertexOne is one",
                1, g.outDegree(vertexOne));
        assertEquals("indegree of vertexTwo is one", 1, g.inDegree(vertexTwo));
        assertEquals("outdegree of vertexTwo is one",
                1, g.outDegree(vertexTwo));
        assertEquals("indegree of vertexThree is one",
                1, g.inDegree(vertexThree));
        assertEquals("outdegree of vertexThre is zero",
                0, g.outDegree(vertexThree));
    }

    @Test
    public void createAndRemoveVertices() {
        DirectedGraph g = new DirectedGraph();
        int vertexOne = g.add();
        int vertexTwo = g.add();
        int vertexThree = g.add();
        int edgeIdOne = g.add(vertexOne, vertexTwo);
        int edgeIdTwo = g.add(vertexTwo, vertexThree);
        g.remove(vertexTwo);
        assertFalse("graph does not have edge one",
                g.contains(vertexOne, vertexTwo));
        assertFalse("graph does not have edge two",
                g.contains(vertexTwo, vertexThree));
        assertEquals("number of edges is zero",
                0, g.edgeSize());
        assertEquals("indegree of vertexOne is zero", 0, g.inDegree(vertexOne));
        assertEquals("outdegree of vertexOne is zero",
                0, g.outDegree(vertexOne));
        assertEquals("indegree of vertexThree is zero",
                0, g.inDegree(vertexThree));
        assertEquals("outdegree of vertexThre is zero",
                0, g.outDegree(vertexThree));
    }

    @Test
    public void createAndRemoveVerticesKeepEdges() {
        DirectedGraph g = new DirectedGraph();
        int vertexOne = g.add();
        int vertexTwo = g.add();
        int vertexThree = g.add();
        int edgeIdOne = g.add(vertexOne, vertexTwo);
        int edgeIdTwo = g.add(vertexTwo, vertexThree);
        g.remove(vertexOne);
        assertFalse("graph does not have edge one",
                g.contains(vertexOne, vertexTwo));
        assertTrue("graph does have edge two",
                g.contains(vertexTwo, vertexThree));
        assertEquals("number of edges is one", 1, g.edgeSize());
        assertEquals("indegree of vertexTwo is zero", 0, g.inDegree(vertexTwo));
        assertEquals("outdegree of vertexTwo is one",
                1, g.outDegree(vertexTwo));
        assertEquals("indegree of vertexThree is one",
                1, g.inDegree(vertexThree));
        assertEquals("outdegree of vertexThree is zero",
                0, g.outDegree(vertexThree));
    }


    @Test
    public void emptyUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        assertEquals("Initial graph has no vertices", 0, g.vertexSize());
        assertEquals("Initial graph has no edges", 0, g.edgeSize());
    }

    @Test
    public void addingOneVertexUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        int vertexId = g.add();
        assertTrue("graph has vertex", g.contains(vertexId));
        assertEquals("number of vertices is one", 1, g.vertexSize());
        assertEquals("number of edges is zero", 0, g.edgeSize());
        assertEquals("maxvertex is one", 1, g.maxVertex());
        assertEquals("vertex id is one", 1, vertexId);
        assertEquals("indegree of vertexId is zero", 0, g.inDegree(vertexId));
        assertEquals("outdegree of vertexId is zero", 0, g.outDegree(vertexId));
    }

    @Test
    public void addingOneSelfEdgeUndirectedGraph() {
        DirectedGraph g = new DirectedGraph();
        int vertexId = g.add();
        int edgeId = g.add(vertexId, vertexId);
        assertEquals("vertex id is one", 1, vertexId);
        assertTrue("graph has edge", g.contains(vertexId, vertexId));
        assertEquals("edge id is correctly retrieved",
                edgeId, g.edgeId(vertexId, vertexId));
        assertEquals("number of edges is one", 1, g.edgeSize());
        assertEquals("indegree of vertexId is one", 1, g.inDegree(vertexId));
        assertEquals("outdegree of vertexId is one", 1, g.outDegree(vertexId));
    }


    @Test
    public void graphThreeVerticesTwoEdgesUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        int vertexOne = g.add();
        int vertexTwo = g.add();
        int vertexThree = g.add();
        int edgeIdOne = g.add(vertexOne, vertexTwo);
        int edgeIdTwo = g.add(vertexTwo, vertexThree);
        assertEquals("vertexOne is one", 1, vertexOne);
        assertEquals("vertexTwo is two", 2, vertexTwo);
        assertEquals("vertexThree is three", 3, vertexThree);
        assertEquals("edgeIdOne is one", 1, edgeIdOne);
        assertEquals("edgeIdOne is two", 2, edgeIdTwo);
        assertTrue("graph has edge one", g.contains(vertexOne, vertexTwo));
        assertTrue("graph has back edge", g.contains(vertexTwo, vertexOne));
        assertTrue("graph has edge two", g.contains(vertexTwo, vertexThree));
        assertTrue("graph has back edge", g.contains(vertexThree, vertexTwo));
        assertEquals("edge id is correctly retrieved",
                edgeIdOne, g.edgeId(vertexOne, vertexTwo));
        assertEquals("edge id is correctly retrieved",
                edgeIdTwo, g.edgeId(vertexTwo, vertexThree));
        assertEquals("edge id is correctly retrieved",
                edgeIdOne, g.edgeId(vertexTwo, vertexOne));
        assertEquals("edge id is correctly retrieved",
                edgeIdTwo, g.edgeId(vertexThree, vertexTwo));
        assertEquals("number of edges is two", 2, g.edgeSize());
        assertEquals("indegree of vertexOne is one", 1, g.inDegree(vertexOne));
        assertEquals("outdegree of vertexOne is one",
                1, g.outDegree(vertexOne));
        assertEquals("indegree of vertexTwo is two", 2, g.inDegree(vertexTwo));
        assertEquals("outdegree of vertexTwo is two",
                2, g.outDegree(vertexTwo));
        assertEquals("indegree of vertexThree is one",
                1, g.inDegree(vertexThree));
        assertEquals("outdegree of vertexThree is one",
                1, g.outDegree(vertexThree));
    }

    @Test
    public void createAndRemoveVerticesUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        int vertexOne = g.add();
        int vertexTwo = g.add();
        int vertexThree = g.add();
        int edgeIdOne = g.add(vertexOne, vertexTwo);
        int edgeIdTwo = g.add(vertexTwo, vertexThree);
        g.remove(vertexTwo);
        assertFalse("graph does not have edge one",
                g.contains(vertexOne, vertexTwo));
        assertFalse("graph does not have edge two",
                g.contains(vertexTwo, vertexThree));
        assertEquals("number of edges is zero",
                0, g.edgeSize());
        assertEquals("indegree of vertexOne is zero", 0, g.inDegree(vertexOne));
        assertEquals("outdegree of vertexOne is zero",
                0, g.outDegree(vertexOne));
        assertEquals("indegree of vertexThree is zero",
                0, g.inDegree(vertexThree));
        assertEquals("outdegree of vertexThre is zero",
                0, g.outDegree(vertexThree));
    }

    @Test
    public void createAndRemoveVerticesKeepEdgesUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        int vertexOne = g.add();
        int vertexTwo = g.add();
        int vertexThree = g.add();
        int edgeIdOne = g.add(vertexOne, vertexTwo);
        int edgeIdTwo = g.add(vertexTwo, vertexThree);
        g.remove(vertexOne);
        assertFalse("graph does not have edge one",
                g.contains(vertexOne, vertexTwo));
        assertTrue("graph does have edge two",
                g.contains(vertexTwo, vertexThree));
        assertEquals("number of edges is one", 1, g.edgeSize());
        assertEquals("indegree of vertexTwo is one", 1, g.inDegree(vertexTwo));
        assertEquals("outdegree of vertexTwo is one", 1,
                g.outDegree(vertexTwo));
        assertEquals("indegree of vertexThree is one",
                1, g.inDegree(vertexThree));
        assertEquals("outdegree of vertexThree is one",
                1, g.outDegree(vertexThree));
    }
}
