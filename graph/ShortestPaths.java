package graph;

/* See restrictions in Graph.java. */

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author A.
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        closedSet = new TreeSet<Integer>();
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        closedSet.clear();
        Iteration<Integer> allVertices = _G.vertices();
        while (allVertices.hasNext()) {
            int curr = allVertices.next();
            setWeight(curr, Double.POSITIVE_INFINITY);
            setPredecessor(curr, 0);
        }
        double[] fscore = new double[_G.vertexSize() + 1];
        for (int i = 0; i < fscore.length; i++) {
            fscore[i] = Double.POSITIVE_INFINITY;
        }
        List<Integer> fringe = new LinkedList<Integer>();
        fringe.add(_source);
        setWeight(_source, 0);
        fscore[_source] = estimatedDistance(v);
        while (!fringe.isEmpty()) {
            double minScore = fscore[fringe.get(0)];
            int lowestVertexIndex = 0;
            for (int i = 1; i < fringe.size(); i++) {
                if (fscore[fringe.get(i)] < minScore) {
                    minScore = fscore[fringe.get(i)];
                    lowestVertexIndex = i;
                }
            }
            int lowestVertex = fringe.get(lowestVertexIndex);
            closedSet.add(fringe.get(lowestVertexIndex));
            if (fringe.get(lowestVertexIndex) == v) {
                break;
            }
            fringe.remove(lowestVertexIndex);
            Iteration<Integer> succs = _G.successors(lowestVertex);
            while (succs.hasNext()) {
                int succ = succs.next();
                if (!closedSet.contains(succ)) {
                    double tScore = getWeight(lowestVertex)
                            + getWeight(lowestVertex, succ);
                    if (!fringe.contains(succ)) {
                        fringe.add(succ);
                        setPredecessor(succ, lowestVertex);
                        setWeight(succ, tScore);
                        fscore[succ] = getWeight(succ)
                                + estimatedDistance(succ);
                    } else {
                        if (tScore < getWeight(succ)) {
                            setPredecessor(succ, lowestVertex);
                            setWeight(succ, tScore);
                            fscore[succ] = getWeight(succ)
                                    + estimatedDistance(succ);
                        }
                    }
                }
            }
        }
        List<Integer> bestPath = new LinkedList<Integer>();
        for (int curr = v; curr != 0; curr = getPredecessor(curr)) {
            bestPath.add(0, curr);
        }
        return bestPath;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        if (getDest() == 0) {
            throw new IllegalStateException("unset destination vertex");
        }
        return pathTo(getDest());
    }


    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** The closed set. */
    private Set<Integer> closedSet;
}
