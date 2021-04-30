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
public class SyncStaticProcessBase extends RichMapFunction<Edge, Set<Biclique>> {
    private final CustomizedBipartiteGraph customizedBipartiteGraph;
    private static Set<Biclique> BC = new HashSet<>();

    public SyncStaticProcessBase(CustomizedBipartiteGraph customizedBipartiteGraph){
        this.customizedBipartiteGraph = customizedBipartiteGraph;
    }

    @Override
    public Set<Biclique> map(Edge edge) throws Exception {
        // before calculate, we need to add edges into graph.
        customizedBipartiteGraph.insertEdge(edge);

        // then calculate
        AbstractStaticBC mineLMBC = new MineLMBC(customizedBipartiteGraph);
        BC = mineLMBC.getBicliques();

        return BC;
    }
}
