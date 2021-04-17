package mbe.utils;

import mbe.common.Edge;
import mbe.common.Vertex;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Set;

/**
 * Created by Jiri Yu on 2021/4/18.
 */
public class RandomGenerate {

    public static Vertex[] randomGenerateVertices(int len, String partition, Set<Vertex> vertexSet){
        Vertex[] vertices = new Vertex[len];
        for (int i = 0; i < len; i++) {
            vertices[i] = new Vertex((long)i, RandomStringUtils.random(len, true, true), partition);
            vertexSet.add(vertices[i]);
        }
        return vertices;
    }

    public static Edge[] randomGenerateEdges(Set<Edge> edgeSet, Vertex[] verticesL, Vertex[] verticesR, int numE){
        Edge[] edges = new Edge[numE];
        int count = 0;
        do{
            Edge edge = new Edge(
                    verticesL[RandomUtils.nextInt(0, verticesL.length)],
                    verticesR[RandomUtils.nextInt(0, verticesR.length)]);
            if(!edgeSet.contains(edge)){
                edges[count] = edge;
                edgeSet.add(edge);
                count += 1;
            }
        }while(count != numE);
        return edges;
    }
}
