package mbe.process;

import mbe.algorithm.AbstractStaticBC;
import mbe.algorithm.DynamicBC;
import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @description: async feature implementation,
 * to make the Algorithm can process record while the source stream not being stopped.
 * 
 * @className: AsyncDynamicProcessBase
 * @author: Jiri Yu
 * @date: 2021/4/29 
 */
public class AsyncDynamicProcessBase extends RichAsyncFunction<Edge, Set<Biclique>> {
    private final static Set<Edge> edgeSet = new HashSet<>();

    private final CustomizedBipartiteGraph customizedBipartiteGraph;
    private final Class<? extends AbstractStaticBC> T;

    private static CompletableFuture<Set<Biclique>> future = null;

    private Set<Biclique> BC;

    public AsyncDynamicProcessBase(CustomizedBipartiteGraph customizedBipartiteGraph,
                                   Class<? extends AbstractStaticBC> T){
        this.customizedBipartiteGraph = customizedBipartiteGraph;
        this.T = T;
        this.BC = new HashSet<>();
    }

    @Override
    public void asyncInvoke(Edge edge, ResultFuture<Set<Biclique>> resultFuture) throws Exception {
        customizedBipartiteGraph.insertEdge(edge);

        if (future == null || future.isDone()){
            edgeSet.clear();
            edgeSet.add(edge);

            future = CompletableFuture.supplyAsync(new DynamicBC(customizedBipartiteGraph, edgeSet, BC, T));
            future.thenAccept(bicliques -> {
                BC = new HashSet<>(bicliques);
                resultFuture.complete(Collections.singleton(bicliques));
            });
            return;
        }

        edgeSet.add(edge);
        resultFuture.complete(Collections.EMPTY_SET);
    }

    @Override
    public void timeout(Edge edge, ResultFuture<Set<Biclique>> resultFuture) throws Exception {
        System.out.println("Timeout");
    }
}
