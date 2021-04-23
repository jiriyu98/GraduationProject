package mbe.utils;

import mbe.common.Edge;
import mbe.common.Partition;
import mbe.common.Vertex;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Jiri Yu on 2021/4/23.
 */
public class SerializableUtilsTest {
    @Test
    public void test() throws IOException {
        Set<Vertex> vertices = new HashSet<>();
        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(10000, Partition.LEFT, vertices);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(10000, Partition.RIGHT, vertices);
        SerializableUtils.serializePojo(vertices, "testCase1Vertices.txt");
        Set<Vertex> verticesNew = SerializableUtils.deserializePojo("testCase1Vertices.txt", new TypeReference<HashSet<Vertex>>(){});
        assertEquals(vertices.size(), verticesNew.size());
        assertEquals(vertices.size(), verticesL.length + verticesR.length);
        assertEquals(vertices, verticesNew);

        Set<Edge> edges = new HashSet<>();
        Edge[] es = RandomGenerate.randomGenerateEdges(edges, verticesL, verticesR, 10000);
        SerializableUtils.serializePojo(es, "testCase1Edges.txt");
        Edge[] esNew = SerializableUtils.deserializePojo("testCase1Edges.txt", Edge[].class);
        Set<Edge> edgesNew = SerializableUtils.deserializePojo("testCase1Edges.txt", new TypeReference<HashSet<Edge>>() {
        });
        assertArrayEquals(es, esNew);
        assertEquals(edges, edgesNew);

        File file1 = new File("src/main/resources/data/testCase1Vertices.txt");
        File file2 = new File("src/main/resources/data/testCase1Edges.txt");
        file1.delete();
        file2.delete();
    }
}