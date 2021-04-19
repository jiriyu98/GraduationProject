package mbe.algorithm;

import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import mbe.common.Vertex;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: DynamicBC, an algorithm for compute maximal bicliques in dynamic graph.
 * Dynamic graph means, vertices are fixed, edges will be added continuously.
 *
 * @className: DynamicBC
 * @author: Jiri Yu
 * @date: 2021/4/18
 */
public class DynamicBC extends AbstractDynamicBC {

    private final Set<Biclique> gammaNewBC;
    private final Set<Biclique> gammaDelBC;

    public DynamicBC(CustomizedBipartiteGraph customizedBipartiteGraph) {
        super(customizedBipartiteGraph);
        this.gammaNewBC = new HashSet<>();
        this.gammaDelBC = new HashSet<>();
    }

    public Set<Biclique> getGammaDelBC() {
        return gammaDelBC;
    }

    public Set<Biclique> getGammaNewBC() {
        return gammaNewBC;
    }

    public void calculateBC() {

    }

    /*
     * @description: H are edges being added to input bipartite graph
     *
     * @param H, edges being added to G
     * @return void
     * @author Jiri Yu
     */
    public void calculateNewBC(Set<Edge> H) {

        // preparation, gammaNewBC should be empty.
        gammaNewBC.clear();

        // *Line* means line number of the MineLMBC in paper.

        // it is useful
        Set<Edge> edgesI = new HashSet<>();

        // Line 1, it will be omitted. Because function assumes the G has been changed. It is G' now.

        // Line 2-9
        for (Edge edge : H) {

            // Line 4
            Vertex u = edge.getLeft();
            Vertex v = edge.getRight();

            // Line 5, subgraph of G' induced by (gamma(G', u) U Gamma(G', v))
            // get vertices L & R
            Set<Vertex> verticesL = customizedBipartiteGraph.getAdjacentVertices(v);
            Set<Vertex> verticesR = customizedBipartiteGraph.getAdjacentVertices(u);
            CustomizedBipartiteGraph subGraph = new CustomizedBipartiteGraph(verticesL, verticesR);
            // get edges
            // Because it is just a specific method used in DynamicBC, so it isn't in CustomizedBG's methods.
            Set<Edge> edgeSet = new HashSet<>();
            for (Vertex vl : verticesL) {
                for (Vertex vr : verticesR) {
                    if (customizedBipartiteGraph.containsEdge(vl, vr)) {
                        edgeSet.add(new Edge(vl, vr));
                    }
                }
            }
            // add edges into graph, and the subgraph is established now.
            subGraph.insertAllEdges(edgeSet);

            // Line 6, "Let B denote the set of the generated bicliques."
            MineLMBC mineLMBC = new MineLMBC(subGraph);
            // preparation for mineLMBC
            int ms = 1;
            mineLMBC.calculateBC(new HashSet<>(), subGraph.getVerticesL(), subGraph.getVerticesR(), ms);
            Set<Biclique> B = mineLMBC.getBicliques();

            // Line 7, do not use hashset, it is costly.
            for (Biclique biclique : B) {
                boolean ifContainsEdge = false;
                Set<Vertex> vls = biclique.getLeftSet();
                Set<Vertex> vrs = biclique.getRightSet();
                for (Vertex vl : vls) {
                    for (Vertex vr : vrs) {
                        if (edgesI.contains(new Edge(vl, vr))) {
                            ifContainsEdge = true;
                            break;
                        }
                    }
                }

                if (!ifContainsEdge) {
                    gammaNewBC.add(biclique);
                }
            }
            edgesI.add(edge);
        }

    }

    public void calculateSubBC() {

    }
}
