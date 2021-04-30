package mbe.process;

import mbe.common.Biclique;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 
 * 
 * @className: SubsumedBicliquesProcess
 * @author: Jiri Yu
 * @date: 2021/4/30 
 */
public class SubsumedBicliquesProcess extends RichMapFunction<Tuple2<Set<Biclique>, Set<Biclique>>, Set<Biclique>> {
    private final Set<Biclique> delBCSets;
    private final Set<Biclique> BC;

    public SubsumedBicliquesProcess() {
        this.delBCSets = new HashSet<>();
        this.BC = new HashSet<>();
    }

    @Override
    public Set<Biclique> map(Tuple2<Set<Biclique>, Set<Biclique>> tuple2) throws Exception {
        Set<Biclique> gammaNew = tuple2.f0;
        Set<Biclique> gammaDel = tuple2.f1;

        delBCSets.addAll(gammaDel);
        for (Biclique biclique : gammaNew){
            if(!delBCSets.contains(biclique)){
                BC.add(biclique);
            }
        }
        for (Biclique biclique : gammaDel){
            BC.remove(biclique);
        }
        return BC;
    }
}
