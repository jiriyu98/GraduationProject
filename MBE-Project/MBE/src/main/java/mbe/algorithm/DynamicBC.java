package mbe.algorithm;

import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import mbe.common.Vertex;

import java.lang.reflect.InvocationTargetException;
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
    private final Class<? extends AbstractStaticBC> staticBCClass;

    private final Set<Biclique> gammaNewBC;
    private final Set<Biclique> gammaDelBC;
    private final Set<Biclique> BC;

    private final Set<Edge> H;

    public DynamicBC(CustomizedBipartiteGraph customizedBipartiteGraph,
                     Set<Edge> H, Set<Biclique> BC,
                     Class<? extends AbstractStaticBC> staticBCClass) {
        super(customizedBipartiteGraph);

        this.gammaNewBC = new HashSet<>();
        this.gammaDelBC = new HashSet<>();

        this.H = new HashSet<>();
        this.H.addAll(H);

        this.BC = new HashSet<>();
        this.BC.addAll(BC);

        this.staticBCClass = staticBCClass;
    }

    public Set<Biclique> getGammaDelBC() {
        return gammaDelBC;
    }

    public Set<Biclique> getGammaNewBC() {
        return gammaNewBC;
    }

    @Override
    public Set<Biclique> getBicliques() {
        calculateNewBC();
        calculateSubBC();

        // BC + NewBC - DelBC -> BC
        maximalBicliques.addAll(BC);
        maximalBicliques.addAll(gammaNewBC);
        maximalBicliques.removeAll(gammaDelBC);
        return maximalBicliques;
    }

    /*
     * @description: calculate new maximal bicliques in G', and the output is stored in gammaNewBC.
     *
     * @param
     * @return void
     * @author Jiri Yu
     */
    public void calculateNewBC() {
        // *Line* means line number of the MineLMBC in paper.
        // it is useful
        Set<Edge> edgesI = new HashSet<>();
        // preparation, gammaNewBC should be empty.
        gammaNewBC.clear();

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
            // TODO(Jiri Yu): Well, it seems ugly to me. Are there any good ideas to beautify them?
            //  How to avoid to initialize Static as null firstly?
            AbstractStaticBC StaticBC = null;
            try {
                StaticBC = staticBCClass.getConstructor(CustomizedBipartiteGraph.class).newInstance(subGraph);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                if (StaticBC == null){
                    System.out.println("NPE happened, exit.");
                    System.exit(-1);
                }
            }
            Set<Biclique> B = StaticBC.getBicliques();

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

    /*
     * @description: calculate new maximal bicliques in G', and the output is stored in gammaDelBC.
     *
     * @param
     * @return void
     * @author Jiri Yu
     */
    public void calculateSubBC() {
        // *Line* means line number of the MineLMBC in paper.

        // Line 1
        gammaDelBC.clear();

        // Line 2
        for (Biclique b : gammaNewBC){
            // Line 3
            Set<Biclique> S = new HashSet<>();
            S.add(b);

            // Line 4, edge in H
            for (Edge edge : H){
                Vertex u = edge.getLeft();
                Vertex v = edge.getRight();
                // Line 4, edge in E(b), the property of biclique
                if(b.containsEdge(edge)){
                    // Line 5
                    Set<Biclique> SPrime = new HashSet<>();

                    // Line 6, bPrime means b' in paper
                    for (Biclique bPrime : S){
                        if (bPrime.containsEdge(edge)){
                            // We already know left endpoint and right endpoint
                            // Line 8
                            if (bPrime.getLeftSet().size() > 1){
                                Biclique b1 = new Biclique(bPrime);
                                b1.removeLeftVertex(u);
                                SPrime.add(b1);
                            }
                            if (bPrime.getRightSet().size() > 1){
                                Biclique b2 = new Biclique(bPrime);
                                b2.removeRightVertex(v);
                                SPrime.add(b2);
                            }
                        }else{
                            // Line 11
                            SPrime.add(bPrime);
                        }
                    }
                    // Line 14
                    S = SPrime;
                }
            }

            // Line 15-18
            for (Biclique bPrime : S){
                if(BC.contains(bPrime)){
                    gammaDelBC.add(bPrime);
                    BC.remove(bPrime);
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
