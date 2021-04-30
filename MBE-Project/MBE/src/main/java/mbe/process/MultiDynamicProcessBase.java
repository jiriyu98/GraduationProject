package mbe.process;

import mbe.algorithm.DynamicBC;
import mbe.algorithm.MineLMBC;
import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.Set;

/**
 * Created by Jiri Yu on 2021/4/30.
 */
public class MultiDynamicProcessBase extends RichMapFunction<Tuple2<Edge, CustomizedBipartiteGraph>, Tuple2<Set<Biclique>, Set<Biclique>>> {

    @Override
    public Tuple2<Set<Biclique>, Set<Biclique>> map(Tuple2<Edge, CustomizedBipartiteGraph> tuple2) throws Exception {
        Edge edge = tuple2.f0;
        CustomizedBipartiteGraph subGraph = tuple2.f1;
        Set<Biclique> gammaNew = DynamicBC.calculateGammaNew(subGraph, MineLMBC.class);
        Set<Biclique> gammaDel = DynamicBC.calculateGammaDel(gammaNew, edge);

        return Tuple2.of(gammaNew, gammaDel);
    }
}
