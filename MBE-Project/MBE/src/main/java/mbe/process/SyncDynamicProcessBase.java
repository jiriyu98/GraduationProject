package mbe.process;

import mbe.algorithm.AbstractStaticBC;
import mbe.algorithm.DynamicBC;
import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 
 * @ClassName: SyncDynamicProcessBase
 * @author: Jiri Yu
 * @date: 2021/4/8 
 */
public class SyncDynamicProcessBase extends RichMapFunction<Edge, Long> {
    private final CustomizedBipartiteGraph customizedBipartiteGraph;
    private final Class<? extends AbstractStaticBC> T;
    private final Set<Edge> edgeSet;

    private static Set<Biclique> BC = new HashSet<>();

    private long costTime;


    public SyncDynamicProcessBase(CustomizedBipartiteGraph customizedBipartiteGraph,
                                  Class<? extends AbstractStaticBC> T){
        this.customizedBipartiteGraph = new CustomizedBipartiteGraph(customizedBipartiteGraph);
        this.T = T;
        this.edgeSet = new HashSet<>();
        this.costTime = 0;
    }

    @Override
    public Long map(Edge edge) throws Exception {
        // before calculate, we need to add edges into graph.
        long startTime = System.currentTimeMillis();
        customizedBipartiteGraph.insertEdge(edge);

        // then calculate
        edgeSet.clear();
        edgeSet.add(edge);

        DynamicBC dynamicBC = new DynamicBC(customizedBipartiteGraph, edgeSet, BC, T);
        BC = dynamicBC.getBicliques();
        long endTime = System.currentTimeMillis();
        costTime += endTime - startTime;

        return costTime;
    }
}