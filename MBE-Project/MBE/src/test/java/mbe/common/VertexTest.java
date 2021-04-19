package mbe.common;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jiri Yu on 2021/4/15.
 */
public class VertexTest {
    @Test
    public void createVertex(){
        int len = 100;
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            Vertex vertex = new Vertex((long)i);
            vertices.add(vertex);
            assertEquals(vertex, vertices.get(i));
            if(i>=1){
                assertNotNull(vertices.get(i-1));
                assertEquals(1, vertices.get(i).compareTo(vertices.get(i-1)));
            }
        }
    }

    @Test
    public void sort(){
        String[] strings = {"V1", "V2", "V3", "V4", "V13", "VV", "VN", "VN"};
        Vertex[] vertices = new Vertex[strings.length];
        for (int i = 0; i < strings.length; i++) {
            vertices[i] = new Vertex((long)i, strings[i]);
        }
        Arrays.sort(vertices);
        Arrays.sort(strings);

        assertEquals(strings.length, vertices.length);
        for (int i = 0; i < strings.length; i++) {
            assertEquals(strings[i], vertices[i].getValue());
        }
    }
}