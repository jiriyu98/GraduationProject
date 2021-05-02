package mbe;

import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import mbe.common.Vertex;
import mbe.source.CustomizedTextInputFormat;
import mbe.utils.SerializableUtils;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jiri Yu on 2021/5/2.
 */
public class MBEOffline {
    public static void main(String[] args) throws IOException {
        // localhost:8081
        // it needs local environment, that is why we include flink-dist.
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        // Because the source is bounded, we choose BATCH mode will get a better performance.
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);
        env.setParallelism(1);
//        env.getConfig().setAutoWatermarkInterval(30000);

        // Step 1, create Graph and insert vertices.
        CustomizedBipartiteGraph customizedBipartiteGraph = new CustomizedBipartiteGraph();

        int temp = 1;

        String fileNameVL = null;
        String fileNameVR = null;
        String fileNameE = null;

        switch (temp){
            case 1:
                fileNameVL = "case1Vertices100L.csv";
                fileNameVR = "case1Vertices100R.csv";
                fileNameE = "case1Edges100.csv";
                break;
            case 2:
                fileNameVL = "case2Vertices1000L.csv";
                fileNameVR = "case2Vertices1000R.csv";
                fileNameE = "case2Edges1000.csv";
                break;
            case 3:
                fileNameVL = "case3Vertices10000L.csv";
                fileNameVR = "case3Vertices10000R.csv";
                fileNameE = "case3Edges10000.csv";
                break;
        }

        List<Vertex> verticesL = SerializableUtils.deserializePojos(fileNameVL, Vertex.class);
        List<Vertex> verticesR = SerializableUtils.deserializePojos(fileNameVR, Vertex.class);
        customizedBipartiteGraph.insertAllVertices(verticesL);
        customizedBipartiteGraph.insertAllVertices(verticesR);
        assert customizedBipartiteGraph.getVerticesL().size() == verticesL.size() : "Wrong vertices' size";
        assert customizedBipartiteGraph.getVerticesR().size() == verticesR.size() : "Wrong vertices' size";

        // Step2, create source node, import edge from deserialization
        DataStream<Edge> source = env
                .readFile(new CustomizedTextInputFormat(), SerializableUtils.directory + fileNameE)
                .setParallelism(1);

        source.print();
    }
}
