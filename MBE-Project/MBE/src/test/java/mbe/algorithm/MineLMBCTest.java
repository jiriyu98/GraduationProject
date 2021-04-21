package mbe.algorithm;

import mbe.common.*;
import mbe.utils.RandomGenerate;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jiri Yu on 2021/4/15.
 */
public class MineLMBCTest {

    private static CustomizedBipartiteGraph graph;

    private static int numL;
    private static int numR;

    @BeforeClass
    public static void setup(){
        numL = 10;
        numR = 10;
        graph = new CustomizedBipartiteGraph();
    }


    @Ignore
    @Test
    public void testCase(){
        Set<Vertex> vertexSet = new HashSet<>();
        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(numL, Partition.LEFT, vertexSet);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(numR, Partition.RIGHT, vertexSet);

        graph.insertAllVertices(verticesL);
        graph.insertAllVertices(verticesR);

        for (int i=0; i<numL/2; ++i){
            Set<Edge> edgeSet = new HashSet<>();
            edgeSet.add(new Edge(verticesL[0], verticesR[i]));
            graph.insertAllEdges(edgeSet);
            AbstractStaticBC staticBC = new MineLMBC(graph);
            assertEquals(1, staticBC.getBicliques().size());
        }
    }

    @Test
    public void mineLMBC() {
        MineLMBC LMBC = new MineLMBC(graph);

        // TODO(Jiri Yu): complete them.
        // Step 1, create graph(1.1 add vertices, 1.2 add edges)
        createGraph();

        // Step 2, prepare parameters
        // X set
        Set<Vertex> X = new HashSet<>();
        // tailX set
        Set<Vertex> tailX = graph.getVerticesL();
        for (Vertex vertex : tailX){
            assertEquals(Partition.LEFT, vertex.getPartition());
        }
        // gammaX set
        Set<Vertex> gammaX = graph.getVerticesR();
        for (Vertex vertex : gammaX){
            assertEquals(Partition.RIGHT, vertex.getPartition());
        }
        // ms
        int ms = 1;

        // Step 3, invoke calculateBC
        LMBC.calculateBC(X, tailX, gammaX, ms);
        Set<Biclique> test1 = LMBC.getMaximalBicliques();

        // Step 4, invoke getBicliques
        Set<Biclique> test2 = LMBC.getBicliques();

        // Step 4, verify the number of bicliques. It should be 10 bicliques in maximal biclique set.
        assertEquals(test1, test2);
        assertEquals(10, LMBC.getMaximalBicliques().size());
    }

    /*
     * @description: assign edges as following graph(9 edges in all):
     *                 0L *** 0R
     *                 1L  *  1R
     *                 2L *** 2R
     *                 3L  *  3R
     *                 4L *** 4R
     *                 5L  *  5R
     *                 6L *** 6R
     *                 7L  *  7R
     *                 8L *** 8R
     *                 9L     9R
     * So we should get {0L->(0R,2R), 2L->(0R,2R,4R), 4L->(2R,4R,6R), 6L->(4R,6R,8R), 8L->(6R,8R)} and vice versa.
     *
     * @param
     * @return void
     * @author Jiri Yu
     */
    private void createGraph(){
        int count = 0;
        Edge[] edges;
        Set<Edge> edgeSet = new HashSet<>();
        Set<Vertex> vertexSet = new HashSet<>();

        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(numL, Partition.LEFT, vertexSet);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(numR, Partition.RIGHT, vertexSet);

        for (int i = 0; i < numL; i += 2) {
            Edge edge1 = new Edge(verticesL[i], verticesR[i]);
            edgeSet.add(edge1);
            count += 1;
            if(i >= 2){
                Edge edge2 = new Edge(verticesL[i], verticesR[i-2]);
                edgeSet.add(edge2);
                count += 1;
            }
            if(i+2 < numR){
                Edge edge3 = new Edge(verticesL[i], verticesR[i+2]);
                edgeSet.add(edge3);
                count += 1;
            }
        }
        edges = new Edge[count];
        assertEquals(numL/2*3-2, count);

        // graph add vertices and edges
        graph.insertAllVertices(verticesL);
        graph.insertAllVertices(verticesR);
        graph.insertAllEdges((Edge[]) edgeSet.toArray(edges));
        assertEquals(edgeSet, graph.getEdges());
        assertEquals(vertexSet, graph.getVertices());
    }
}