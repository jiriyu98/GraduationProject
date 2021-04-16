package mbe.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @description: It should be tested in order, so I use @FixMethodOrder there.
 * 
 * @className: CustomizedBipartiteGraphTest
 * @author: Jiri Yu
 * @date: 2021/4/16 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomizedBipartiteGraphTest {

    private static CustomizedBipartiteGraph graph;

    @BeforeClass
    public static void setup(){
        int numE = 10;
        int numL = 10;
        int numR = 10;
        CustomizedBipartiteGraph graph = new CustomizedBipartiteGraph(numL, numR);
        Edge[] edges = randomGenerateEdges(numE, numL, numR);
        graph.insertAllEdges(edges);
        System.out.println(graph);
    }

    /*
     * @description: Test if
     *
     * @param
     * @return void
     * @author Jiri Yu
     */
    @Test
    public void test1(){
        System.out.println(graph);
    }

    private static Vertex[] randomGenerateVertices(int len, String partition){
        Vertex[] vertices = new Vertex[len];
        for (int i = 0; i < len; i++) {
            vertices[i] = new Vertex((long)i, RandomStringUtils.random(len, true, true), partition);
        }
        return vertices;
    }

    private static Edge[] randomGenerateEdges(int lenE, int lenL, int lenR){
        Edge[] edges = new Edge[lenE];
        Set<Edge> edgeSet = new HashSet<>();
        int count = 0;
        do{
            Edge edge = new Edge(
                    new Vertex(RandomUtils.nextLong(0, lenL), "", Partition.LEFT),
                    new Vertex(RandomUtils.nextLong(0, lenR), "", Partition.RIGHT));
            if(!edgeSet.contains(edge)){
                edges[count] = edge;
                edgeSet.add(edge);
                count += 1;
            }
        }while(count != lenE);
        return edges;
    }
}