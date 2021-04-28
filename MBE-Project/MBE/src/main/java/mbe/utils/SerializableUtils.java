package mbe.utils;

import mbe.common.Edge;
import mbe.common.Partition;
import mbe.common.Vertex;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: generate test cases into csv file, for flink dataStream use.
 * Do not use Kryo or others in Flink because they are costly.
 *
 * @className: SerializableUtils
 * @author: Jiri Yu
 * @date: 2021/4/21
 */
public class SerializableUtils {
    public static final String directory = "src/main/resources/data/";
    public static final String fileExtension = "csv";
    private static final int filesNum = 4;

    public static <T> void serializePojo(List<T> objects, String fileName, Class<T> t) throws IOException {
        fileName = directory + fileName;
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        file.getParentFile().mkdirs();
        if(!file.createNewFile()){
            throw new IOException("Create new file failed.");
        }

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(t);
        csvMapper.writer(schema).writeValue(file, objects);
        return;
    }

    public static <T> T deserializePojo(String value, Class<T> t) throws JsonProcessingException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(t);

        return (T)csvMapper.readerFor(t).with(schema.withColumnSeparator(',')).readValue(value);
    }

    public static <T> List<T> deserializePojos(String fileName, Class<T> t) throws IOException {
        fileName = directory + fileName;
        File file = new File(fileName);

        if(!file.exists()){
            throw new IOException("File doesn't exist.");

        }

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(t);

        return (List<T>)csvMapper.readerFor(t).with(schema.withColumnSeparator(',')).readValues(file).readAll();
    }

    /*
     * @description: Create test cases into file with csv extension.
     *
     * @param args
     * @return void
     * @author Jiri Yu
     */
    public static void main(String[] args) throws IOException {
        File dirc = new File(directory);
        for (File file1 : dirc.listFiles()){
            file1.delete();
        }
        assert dirc.listFiles().length == 0 : "delete not true";

        String[][] fileNames = new String[filesNum][3];
        int[][] sizes = {{100, 100, 100}, {1000, 1000, 1000}, {10000, 10000, 10000}, {100000, 100000, 800000}};

        for (int i=0; i<fileNames.length; ++i){
            String vl = "case" + (i+1) + "Vertices" + sizes[i][0] + Partition.LEFT + "." + fileExtension;
            String vr = "case" + (i+1) + "Vertices" + sizes[i][1] + Partition.RIGHT + "." + fileExtension;
            String e = "case" + (i+1) + "Edges" + sizes[i][2] + "." + fileExtension;
            fileNames[i][0] = vl;
            fileNames[i][1] = vr;
            fileNames[i][2] = e;

            Set<Vertex> vertices = new HashSet<>();
            Set<Edge> edges = new HashSet<>();
            Vertex[] verticesL = RandomGenerate.randomGenerateVertices(sizes[i][0], Partition.LEFT, vertices);
            Vertex[] verticesR = RandomGenerate.randomGenerateVertices(sizes[i][1], Partition.RIGHT, vertices);
            Edge[] es =  RandomGenerate.randomGenerateEdges(edges, verticesL, verticesR, sizes[i][2]);

            SerializableUtils.serializePojo(Arrays.asList(verticesL), vl, Vertex.class);
            SerializableUtils.serializePojo(Arrays.asList(verticesR), vr, Vertex.class);
            SerializableUtils.serializePojo(Arrays.asList(es), e, Edge.class);
        }
    }
}
