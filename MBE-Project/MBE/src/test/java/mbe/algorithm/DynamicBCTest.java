package mbe.algorithm;

import mbe.common.*;
import mbe.utils.RandomGenerate;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

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

    @Ignore
    @Test
    public void testCalculateBC1() {
        // Step 1, preparation
        Set<Set<Biclique>> sets = new HashSet<>();

        // Step 2, preparation
        Set<Biclique> BC = new HashSet<>();

        // Step 3, test calculateDelBC method.
        for (int i=0; i<numL/2; ++i){
            Set<Edge> edgeSet = new HashSet<>();
            edgeSet.add(new Edge(verticesL[0], verticesR[i]));
            customizedBipartiteGraph.insertAllEdges(edgeSet);
            DynamicBC dynamicBC = new DynamicBC(customizedBipartiteGraph, edgeSet, BC, MineLMBC.class);
            AbstractStaticBC staticBC = new MineLMBC(customizedBipartiteGraph);

            Set<Biclique> expectedBC = staticBC.getBicliques();
            Set<Biclique> actualBC = dynamicBC.getBicliques();
            assertEquals(expectedBC, actualBC);

            BC.clear();
            BC.addAll(expectedBC);
        }
    }

    @Ignore
    @Test
    public void testCalculateNewBC() {
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

    @Ignore
    @Test
    public void testCalculateSubBC() {
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

    @Test
    public void testCalculateBC2() {
        // Step 1, preparation
        Set<Set<Biclique>> sets = new HashSet<>();

        // Step 2, preparation
        Set<Biclique> BC = new HashSet<>();

        Edge[] edges = this.createEdges();

        // Step 3, test calculateDelBC method.
        for (int i=0; i<edges.length; ++i){
            Set<Edge> edgeSet = new HashSet<>();
            edgeSet.add(edges[i]);
            customizedBipartiteGraph.insertAllEdges(edgeSet);
            DynamicBC dynamicBC = new DynamicBC(customizedBipartiteGraph, edgeSet, BC, MineLMBC.class);
            AbstractStaticBC staticBC = new MineLMBC(customizedBipartiteGraph);

            Set<Biclique> expectedBC = staticBC.getBicliques();
            Set<Biclique> actualBC = dynamicBC.getBicliques();
            assertEquals(expectedBC, actualBC);

            System.out.println("newBC:" + soutBC(dynamicBC.getGammaNewBC()));
            System.out.println("delBC:" + soutBC(dynamicBC.getGammaDelBC()));
            System.out.println("BC:" + soutBC(dynamicBC.getBicliques()));
            System.out.println("BCsize:" + dynamicBC.getBicliques().size());


            BC.clear();
            BC.addAll(expectedBC);
        }
    }

    private Edge[] createEdges(){
        int count = 0;
        Edge[] edges = new Edge[13];

        for (int i = 0; i < numL; i += 2) {
            Edge edge1 = new Edge(verticesL[i], verticesR[i]);
            edges[count] = edge1;
            count += 1;
            if(i >= 2){
                Edge edge2 = new Edge(verticesL[i], verticesR[i-2]);
                edges[count] = edge2;
                count += 1;
            }
            if(i+2 < numR){
                Edge edge3 = new Edge(verticesL[i], verticesR[i+2]);
                edges[count] = edge3;
                count += 1;
            }
        }
        return edges;
    }

    private String soutBC(Set<Biclique> bicliques){
        String result = new String();
        for (Biclique biclique : bicliques){
            result += "<{";
            Iterator<Vertex> iterator = biclique.getLeftSet().iterator();
            while(iterator.hasNext()){
                Vertex vertex = iterator.next();
                result += vertex.getId() + vertex.getPartition();
                if (iterator.hasNext()){
                    result += ",";
                }
            }
            result += "},{";
            iterator = biclique.getRightSet().iterator();
            while(iterator.hasNext()){
                Vertex vertex = iterator.next();
                result += vertex.getId() + vertex.getPartition();
                if (iterator.hasNext()){
                    result += ",";
                }
            }
            result += "}>";
        }
        return result;
    }
}