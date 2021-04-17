package mbe.algorithm;

import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Vertex;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jiri Yu on 2021/4/15.
 */
public class MineLMBCTest {

    private static int numL;
    private static int numR;
    private static int numE;

    private static CustomizedBipartiteGraph graph;

    @BeforeClass
    public static void setup(){
        numL = 10;
        numR = 10;
        graph = new CustomizedBipartiteGraph(numL, numR);
    }

    @Test
    public void mineLMBC() {
        CustomizedBipartiteGraph graph = new CustomizedBipartiteGraph(numL, numR);
        MineLMBC LMBC = new MineLMBC(graph);

        // X set
        Set<Vertex> X = new HashSet<>();
        // tailX set
        Set<Vertex> tailX = graph.getVerticesL();
        // gammaX set
        Set<Vertex> gammaX = graph.getVerticesR();
        // ms
        int ms = 1;

        // TODO(Jiri Yu): complete them.
        // Step 1, add vertices into graph
        // Step 2, add edges into graph
        // Step 3, invoke assertEquals
        LMBC.mineLMBC(X, tailX, gammaX, ms);
    }
}