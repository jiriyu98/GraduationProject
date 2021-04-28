package mbe.algorithm;

import mbe.common.CustomizedBipartiteGraph;

/**
 * @description: Abstract class for static algorithm, which will be used in DynamicBC.
 *
 * @className: AbstractStaticBC
 * @author: Jiri Yu
 * @date: 2021/4/27
 */
public abstract class AbstractStaticBC extends AbstractBC{

    protected AbstractStaticBC(CustomizedBipartiteGraph customizedBipartiteGraph) {
        super(customizedBipartiteGraph);
    }
}
