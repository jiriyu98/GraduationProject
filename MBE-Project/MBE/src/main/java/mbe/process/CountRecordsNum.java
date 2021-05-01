package mbe.process;

import mbe.common.Biclique;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Set;

/**
 * @description: just for count
 *
 * @className: CountRecordsNum
 * @author: Jiri Yu
 * @date: 2021/5/2
 */
public class CountRecordsNum implements MapFunction<Set<Biclique>, Long> {
    long count = 0;

    @Override
    public Long map(Set<Biclique> bicliques) throws Exception {
        count = bicliques.size();
        return count;
    }
}
