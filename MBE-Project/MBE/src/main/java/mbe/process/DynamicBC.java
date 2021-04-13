package mbe.process;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.entity.Alert;

/**
 * @Description: 
 * @ClassName: DynamicBC
 * @author: Jiri Yu
 * @date: 2021/4/8 
 */
public class DynamicBC extends KeyedProcessFunction<Long, Edge, Alert> {
    @Override
    public void processElement(Edge edge, Context context, Collector<Alert> collector) throws Exception {
//        Alert alert = new Alert();
//        alert.setId(edge.getX());
//        collector.collect(alert);
    }
}
