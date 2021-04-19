package mbe.algorithm;

import mbe.common.CustomizedBipartiteGraph;

/*
 * @description: Abstract class for static algorithm, which will be used in DynamicBC.
 *
 * @param null
 * @return
 * @author Jiri Yu
 */
public abstract class AbstractStaticBC extends AbstractBC{

    protected AbstractStaticBC(CustomizedBipartiteGraph customizedBipartiteGraph) {
        super(customizedBipartiteGraph);
    }
}
