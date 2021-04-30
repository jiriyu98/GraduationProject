package mbe.process;

import mbe.common.Biclique;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Set;

/**
 * Created by Jiri Yu on 2021/4/29.
 */
public class CountRecordsNum implements MapFunction<Set<Biclique>, Long> {
    long count = 0;

    @Override
    public Long map(Set<Biclique> bicliques) throws Exception {
        count = bicliques.size();
        return count;
    }
}
