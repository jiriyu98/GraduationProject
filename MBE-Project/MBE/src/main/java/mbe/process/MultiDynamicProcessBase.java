package mbe.process;

import mbe.algorithm.DynamicBC;
import mbe.algorithm.MineLMBC;
import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.Set;

/**
 * @description: this class can be executed in parallel.
 * 
 * @className: MultiDynamicProcessBase
 * @author: Jiri Yu
 * @date: 2021/5/2 
 */
public class MultiDynamicProcessBase extends RichMapFunction<Tuple3<Edge, CustomizedBipartiteGraph, Long>,
        Tuple3<Set<Biclique>, Set<Biclique>, Long>> {

    @Override
    public Tuple3<Set<Biclique>, Set<Biclique>, Long> map(Tuple3<Edge, CustomizedBipartiteGraph, Long> tuple3) throws Exception {
        Edge edge = tuple3.f0;
        CustomizedBipartiteGraph subGraph = tuple3.f1;
        Set<Biclique> gammaNew = DynamicBC.calculateGammaNew(subGraph, MineLMBC.class);
        Set<Biclique> gammaDel = DynamicBC.calculateGammaDel(gammaNew, edge);

        return Tuple3.of(gammaNew, gammaDel, tuple3.f2);
    }
}
