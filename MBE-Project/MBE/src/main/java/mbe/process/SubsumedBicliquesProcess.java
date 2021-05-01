package mbe.process;

import mbe.common.Biclique;
import mbe.utils.Constants;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.*;

/**
 * @description: calculate subsumed bicliques
 *
 * @className: SubsumedBicliquesProcess
 * @author: Jiri Yu
 * @date: 2021/4/30
 */
public class SubsumedBicliquesProcess extends RichMapFunction<Tuple3<Set<Biclique>, Set<Biclique>, Long>, Set<Biclique>> {
    private final Map<Long, Set<Biclique>> delBCMaps;
    private final Set<Biclique> BC;

    private final long boundSize = Constants.boundSize;
    private final long batchSize = Constants.batchSize;
    private long boundCount;
    // count a batch to delete HashMap, like a period.
    private long batchCount;

    public SubsumedBicliquesProcess() {
        this.delBCMaps = new HashMap<>();
        this.BC = new HashSet<>();
        this.boundCount = 0;
        this.batchCount = 0;
    }

    @Override
    public Set<Biclique> map(Tuple3<Set<Biclique>, Set<Biclique>, Long> tuple3) throws Exception {
        Set<Biclique> gammaNew = tuple3.f0;
        Set<Biclique> gammaDel = tuple3.f1;
        long id = tuple3.f2;

        Iterator<Biclique> iterator = gammaNew.iterator();
        while (iterator.hasNext()) {
            Biclique biclique = iterator.next();
            for (Set<Biclique> bicliques : delBCMaps.values()) {
                if (bicliques.contains(biclique)) {
                    iterator.remove();
                    break;
                }
            }
        }
        BC.addAll(gammaNew);

        delBCMaps.put(id, gammaDel);

        for (Biclique biclique : gammaDel) {
            BC.remove(biclique);
        }

        // then update del Map
        batchCount = (batchCount + 1) % batchSize;
        if (batchCount == 0) {
            for (long i = boundCount; ; i = (i + 1) % boundSize) {
                if (i != id && delBCMaps.containsKey(i)) {
                    delBCMaps.remove(i);
                    boundCount = i;
                    continue;
                }
                if (i == id) {
                    boundCount = i;
                }
                break;
            }
        }
        return BC;
    }
}
