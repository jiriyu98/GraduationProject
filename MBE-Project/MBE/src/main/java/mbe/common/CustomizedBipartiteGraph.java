package mbe.common;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @description: data structure for bipartite graph.
 * BipartiteGraph has been used by Flink Gelly, in case I include this library.
 * seq: [1, 2, 3, ..., verticesNum*], *V* means type of Vertex id
 *
 * @className: CustomizedBipartiteGraph
 * @author: Jiri Yu
 * @date: 2021/4/10 
 */
public class CustomizedBipartiteGraph {
    private final long numVerticesL;
    private final long numVerticesR;

    private Graph<Vertex, Edge> graph;

    public CustomizedBipartiteGraph(long numVerticesL,
                                    long numVerticesR){
        this.numVerticesL = numVerticesL;
        this.numVerticesR = numVerticesR;
        graph = new SimpleGraph<>(Edge.class);
    }

    public long getNumVerticesL() {
        return numVerticesL;
    }

    public long getNumVerticesR() {
        return numVerticesR;
    }

    public boolean insertVertex(Vertex vertex){
        return graph.addVertex(vertex);
    }

    /*
     * @description: I recommend to use insertVertex instead of insertAllVertices.
     *
     * @param vertices
     * @return void
     * @author Jiri Yu
     */
    @Deprecated
    public void insertAllVertices(Vertex[] vertices){
        for (int i = 0; i < vertices.length; i++) {
            graph.addVertex(vertices[i]);
        }
    }

    public boolean insertEdge(Edge edge){
        graph.addVertex(edge.getX());
        graph.addVertex(edge.getY());
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
    public void insertAllEdges(Edge[] edges){
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
    public Set<Vertex> getAdjacentVertices(Vertex v){
        return Graphs.neighborSetOf(graph, v);
    }

    /*
     * @description: utils function, for get vertices' adjacent vertices. It is useful in MineLMBC algorithm.
     *
     * @param v	
     * @return java.util.Set<mbe.common.Vertex>
     * @author Jiri Yu
     */
    public Set<Vertex> getAdjacentVertices(Set<Vertex> vs){
        Set<Vertex> adjacencies = new TreeSet<>();
        Iterator<Vertex> iterator = vs.iterator();

        while(iterator.hasNext()){
            Vertex vertex = iterator.next();
            adjacencies.addAll(Graphs.neighborSetOf(graph, vertex));
        }
        return adjacencies;
    }

    /*
     * @description: this function for gammaX Union V, for quick use in MineLMBC.
     *
     * @param gammaX, adjacent vertices of vertices set X.
     * @param v, vertex
     * @return java.util.Set<mbe.common.Vertex>
     * @author Jiri Yu
     */
    public Set<Vertex> getAdjacentVerticesAndIntersect(Set<Vertex> gammaX, Vertex v){
        Set<Vertex> vertices = Graphs.neighborSetOf(graph, v);
        Set<Vertex> adjacentVertices = new HashSet<>();
        if(gammaX.size() > vertices.size()){
            Iterator<Vertex> iterator = gammaX.iterator();
            while(iterator.hasNext()){
                Vertex vertex = iterator.next();
                if(vertices.contains(vertex)){
                    adjacentVertices.add(vertex);
                }
            }
        }else{
            Iterator<Vertex> iterator = vertices.iterator();
            while(iterator.hasNext()){
                Vertex vertex = iterator.next();
                if(gammaX.contains(vertex)){
                    adjacentVertices.add(vertex);
                }
            }
        }
        return adjacentVertices;
    }

    @Override
    public String toString(){
        // Most of the time graph.toString() is confusing.
        // I draw a graph that will be more clear.
        return graph.toString();
    }
}
