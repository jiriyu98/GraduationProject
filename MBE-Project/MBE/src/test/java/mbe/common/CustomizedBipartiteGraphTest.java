package mbe.common;

import mbe.utils.RandomGenerate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @description: test all methods in CustomizedBipartiteGraph class.
 * 
 * @className: CustomizedBipartiteGraphTest
 * @author: Jiri Yu
 * @date: 2021/4/16 
 */
public class CustomizedBipartiteGraphTest {
    private static int numE;
    private static int numL;
    private static int numR;

    @BeforeClass
    public static void setup(){
        numE = 10;
        numL = 10;
        numR = 10;

    }

    /*
     * @description: Test Insert
     *
     * @param
     * @return void
     * @author Jiri Yu
     */
    @Test
    public void testInsert(){
        CustomizedBipartiteGraph graph = new CustomizedBipartiteGraph(numL, numR);

        Set<Edge> edgeSet = new HashSet<>();
        Set<Vertex> vertexSet = new HashSet<>();

        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(numL, Partition.LEFT, vertexSet);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(numR, Partition.RIGHT, vertexSet);

        Edge[] edges = RandomGenerate.randomGenerateEdges(edgeSet, verticesL, verticesR, numE);

        graph.insertAllVertices(verticesL);
        graph.insertAllVertices(verticesR);
        graph.insertAllEdges(edges);

        assertEquals(graph.getEdges(), new HashSet<Edge>(edgeSet));
        assertEquals(graph.getVertices(), new HashSet<Vertex>(vertexSet));
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
    @Test
    public void testAllMethods(){
        CustomizedBipartiteGraph graph = new CustomizedBipartiteGraph(numL, numR);

        int count = 0;
        Edge[] edges;
        Set<Edge> edgeSet = new HashSet<>();
        Set<Vertex> vertexSet = new HashSet<>();

        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(numL, Partition.LEFT, vertexSet);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(numR, Partition.RIGHT, vertexSet);

        // numL == numR
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

        // Step 1, add edges and ensure it works
        graph.insertAllVertices(verticesL);
        graph.insertAllVertices(verticesR);
        graph.insertAllEdges((Edge[]) edgeSet.toArray(edges));
        assertEquals(edgeSet, graph.getEdges());
        assertEquals(vertexSet, graph.getVertices());

        // Step 2, test getIntersectSet method
        Set<Vertex> verticesTest2A = new HashSet<>();
        Set<Vertex> verticesTest2B = new HashSet<>();
        Set<Vertex> verticesExpected2 = new HashSet<>();

        verticesTest2A.add(verticesL[0]);
        verticesTest2A.add(verticesL[1]);
        verticesTest2A.add(verticesR[2]);

        verticesTest2B.add(verticesL[4]);
        verticesTest2B.add(verticesL[3]);
        verticesTest2B.add(verticesR[2]);

        verticesExpected2.add(verticesR[2]);

        assertEquals(verticesExpected2, graph.getIntersectSet(verticesTest2A, verticesTest2B));

        // Step 3, test getAdjacentVertices method
        // Step 3.1, test set 1.
        Set<Vertex> verticesTest31 = new HashSet<>();
        Set<Vertex> verticesExpected31 = new HashSet<>();

        verticesTest31.add(verticesL[0]);
        verticesTest31.add(verticesL[2]);

        verticesExpected31.add(verticesR[0]);
        verticesExpected31.add(verticesR[2]);

        assertEquals(verticesExpected31, graph.getAdjacentVertices(verticesTest31));

        // Step 3.2, test set 2.
        Set<Vertex> verticesTest32 = new HashSet<>();
        Set<Vertex> verticesExpected32 = new HashSet<>();

        verticesTest32.add(verticesL[0]);
        verticesTest32.add(verticesR[2]);

        // verticesExpected2 should be none.
        assertEquals(true, verticesExpected32.isEmpty());

        assertEquals(verticesExpected32, graph.getAdjacentVertices(verticesTest32));

        // Step 4, test getAdjacentVerticesAndIntersect method
        Set<Vertex> verticesTest4 = new HashSet<>();
        Vertex vertexTest4 = verticesR[0];
        Set<Vertex> verticesExpected4 = new HashSet<>();

        verticesTest4.add(verticesL[0]);
        verticesTest4.add(verticesL[2]);

        verticesExpected4.add(verticesL[0]);
        verticesExpected4.add(verticesL[2]);

        assertEquals(verticesExpected4, graph.getAdjacentVerticesAndIntersect(verticesTest4, vertexTest4));
    }
}