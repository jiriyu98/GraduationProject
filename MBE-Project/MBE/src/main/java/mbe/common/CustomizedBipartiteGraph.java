package mbe.common;

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
public class CustomizedBipartiteGraph<V> {
    private final long XVerticesNum;
    private final long YVerticesNum;
    private Set<Edge<V>> edges;
    private Set<Biclique<Vertex<V>>> bicliques; // maximal bicliques

    public CustomizedBipartiteGraph(long XVerticesNum,
                                    long YVerticesNum) {
        edges = new TreeSet<>();
        bicliques = new TreeSet<>();
        this.XVerticesNum = XVerticesNum;
        this.YVerticesNum = YVerticesNum;
    }

    public boolean insertEdge(Edge<V> edge){
        return edges.add(edge);
    }

    public boolean insertBicliques(Biclique<Vertex<V>> biclique){
        return bicliques.add(biclique);
    }

    public boolean insertBicliques(Set<Biclique<Vertex<V>>> bicliques){
        return bicliques.addAll(bicliques);
    }

    public boolean deleteBicliques(Biclique<Vertex<V>> biclique){
        return bicliques.remove(biclique);
    }

    public boolean deleteBicliques(Set<Biclique<Vertex<V>>> bicliques){
        return bicliques.removeAll(bicliques);
    }

    public long getXVerticesNum(){
        return XVerticesNum;
    }

    public long getYVerticesNum(){
        return YVerticesNum;
    }

    /*
     * @Description: utils function, for get a vertex's adjacent vertices.
     *
     * @param v	
     * @return java.util.Set<mbe.common.Vertex<V>>
     * @author Jiri Yu
     */
    public Set<Vertex<V>> getAdjacentVertices(Vertex<V> v){
        Set<Vertex<V>> adjacency = new TreeSet<>();

    }
}
