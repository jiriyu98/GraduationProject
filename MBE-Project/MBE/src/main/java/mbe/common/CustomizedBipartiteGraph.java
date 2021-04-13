package mbe.common;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.Vertex;

/**
 * Created by Jiri Yu on 2021/4/13.
 */
public class CustomizedBipartiteGraph {

    public void test(){
        DataSet<Vertex<String, Long>> vertices = null;
        DataSet<Edge<String, Double>> edges = null;
        Graph<String, Long, Double> graph = Graph.fromDataSet(vertices, edges, null);
        graph.reduceOnNeighbors()
    }
}
