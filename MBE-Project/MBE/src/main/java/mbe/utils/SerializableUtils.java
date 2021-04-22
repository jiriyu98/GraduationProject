package mbe.utils;

import mbe.common.Edge;
import mbe.common.Partition;
import mbe.common.Vertex;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
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

    public static String serializePojo(Object object, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        file.getParentFile().mkdirs();
        if(!file.createNewFile()){
            throw new IOException("Create new file failed.");
        }
        objectMapper.writeValue(file, object);
        String json = objectMapper.writeValueAsString(object);
        return json;
    }

    public static <T> T deserializePojo(String fileName, TypeReference<T> valueTypeRef) throws IOException {
        File file = new File(fileName);
        if(file.exists()){
            throw new IOException("File doesn't exist.");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(fileName, valueTypeRef);
    }

    public static void main(String[] args) throws IOException {
        Set<Vertex> vertices = new HashSet<>();
        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(1000, Partition.LEFT, vertices);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(1000, Partition.RIGHT, vertices);
        String json = serializePojo(vertices, "src/main/resources/data/case1Vertices.txt");

//        Set<Edge> edges = new HashSet<>();
//        Edge[] es =
//
//        Set<Vertex> verticesNew = deserializePojo(json, new TypeReference<HashSet<Vertex>>(){});
//        assert vertices == verticesNew : "Wrong";
    }
}
