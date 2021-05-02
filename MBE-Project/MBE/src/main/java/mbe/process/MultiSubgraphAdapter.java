package mbe.process;

import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import mbe.utils.Constants;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * @description: calculate subgraph as a pre-information.
 *
 * @className: MultiSubgraphAdapter
 * @author: Jiri Yu
 * @date: 2021/5/2
 */
public class MultiSubgraphAdapter extends RichMapFunction<Edge, Tuple3<Edge, CustomizedBipartiteGraph, Long>> {
    private CustomizedBipartiteGraph customizedBipartiteGraph;

    private final long boundSize = Constants.boundSize;
    private long count;

    public MultiSubgraphAdapter(CustomizedBipartiteGraph customizedBipartiteGraph) {
        this.customizedBipartiteGraph = customizedBipartiteGraph;
        // make the start num as zero
        this.count = -1L;
    }

    @Override
    public Tuple3<Edge, CustomizedBipartiteGraph, Long> map(Edge edge) {
        // add edge
        customizedBipartiteGraph.insertEdge(edge);

        // construct subgraph
        CustomizedBipartiteGraph subGraph = customizedBipartiteGraph.getSubGraph(edge);

        // construct id, like watermark
        count = (count + 1) % boundSize;

        // return tuple2
        Tuple3 tuple3 = new Tuple3<>(edge, subGraph, count);
        return tuple3;
    }
}
