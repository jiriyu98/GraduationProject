package mbe.utils;

import akka.japi.Pair;
import mbe.common.Edge;
import mbe.common.Partition;
import mbe.common.Vertex;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.type.TypeReference;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: generate test cases into csv file, for flink dataStream use.
 *
 * @className: SerializableUtils
 * @author: Jiri Yu
 * @date: 2021/4/21
 */
public class SerializableUtils {
    private static final String directory = "src/main/resources/data/";
    private static final String fileExtension = "txt";
    private static final int filesNum = 4;

    public static String serializePojo(Object object, String fileName) throws IOException {
        fileName = directory + fileName;
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        file.getParentFile().mkdirs();
        if(!file.createNewFile()){
            throw new IOException("Create new file failed.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, object);
        String json = objectMapper.writeValueAsString(object);
        return json;
    }

    public static <T> T deserializePojo(String fileName, TypeReference<T> valueTypeRef) throws IOException {
        fileName = directory + fileName;
        File file = new File(fileName);
        if(!file.exists()){
            throw new IOException("File doesn't exist.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, valueTypeRef);
    }

    public static <T> T deserializePojo(String fileName, Class<T> classT) throws IOException {
        fileName = directory + fileName;
        File file = new File(fileName);
        if(!file.exists()){
            throw new IOException("File doesn't exist.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, classT);
    }

    public static void main(String[] args) throws IOException {
        File dirc = new File(directory);
        for (File file1 : dirc.listFiles()){
            file1.delete();
        }
        assert dirc.listFiles().length == 0 : "delete not true";

        String[][] fileNames = new String[filesNum][3];
        int[][] sizes = {{100, 100, 100}, {1000, 1000, 1000}, {10000, 10000, 10000}, {100000, 100000, 800000}};

        for (int i=0; i<fileNames.length; ++i){
            String vl = "case" + (i+1) + "Vertices" + "." + sizes[i][0] + Partition.LEFT + fileExtension;
            String vr = "case" + (i+1) + "Vertices" + "." + sizes[i][1] + Partition.RIGHT + fileExtension;
            String e = "case" + (i+1) + "Edges" + "." + sizes[i][2] + fileExtension;
            fileNames[i][0] = vl;
            fileNames[i][1] = vr;
            fileNames[i][2] = e;

            Set<Vertex> vertices = new HashSet<>();
            Set<Edge> edges = new HashSet<>();
            Vertex[] verticesL = RandomGenerate.randomGenerateVertices(sizes[i][0], Partition.LEFT, vertices);
            Vertex[] verticesR = RandomGenerate.randomGenerateVertices(sizes[i][1], Partition.RIGHT, vertices);
            Edge[] es =  RandomGenerate.randomGenerateEdges(edges, verticesL, verticesR, sizes[i][2]);

            SerializableUtils.serializePojo(verticesL, vl);
            SerializableUtils.serializePojo(verticesR, vr);
            SerializableUtils.serializePojo(es, e);
        }
    }
}
