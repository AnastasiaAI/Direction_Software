package graph;

/* See restrictions in Graph.java. */

import java.util.LinkedList;
import java.util.List;

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author A.
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {

        int result = 0;
        Iteration<int[]> allEdges = this.edges();
        while (allEdges.hasNext()) {
            int[] current = allEdges.next();
            if (current[1] == v) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {

        List<Integer> result = new LinkedList<Integer>();
        Iteration<int[]> allEdges = this.edges();
        while (allEdges.hasNext()) {
            int[] current = allEdges.next();
            if (current[1] == v) {
                result.add(current[0]);
            }
        }
        return Iteration.iteration(result.iterator());
    }
}
