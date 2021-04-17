package mbe.algorithm;

import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Partition;
import mbe.common.Vertex;

import java.util.*;

/**
 * @description: basic algorithm for static graph
 *
 * @className: MineLMBC
 * @author: Jiri Yu
 * @date: 2021/4/12 
 */
public class MineLMBC {
    private final CustomizedBipartiteGraph customizedBipartiteGraph;

    private Set<Biclique> maximalBicliques;

    public MineLMBC(CustomizedBipartiteGraph customizedBipartiteGraph) {
        this.customizedBipartiteGraph = customizedBipartiteGraph;
    }

    public Set<Biclique> getBicliques() {
        return maximalBicliques;
    }

    /*
     * @description: mineLMBC, an algorithm for static bipartite graph.
     *
     * @param X, is a vertex set
     * @param gammaX, is the adjacency list of X
     * @param tailX, is the tail vertices of X
     * @param ms, means minimum size threshold, and it will be assigned as an constant 1 in our algorithm.
     * @return void
     * @author Jiri Yu
     */
    public void mineLMBC(Set<Vertex> X, Set<Vertex> gammaX, Set<Vertex> tailX, long ms){
        // *Line* means line number of the MineLMBC in paper.

        // it is useful for following lines to store size of {X U v}.
        HashMap<Vertex, Integer> sizeOfXUnionVertexHashMap = new HashMap<>();

        // Line 1-3
        Iterator<Vertex> iteratorTailX = tailX.iterator();
        while(iteratorTailX.hasNext()){
            Vertex vertex = iteratorTailX.next();
            // {X U v}
            X.add(vertex);
            // it may be confusing, but in this line X means {X U v} in paper.
            int sizeOfXUnionVertex = customizedBipartiteGraph.getAdjacentVerticesAndIntersect(gammaX, vertex).size();
            if(sizeOfXUnionVertex < ms){
                tailX.remove(vertex);
            }else{
                // only this case should be calculated(i.e. put into hashmap to store)
                sizeOfXUnionVertexHashMap.put(vertex, sizeOfXUnionVertex);
            }
            // X
            X.remove(vertex);
        }

        // Line 4-5
        if(X.size() + tailX.size() < ms){
            return;
        }

        // Line 6
        Set<Vertex> ascendingOrderSet = new TreeSet<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex vertex1, Vertex vertex2) {
                return sizeOfXUnionVertexHashMap.get(vertex1) - sizeOfXUnionVertexHashMap.get(vertex2);
            }
        });
        Iterator<Vertex> iteratorTailXNew = tailX.iterator();
        while(iteratorTailXNew.hasNext()){
            Vertex vertex = iteratorTailXNew.next();
            // TODO(Jiri Yu): whether it should be sorted by String property.
            ascendingOrderSet.add(vertex);
        }

        // Line 7-14
        Iterator<Vertex> iteratorSortedTailXNew = ascendingOrderSet.iterator();
        while(iteratorSortedTailXNew.hasNext()){
            Vertex vertex = iteratorTailX.next();
            tailX.remove(vertex);
            // Line 9
            if(X.size() + 1 + tailX.size() >= ms){
                // Line 10
                X.add(vertex);
                // gamma({X U v})
                Set<Vertex> gammaXUnionVertex = customizedBipartiteGraph.getAdjacentVerticesAndIntersect(gammaX, vertex);
                // gamma(gamma({X U v})
                Set<Vertex> Y = customizedBipartiteGraph.getAdjacentVertices(gammaXUnionVertex);
                Y.removeAll(X);
                // Line 11
                if(tailX.containsAll(Y)){
                    // Line 12
                    if(Y.size() >= ms){
                        // TODO(Jiri Yu): Can I find a new method to avoid state L or R set explicitly.
                        // Line 13, biclique we define distinguishes Left Set adn Right Set.
                        // So we have to distinguish them before construct a new biclique.
                        if(Y.iterator().next().getPartition().equals(Partition.LEFT)){
                            maximalBicliques.add(new Biclique(Y, gammaXUnionVertex));
                        }else{
                            maximalBicliques.add(new Biclique(gammaXUnionVertex, Y));
                        }
                    }
                    tailX.removeAll(Y);
                    mineLMBC(Y, gammaXUnionVertex, tailX, ms);
                }
                // these can be deleted.
//                Y.addAll(X);
                X.remove(vertex);
            }
        }
    }
}
