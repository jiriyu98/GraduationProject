package mbe.source;

import mbe.common.Edge;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.HashSet;

/**
 * @Description: Source function Random ver. seq: [1, 2, 3, ..., verticesNum*]
 * @ClassName: BipartiteGraphSourceFunction
 * @author: Jiri Yu
 * @date: 2021/4/2 
 */
public class BipartiteGraphSourceFunctionRandom extends RichSourceFunction<Edge<Long>> implements ResultTypeQueryable<Edge<Long>> {
    private final long verticesNumX;
    private final long verticesNumY;
    private HashSet<Edge<Long>> edges;

    private volatile boolean isRunning = true;

    private static long CalculateNum(long range){
        return (long)(Math.random() * range);
    }

    public BipartiteGraphSourceFunctionRandom(long verticesNumX,
                                              long verticesNumY) {
        edges = new HashSet<>();
        this.verticesNumX = verticesNumX + 1;
        this.verticesNumY = verticesNumY + 1;
    }

    @Override
    public TypeInformation<Edge<Long>> getProducedType() {
        return null;
    }

    @Override
    public void run(SourceContext<Edge<Long>> sourceContext) throws Exception {
        while (isRunning) {
            long VX, VY;
            Edge<Long> edge;
            do {
                VX = CalculateNum(verticesNumX);
                VY = CalculateNum(verticesNumY);
                edge = new Edge<>(VX, VY);
            } while (edges.contains(edge));
            edges.add(edge);
            sourceContext.collect(new Edge<>(VX, VY));
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
