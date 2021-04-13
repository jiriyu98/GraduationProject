package mbe.algorithm;

import java.util.Iterator;
import java.util.Set;

/**
 * @Description: basic algorithm for static graph
 *
 * @ClassName: MineLMBC
 * @author: Jiri Yu
 * @date: 2021/4/12 
 */
public class MineLMBC<T> {
    private final CustomizedBipartiteGraph<T> customizedBipartiteGraph;

    public MineLMBC(CustomizedBipartiteGraph<T> customizedBipartiteGraph) {
        this.customizedBipartiteGraph = customizedBipartiteGraph;
    }

    /*
     * @Description: mineLMBC, an algorithm for static bipartite graph.
     *
     * @param X, is a vertex set
     * @param gammaX, is the adjacency list of X
     * @param tailX, is the tail vertices of X
     * @param ms, means minimum size threshold, and it will be assigned as an constant 1 in our algorithm.
     * @return void
     * @author Jiri Yu
     */
    public static <T> void mineLMBC(Set<Vertex<T>> X, Set<Vertex<T>> gammaX, Set<Vertex<T>> tailX, long ms){
        Iterator<Vertex<T>> iterator = tailX.iterator();

        while(iterator.hasNext()){
            Vertex<T> vertex = iterator.next();
            X.add(vertex);
        }
    }
}
