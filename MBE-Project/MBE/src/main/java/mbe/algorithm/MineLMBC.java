package mbe.algorithm;

import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Partition;
import mbe.common.Vertex;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: basic algorithm for static graph
 *
 * @className: MineLMBC
 * @author: Jiri Yu
 * @date: 2021/4/12
 */
public class MineLMBC extends AbstractStaticBC {

    public MineLMBC(CustomizedBipartiteGraph customizedBipartiteGraph) {
        super(customizedBipartiteGraph);
    }

    public Set<Biclique> getMaximalBicliques(){
        return maximalBicliques;
    }

    @Override
    public Set<Biclique> getBicliques(){
        // mock delay
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        maximalBicliques.clear();
        Set<Vertex> X = new HashSet<>();
        Set<Vertex> tailX = new HashSet<>(customizedBipartiteGraph.getVerticesL());
        Set<Vertex> gammaX = new HashSet<>(customizedBipartiteGraph.getVerticesR());
        int ms = 1;
        calculateBC(X, tailX, gammaX, ms);
        return maximalBicliques;
    }

    /**
     * @description: mineLMBC, an algorithm for static bipartite graph.
     *
     * @param X, is a vertex set
     * @param gammaX, is the adjacency list of X
     * @param tailX, is the tail vertices of X
     * @param ms, means minimum size threshold, and it will be assigned as an constant 1 in our algorithm.
     * @return void
     * @author Jiri Yu
     */
    public void calculateBC(Set<Vertex> X, Set<Vertex> gammaX, Set<Vertex> tailX, long ms) {
        // *Line* means line number of the MineLMBC in paper.

        // it is useful for following lines to store size of (X U {v}).
        HashMap<Vertex, Integer> sizeOfXUnionVertexHashMap = new HashMap<>();

        // Line 1-3
        Iterator<Vertex> iteratorTailX = tailX.iterator();
        while (iteratorTailX.hasNext()) {
            Vertex vertex = iteratorTailX.next();
            int sizeOfXUnionVertex =
                    customizedBipartiteGraph.getAdjacentVerticesAndIntersect(gammaX, vertex).size();
            if (sizeOfXUnionVertex < ms) {
                // tailX.remove(vertex);
                iteratorTailX.remove();
            } else {
                // only this case should be calculated(i.e. put into hashmap to store)
                sizeOfXUnionVertexHashMap.put(vertex, sizeOfXUnionVertex);
            }
        }

        // Line 4-5
        if (X.size() + tailX.size() < ms) {
            return;
        }

        // Line 6
        // WARNING: TreeSet will replace the old element which is compared same to the new one.
        // So, in this way, I just add a new judgment to ensure it will get the all vertices.
        Set<Vertex> ascendingOrderSet = new TreeSet<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                int size1 = sizeOfXUnionVertexHashMap.get(o1);
                int size2 = sizeOfXUnionVertexHashMap.get(o2);
                if (size1 == size2) {
                    return o1.getId().compareTo(o2.getId());
                } else {
                    return size1 - size2;
                }
            }
        });
        for (Vertex vertex : tailX){
            ascendingOrderSet.add(vertex);
        }

        // FIXME(Jiri Yu): remove it when releasing
        assert (ascendingOrderSet.size() == tailX.size())
                : "tailX.size():" + tailX.size() + ", ascendingOrderSet.size():" + ascendingOrderSet.size();

        // Line 7-14
        Iterator<Vertex> iteratorSortedTailXNew = ascendingOrderSet.iterator();
        while (iteratorSortedTailXNew.hasNext()) {
            Vertex vertex = iteratorSortedTailXNew.next();
            // tailX.remove(vertex);
            iteratorSortedTailXNew.remove();
            // Line 9
            if (X.size() + 1 + tailX.size() >= ms) {
                // Line 10
                // gamma(X U {v})
                Set<Vertex> gammaXUnionVertex =
                        customizedBipartiteGraph.getAdjacentVerticesAndIntersect(gammaX, vertex);
                // gamma(gamma(X U {v})
                Set<Vertex> Y = customizedBipartiteGraph.getAdjacentVertices(gammaXUnionVertex);
                // Y - (X U {v})
                Set<Vertex> delY = new HashSet<>(Y);
                delY.removeAll(X);
                delY.remove(vertex);
                // Line 11
                if (tailX.containsAll(delY)) {
                    // Line 12
                    if (Y.size() >= ms) {
                        // Line 13, biclique we define distinguishes Left Set adn Right Set.
                        // So we have to distinguish them before construct a new biclique.
                        // It's a trick in Biclique class's definition.
                        if (Y.iterator().next().getPartition().equals(Partition.LEFT)) {
                            maximalBicliques.add(new Biclique(Y, gammaXUnionVertex));
                        } else {
                            maximalBicliques.add(new Biclique(gammaXUnionVertex, Y));
                        }
                    }
                    // It's in the loop, so tailX will be used next time
                    Set<Vertex> tailXNew = new HashSet<>(tailX);
                    tailXNew.removeAll(Y);
                    calculateBC(Y, gammaXUnionVertex, tailXNew, ms);
                }
            }
        }
    }

    /*
     * @description: implement supplier interface
     *
     * @param
     * @return java.util.Set<mbe.common.Biclique>
     * @author Jiri Yu
     */
    @Override
    public Set<Biclique> get() {
        return this.getBicliques();
    }
}
