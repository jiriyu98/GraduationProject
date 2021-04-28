package mbe.utils;

import mbe.common.Edge;
import mbe.common.Partition;
import mbe.common.Vertex;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jiri Yu on 2021/4/23.
 */
public class SerializableUtilsTest {
    @Test
    public void test() throws IOException {

        // Test deserializePojos()
        Set<Vertex> vertices = new HashSet<>();
        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(10000, Partition.LEFT, vertices);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(10000, Partition.RIGHT, vertices);
        SerializableUtils.serializePojo(Arrays.asList(verticesL), "testCase1VerticesL.csv", Vertex.class);
        SerializableUtils.serializePojo(Arrays.asList(verticesR), "testCase1VerticesR.csv", Vertex.class);
        List<Vertex> vertexListL = SerializableUtils.deserializePojos("testCase1VerticesL.csv", Vertex.class);
        List<Vertex> vertexListR = SerializableUtils.deserializePojos("testCase1VerticesR.csv", Vertex.class);
        assertEquals(Arrays.asList(verticesL), vertexListL);
        assertEquals(Arrays.asList(verticesR), vertexListR);

        Set<Edge> edges = new HashSet<>();
        Edge[] es = RandomGenerate.randomGenerateEdges(edges, verticesL, verticesR, 10000);
        SerializableUtils.serializePojo(Arrays.asList(es), "testCase1Edges.csv", Edge.class);
        List<Edge> edgeList = SerializableUtils.deserializePojos("testCase1Edges.csv", Edge.class);
        assertEquals(Arrays.asList(es).get(0), edgeList.get(0));

        File file1 = new File("src/main/resources/data/testCase1VerticesL.csv");
        File file2 = new File("src/main/resources/data/testCase1VerticesR.csv");
        File file3 = new File("src/main/resources/data/testCase1Edges.csv");

        // Test {@link #SerializableUtils.deserializePojo()}
        BufferedReader br = new BufferedReader(new FileReader(file3));
        String line = br.readLine();
        Edge edge = SerializableUtils.deserializePojo(line, Edge.class);
        assertEquals(edge, es[0]);

        file1.delete();
        file2.delete();
        file3.delete();
    }
}