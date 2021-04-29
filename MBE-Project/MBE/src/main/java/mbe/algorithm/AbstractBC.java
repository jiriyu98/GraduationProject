package mbe.algorithm;

import mbe.common.Biclique;
import mbe.common.CustomizedBipartiteGraph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Jiri Yu on 2021/4/19.
 */
public abstract class AbstractBC implements Serializable, Supplier<Set<Biclique>> {
    protected final CustomizedBipartiteGraph customizedBipartiteGraph;
    protected final Set<Biclique> maximalBicliques;

    public AbstractBC(CustomizedBipartiteGraph customizedBipartiteGraph) {
        this.customizedBipartiteGraph = customizedBipartiteGraph;
        this.maximalBicliques = new HashSet<>();
    }

    public Set<Biclique> getBicliques(){
        return maximalBicliques;
    }
}
