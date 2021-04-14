package mbe.common;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Description: data structure for bipartite graph.
 * BipartiteGraph has been used by Flink Gelly, in case I include this library.
 * seq: [1, 2, 3, ..., verticesNum*], *V* means type of Vertex id
 *
 * @ClassName: CustomizedBipartiteGraph
 * @author: Jiri Yu
 * @date: 2021/4/10 
 */
public class CustomizedBipartiteGraph {
    private final long XVerticesNum;
    private final long YVerticesNum;

    private Graph<Vertex, Edge> graph;

    public CustomizedBipartiteGraph(long xVerticesNum,
                                    long yVerticesNum){
        XVerticesNum = xVerticesNum;
        YVerticesNum = yVerticesNum;
        graph = new SimpleGraph<>(Edge.class);
    }

    public long getXVerticesNum() {
        return XVerticesNum;
    }

    public long getYVerticesNum() {
        return YVerticesNum;
    }

    public Edge insertEdge(Vertex v1, Vertex v2){
        graph.addVertex(v1);
        graph.addVertex(v2);
        return graph.addEdge(v1, v2);
    }

    /*
     * @Description: utils function, for get a vertex's adjacent vertices. It is useful in MineLMBC algorithm.
     *
     * @param v
     * @return java.util.Set<mbe.common.Vertex<V>>
     * @author Jiri Yu
     */
    public Set<Vertex> getAdjacentVertices(Vertex v){
        return Graphs.neighborSetOf(graph, v);
    }

    /*
     * @Description: utils function, for get vertices' adjacent vertices. It is useful in MineLMBC algorithm.
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
     * @Description: this function for gammaX Union V, for quick use in MineLMBC.
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
}
