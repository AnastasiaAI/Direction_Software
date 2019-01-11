package graph;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author A.
 */
abstract class GraphObj extends Graph {

    /** Edge. */
    private class Edge {

        /** source. */
        private int source;

        /** target. */
        private int target;

        /** id of edge. */
        private int id;
    }

    /** A max vertex. */
    private int maxVertex;

    /** int id. */
    private int maxEdgeId;

    /** A new list of lists of edges @param adjacencyLists. */
    private List<List<Edge>> adjacencyLists;

    /** A new list of @param vertices. */
    private List<Integer> vertices;

    /** A new list of @param edges. */
    private List<Edge> edges;

    /** A new, empty Graph. */
    GraphObj() {
        adjacencyLists = new ArrayList<List<Edge>>();
        adjacencyLists.add(null);
        vertices = new ArrayList<Integer>();
        edges = new ArrayList<Edge>();
        maxVertex = 1;
        maxEdgeId = 1;

    }

    @Override
    public int vertexSize() {
        return vertices.size();
    }

    @Override
    public int maxVertex() {
        int max = 1;
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i) > max) {
                max = vertices.get(i);
            }
        }
        return max;
    }

    @Override
    public int edgeSize() {
        if (isDirected()) {
            return edges.size();
        } else {
            int selfEdges = 0;
            for (int i = 0; i < edges.size(); i++) {
                if (edges.get(i).source == edges.get(i).target) {
                    selfEdges++;
                }
            }
            return ((edges.size() + selfEdges) / 2);
        }
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        return adjacencyLists.get(v).size();
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i) == u) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        if (!contains(u) || !contains(v)) {
            return false;
        }
        List<Edge> edDges = adjacencyLists.get(u);
        if (edDges == null) {
            return false;
        }
        for (int i = 0; i < edDges.size(); i++) {
            Edge current = edDges.get(i);
            if (current.target == v) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int add() {
        boolean[] taken = new boolean[maxVertex];
        for (int i = 0; i < vertices.size(); i++) {
            int curr = vertices.get(i);
            taken[curr] = true;
        }
        int nextId = maxVertex;
        boolean found = false;
        for (int i = 1; i < taken.length && !found; i++) {
            if (!taken[i]) {
                nextId = i;
                found = true;
            }
        }
        vertices.add(nextId);
        List<Edge> adjacencyList = new LinkedList<Edge>();
        if (nextId == maxVertex) {
            adjacencyLists.add(adjacencyLists.size(), adjacencyList);
            maxVertex++;
        } else {
            adjacencyLists.remove(nextId);
            adjacencyLists.add(nextId, adjacencyList);
        }
        return (nextId);
    }

    @Override
    public int add(int u, int v) {
        if (!contains(u) || !contains(v)) {
            throw new IllegalArgumentException("invalid vertices");
        }
        int result = 0;
        if (contains(u, v)) {
            List<Edge> eDdges = adjacencyLists.get(u);
            for (int i = 0; i < eDdges.size(); i++) {
                Edge current = eDdges.get(i);
                if (current.target == v) {
                    result = current.id;
                }
            }
        } else {
            Edge edgeOne = new Edge();
            edgeOne.source = u;
            edgeOne.target = v;
            edgeOne.id = maxEdgeId;
            adjacencyLists.get(u).add(edgeOne);
            edges.add(edgeOne);
            if (!isDirected() && u != v) {
                Edge edgeTwo = new Edge();
                edgeTwo.source = v;
                edgeTwo.target = u;
                edgeTwo.id = maxEdgeId;
                adjacencyLists.get(v).add(edgeTwo);
                edges.add(edgeTwo);
            }
            result = maxEdgeId;
            maxEdgeId++;
        }
        return result;
    }

    @Override
    public void remove(int v) {
        if (contains(v)) {
            Iteration<Integer> allVertices = vertices();
            while (allVertices.hasNext()) {
                int current = allVertices.next();
                if (contains(v, current)) {
                    remove(v, current);
                }
                if (contains(current, v)) {
                    remove(current, v);
                }
            }
            vertices.remove(vertices.indexOf(v));
            int size = adjacencyLists.size() - 1;
            for (int i = size; !contains(i); i--) {
                adjacencyLists.remove(i);
                maxVertex--;
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        if (contains(u, v)) {
            List<Edge> adjList = adjacencyLists.get(u);
            for (int i = 0; i < adjList.size(); i++) {
                if (adjList.get(i).target == v) {
                    adjList.remove(i);
                }
            }
            for (int i = 0; i < edges.size(); i++) {
                Edge currEdge = edges.get(i);
                if (currEdge.source == u && currEdge.target == v) {
                    edges.remove(i);
                }
            }
            if (!isDirected()) {
                adjList = adjacencyLists.get(v);
                for (int i = 0; i < adjList.size(); i++) {
                    if (adjList.get(i).target == u) {
                        adjList.remove(i);
                    }
                }
                for (int i = 0; i < edges.size(); i++) {
                    Edge currEdge = edges.get(i);
                    if (currEdge.source == v && currEdge.target == u) {
                        edges.remove(i);
                    }
                }
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return Iteration.iteration(vertices.iterator());
    }

    @Override
    public Iteration<Integer> successors(int v) {

        List<Integer> successorS = new LinkedList<Integer>();
        List<Edge> adjacencyList = adjacencyLists.get(v);

        for (int i = 0; i < adjacencyList.size(); i++) {
            successorS.add(adjacencyList.get(i).target);
        }
        return Iteration.iteration(successorS.iterator());
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        List<int[]> result = new LinkedList<int[]>();
        for (int i = 0; i < edges.size(); i++) {
            Edge current = edges.get(i);
            int[] value = new int[2];
            value[0] = current.source;
            value[1] = current.target;
            result.add(value);
        }
        return Iteration.iteration(result.iterator());
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (!contains(u, v)) {
            throw new IllegalArgumentException("invalid edge");
        }
        List<Edge> edDges = adjacencyLists.get(u);
        for (int i = 0; i < edDges.size(); i++)  {
            Edge current = edDges.get(i);
            if (current.target == v) {
                return current.id;
            }
        }
        return -1;
    }


}
