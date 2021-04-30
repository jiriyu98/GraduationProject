package mbe.process;

import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by Jiri Yu on 2021/4/30.
 */
public class SubgraphAdapter extends RichMapFunction<Edge, Tuple2<Edge, CustomizedBipartiteGraph>> {
    private CustomizedBipartiteGraph customizedBipartiteGraph;

    public SubgraphAdapter(CustomizedBipartiteGraph customizedBipartiteGraph){
        this.customizedBipartiteGraph = customizedBipartiteGraph;
    }

    @Override
    public Tuple2<Edge, CustomizedBipartiteGraph> map(Edge edge) {
        // add edge
        customizedBipartiteGraph.insertEdge(edge);

        // construct subgraph
        CustomizedBipartiteGraph subGraph = customizedBipartiteGraph.getSubGraph(edge);

        // return tuple2
        Tuple2 tuple2 = new Tuple2<>(edge, subGraph);
        return tuple2;
    }
}
