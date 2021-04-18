package mbe.common;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

/**
 * @description: data structure for bipartite graph.
 * BipartiteGraph has been used by Flink Gelly, in case I include this library.
 * Because vertex num is fixed, so I use a trick to indicate left vertices set and right vertices set.
 * seq: [1, 2, 3, ..., verticesNum*], V means type of Vertex id
 * @className: CustomizedBipartiteGraph
 * @author: Jiri Yu
 * @date: 2021/4/10
 */
public class CustomizedBipartiteGraph {
    private final long numVerticesL;
    private final long numVerticesR;

    private final Set<Vertex> verticesL;
    private final Set<Vertex> verticesR;

    private Graph<Vertex, Edge> graph;

    public CustomizedBipartiteGraph(long numVerticesL,
                                    long numVerticesR) {
        this.numVerticesL = numVerticesL;
        this.numVerticesR = numVerticesR;
        this.verticesL = new HashSet<>();
        this.verticesR = new HashSet<>();
        this.graph = new SimpleGraph<>(Edge.class);
    }

    public long getNumVerticesL() {
        return numVerticesL;
    }

    public long getNumVerticesR() {
        return numVerticesR;
    }

    public Set<Vertex> getVerticesL() {
        return verticesL;
    }

    public Set<Vertex> getVerticesR() {
        return verticesR;
    }

    public boolean insertVertex(Vertex vertex) {
        if (vertex.getPartition() == Partition.LEFT) {
            verticesL.add(vertex);
        } else {
            verticesR.add(vertex);
        }
        return graph.addVertex(vertex);
    }

    public Set<Edge> getEdges() {
        return graph.edgeSet();
    }

    public Set<Vertex> getVertices() {
        return graph.vertexSet();
    }

    /*
     * @description: I recommend to use insertVertex instead of insertAllVertices.
     *
     * @param vertices
     * @return void
     * @author Jiri Yu
     */
    @Deprecated
    public void insertAllVertices(Vertex[] vertices) {
        for (int i = 0; i < vertices.length; i++) {
            insertVertex(vertices[i]);
        }
    }

    /*
     * @description: In this method, we assume all the vertex is legal.
     * That is, the edge's two end points are already in the graph.
     *
     * @param edge
     * @return boolean
     * @author Jiri Yu
     */
    public boolean insertEdge(Edge edge) {
        if (graph.containsEdge(edge)) {
            return false;
        }
        return graph.addEdge(edge.getX(), edge.getY(), edge);
    }

    /*
     * @description: I recommend to use insertEdge instead of insertAllEdges.
     *
     * @param edges
     * @return void
     * @author Jiri Yu
     */
    @Deprecated
    public void insertAllEdges(Edge[] edges) {
        for (int i = 0; i < edges.length; i++) {
            insertEdge(edges[i]);
        }
    }

    /*
     * @description: utils function, for get a vertex's adjacent vertices. It is useful in MineLMBC algorithm.
     *
     * @param v
     * @return java.util.Set<mbe.common.Vertex<V>>
     * @author Jiri Yu
     */
    public Set<Vertex> getAdjacentVertices(Vertex v) {
        return Graphs.neighborSetOf(graph, v);
    }

    /*
     * @description: utils function, for get vertices' adjacent vertices. It is useful in MineLMBC algorithm.
     * Pay attention, gamma(X) returns vertices adjacent to all vertices in set X.
     * We can get gamma(X) == Intersect(gamma(x)), where x belongs to X.
     *
     * @param v
     * @return java.util.Set<mbe.common.Vertex>
     * @author Jiri Yu
     */
    public Set<Vertex> getAdjacentVertices(Set<Vertex> vertices) {
        Set<Vertex> adjacencies = new TreeSet<>();

        if (vertices.isEmpty()) {
            return adjacencies;
        }

        Iterator<Vertex> iterator = vertices.iterator();
        adjacencies.addAll(Graphs.neighborSetOf(graph, iterator.next()));
        while (iterator.hasNext()) {
            Vertex vertex = iterator.next();
            adjacencies = getIntersectSet(adjacencies, Graphs.neighborSetOf(graph, vertex));
        }
        return adjacencies;
    }

    /*
     * @description: utils function, for get intersect set between set A and set B.
     *
     * @param A
     * @param B
     * @return java.util.Set<mbe.common.Vertex>
     * @author Jiri Yu
     */
    public Set<Vertex> getIntersectSet(Set<Vertex> A, Set<Vertex> B) {
        Set<Vertex> intersectSet = new HashSet<>();
        if (A.size() > B.size()) {
            Iterator<Vertex> iterator = A.iterator();
            while (iterator.hasNext()) {
                Vertex vertex = iterator.next();
                if (B.contains(vertex)) {
                    intersectSet.add(vertex);
                }
            }
        } else {
            Iterator<Vertex> iterator = B.iterator();
            while (iterator.hasNext()) {
                Vertex vertex = iterator.next();
                if (A.contains(vertex)) {
                    intersectSet.add(vertex);
                }
            }
        }
        return intersectSet;
    }

    /*
     * @description: this function for gammaX Union V, for quick use in MineLMBC.
     *
     * @param gammaX, adjacent vertices of vertices set X.
     * @param v, vertex
     * @return java.util.Set<mbe.common.Vertex>
     * @author Jiri Yu
     */
    public Set<Vertex> getAdjacentVerticesAndIntersect(Set<Vertex> gammaX, Vertex v) {
        Set<Vertex> vertices = Graphs.neighborSetOf(graph, v);
        return getIntersectSet(gammaX, vertices);
    }

    @Override
    public String toString() {
        // Most of the time graph.toString() is confusing.
        // I shall draw a graph that will be more clear.
        return graph.toString();
    }
}
