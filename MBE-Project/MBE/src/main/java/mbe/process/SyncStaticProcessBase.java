package mbe.process;

import mbe.algorithm.AbstractStaticBC;
import mbe.algorithm.DynamicBC;
import mbe.algorithm.MineLMBC;
import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 
 * 
 * @className: SyncStaticProcessBase
 * @author: Jiri Yu
 * @date: 2021/4/28 
 */
public class SyncStaticProcessBase extends RichMapFunction<Edge, Long> {
    private final CustomizedBipartiteGraph customizedBipartiteGraph;
    private static Set<Biclique> BC = new HashSet<>();

    private long costTime;

    public SyncStaticProcessBase(CustomizedBipartiteGraph customizedBipartiteGraph){
        this.customizedBipartiteGraph = new CustomizedBipartiteGraph(customizedBipartiteGraph);
        this.costTime = 0;
    }

    @Override
    public Long map(Edge edge) throws Exception {
        // before calculate, we need to add edges into graph.
        long startTime = System.currentTimeMillis();
        customizedBipartiteGraph.insertEdge(edge);

        // then calculate
        AbstractStaticBC mineLMBC = new MineLMBC(customizedBipartiteGraph);
        BC = mineLMBC.getBicliques();
        long endTime = System.currentTimeMillis();
        costTime += endTime - startTime;

        return costTime;
    }
}
