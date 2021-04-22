package mbe;

import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Partition;
import mbe.common.Vertex;
import mbe.utils.RandomGenerate;
import scala.util.Random;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: This class execute the process for comparing performance between different algorithms.
 * 
 * @className: ComparePerformance
 * @author: Jiri Yu
 * @date: 2021/4/21 
 */
public class ComparePerformance {


    public static void main(String[] args) {
        long timeStart = System.currentTimeMillis();
        long endStart = System.currentTimeMillis();
    }

    private void createGraph(CustomizedBipartiteGraph customizedBipartiteGraph, int numL, int numR){
        customizedBipartiteGraph = new CustomizedBipartiteGraph();

        Set<Vertex> vertices = new HashSet<>();
        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(numL, Partition.LEFT, vertices);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(numR, Partition.RIGHT, vertices);

        customizedBipartiteGraph.insertAllVertices(verticesL);
        customizedBipartiteGraph.insertAllVertices(verticesR);

    }
}
