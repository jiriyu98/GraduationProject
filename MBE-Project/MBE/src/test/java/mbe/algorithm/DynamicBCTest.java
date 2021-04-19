package mbe.algorithm;

import mbe.common.*;
import mbe.utils.RandomGenerate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Jiri Yu on 2021/4/19.
 */
public class DynamicBCTest {

    private static int numL;
    private static int numR;
    private static CustomizedBipartiteGraph customizedBipartiteGraph;
    private static Vertex[] verticesL;
    private static Vertex[] verticesR;

    @BeforeClass
    public static void setup(){
        // Create graph first.
        numL = 10;
        numR = 10;
        customizedBipartiteGraph = new CustomizedBipartiteGraph();

        Set<Vertex> vertexSet = new HashSet<>();
        verticesL = RandomGenerate.randomGenerateVertices(numL, Partition.LEFT, vertexSet);
        verticesR = RandomGenerate.randomGenerateVertices(numR, Partition.RIGHT, vertexSet);

        customizedBipartiteGraph.insertAllVertices(verticesL);
        customizedBipartiteGraph.insertAllVertices(verticesR);

        assertEquals(vertexSet.size(), customizedBipartiteGraph.getVertices().size());
    }

    @Test
    public void calculateBC() {

        // Step 1, preparation
        DynamicBC dynamicBC = new DynamicBC(customizedBipartiteGraph);
        Set<Set<Biclique>> sets = new HashSet<>();

        // Step 2, test calculateNewBC method.
        for (int i=0; i<numL/2; ++i){
            Set<Edge> edgeSet = new HashSet<>();
            edgeSet.add(new Edge(verticesL[i], verticesR[i]));
            customizedBipartiteGraph.insertAllEdges(edgeSet);
            dynamicBC.calculateNewBC(edgeSet);
            Set<Biclique> bicliques = dynamicBC.getGammaNewBC();
            sets.add(bicliques);
            System.out.println(bicliques);
        }
    }

    @Test
    public void calculateNewBC() {
    }

    @Test
    public void calculateSubBC() {
    }
}