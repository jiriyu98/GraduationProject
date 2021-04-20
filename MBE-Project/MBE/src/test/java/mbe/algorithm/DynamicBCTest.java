package mbe.algorithm;

import mbe.common.*;
import mbe.utils.RandomGenerate;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import scala.Array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
    }

    @Ignore
    @Test
    public void calculateNewBC() {
        // Step 1, preparation
        Set<Set<Biclique>> sets = new HashSet<>();

        // Step 2, test calculateNewBC method.
        for (int i=0; i<numL/2; ++i){
            Set<Edge> edgeSet = new HashSet<>();
            edgeSet.add(new Edge(verticesL[i], verticesR[i]));
            customizedBipartiteGraph.insertAllEdges(edgeSet);
            DynamicBC dynamicBC = new DynamicBC(customizedBipartiteGraph, edgeSet, new HashSet<>(), MineLMBC.class);
            dynamicBC.calculateNewBC();
            Set<Biclique> bicliques = dynamicBC.getGammaNewBC();
            sets.add(bicliques);
            Set<Biclique> trueBC = new HashSet<>();
            trueBC.add(new Biclique(
                    new HashSet<>(Arrays.asList(verticesL[i])),
                    new HashSet<>(Arrays.asList(verticesR[i]))));
            assertEquals(trueBC, bicliques);
        }
    }

    @Test
    public void calculateSubBC() {
        // Step 1, preparation
        Set<Set<Biclique>> sets = new HashSet<>();

        // Step 2, preparation
        Set<Biclique> BC = new HashSet<>();
        Set<Vertex> leftSet = new HashSet<>();
        Set<Vertex> rightSet = new HashSet<>();
        leftSet.add(verticesL[0]);

        // lastNewBC means newBC in last loop, and it equals delBC in this loop.
        Set<Biclique> lastNewBC = new HashSet<>();

        // Step 3, test calculateDelBC method.
        for (int i=0; i<numL/2; ++i){
            Set<Edge> edgeSet = new HashSet<>();
            edgeSet.add(new Edge(verticesL[0], verticesR[i]));
            customizedBipartiteGraph.insertAllEdges(edgeSet);
            DynamicBC dynamicBC = new DynamicBC(customizedBipartiteGraph, edgeSet, BC, MineLMBC.class);
            dynamicBC.getBicliques();
            Set<Biclique> newBC = dynamicBC.getGammaNewBC();
            Set<Biclique> delBC = dynamicBC.getGammaDelBC();

            // generate a new biclique and added to BC set
            rightSet.add(verticesR[i]);
            BC.clear();
            BC.add(new Biclique(leftSet, rightSet));
            assertEquals(lastNewBC, delBC);

            // set last newBC
            lastNewBC = newBC;
        }
    }
}