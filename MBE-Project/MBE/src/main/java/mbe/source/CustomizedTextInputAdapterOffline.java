package mbe.source;

import mbe.common.Edge;
import mbe.utils.Constants;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: CustomizedTextInputAdapter offline version. It is just a demo.
 * tuple2<NewEdgeSet, AllEdgeSet>, f0->NewEdgeSet, f1->AllEdgeSet
 *
 * @className: CustomizedTextInputAdapterOffline
 * @author: Jiri Yu
 * @date: 2021/5/2
 */
public class CustomizedTextInputAdapterOffline extends RichMapFunction<Edge, Tuple2<List<Edge>, List<Edge>>> {

    private final List<Edge> returnNewRecord;
    private final List<Edge> returnAllRecord;
    private final int offlineSourceParallelism;
    private final int offlineSourceBatchSize;
    private final int offlineSourceEpochSize;
    private int countNum;

    public CustomizedTextInputAdapterOffline(){
        this.returnNewRecord = new ArrayList<>();
        this.returnAllRecord = new ArrayList<>();
        this.offlineSourceParallelism = Constants.offlineSourceParallelism;
        this.offlineSourceBatchSize = Constants.batchSourceSize;
        this.offlineSourceEpochSize = this.offlineSourceParallelism * this.offlineSourceBatchSize;
        this.countNum = -1;
    }

    @Override
    public Tuple2<List<Edge>, List<Edge>> map(Edge edge) throws Exception {
        // TODO(Jiri Yu): finish it.
        return null;
    }
}
