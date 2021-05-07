package mbe.process;

import mbe.algorithm.AbstractStaticBC;
import mbe.algorithm.DynamicBC;
import mbe.algorithm.MineLMBC;
import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @description: async feature implementation, multi.
 * to make the Algorithm can process record while the source stream not being stopped.
 *
 * @className: AsyncMultiDynamicProcessBase
 * @author: Jiri Yu
 * @date: 2021/4/29
 */
public class AsyncMultiDynamicProcessBase extends RichAsyncFunction<Tuple3<Edge, CustomizedBipartiteGraph, Long>, Tuple3<Set<Biclique>, Set<Biclique>, Long>> {
    private final static Set<Edge> edgeSet = new HashSet<>();

    private final CustomizedBipartiteGraph customizedBipartiteGraph;
    private final Class<? extends AbstractStaticBC> T;
    private final int edgeSetSize = 5;

    private static CompletableFuture<Set<Biclique>> future = null;

    private Set<Biclique> BC;

    public AsyncMultiDynamicProcessBase(CustomizedBipartiteGraph customizedBipartiteGraph,
                                   Class<? extends AbstractStaticBC> T){
        this.customizedBipartiteGraph = customizedBipartiteGraph;
        this.T = T;
        this.BC = new HashSet<>();
    }

    @Override
    public void asyncInvoke(Tuple3<Edge, CustomizedBipartiteGraph, Long> tuple3, ResultFuture<Tuple3<Set<Biclique>, Set<Biclique>, Long>> resultFuture) throws Exception {
        CompletableFuture<Tuple3<Set<Biclique>, Set<Biclique>, Long>> future;
        future = CompletableFuture.supplyAsync(() -> {
            Edge edge = tuple3.f0;
            CustomizedBipartiteGraph subGraph = tuple3.f1;
            Set<Biclique> gammaNew = DynamicBC.calculateGammaNew(subGraph, MineLMBC.class);
            Set<Biclique> gammaDel = DynamicBC.calculateGammaDel(gammaNew, edge);
            return Tuple3.of(gammaNew, gammaDel, tuple3.f2);
        });
        future.thenAccept(iTuple3 -> {
            resultFuture.complete(Collections.singleton(iTuple3));
        });
    }

    @Override
    public void timeout(Tuple3<Edge, CustomizedBipartiteGraph, Long> tuple3, ResultFuture<Tuple3<Set<Biclique>, Set<Biclique>, Long>> resultFuture) throws Exception {
        this.asyncInvoke(tuple3, resultFuture);
    }
}
