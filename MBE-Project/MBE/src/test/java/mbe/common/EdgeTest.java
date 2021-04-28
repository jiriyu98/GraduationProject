package mbe.common;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Jiri Yu on 2021/4/16.
 */
public class EdgeTest {
    @Test
    public void testEdge(){
        int len = 10;
        Edge[] edges = new Edge[len];
        for (int i = 0; i < len; i++) {
            Vertex vertex1 = new Vertex((long)i+1);
            Vertex vertex2 = new Vertex((long)(-(i+1)));
            assertNotEquals(vertex1, vertex2);
            edges[i] = new Edge(vertex1, vertex2);
            if(i >= 1){
                assertNotEquals(edges[i], edges[i-1]);
            }
        }
    }
}