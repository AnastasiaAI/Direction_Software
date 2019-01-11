package graph;

/* See restrictions in Graph.java. */

import java.util.LinkedList;
import java.util.List;

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author A.
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
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
