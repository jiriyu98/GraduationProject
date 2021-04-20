package mbe.common;

import mbe.utils.RandomGenerate;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import scala.util.Random;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created by Jiri Yu on 2021/4/15.
 */
public class BicliqueTest {
    @Test
    public void createBiclique(){
        Biclique biclique;
        Set<Vertex> vertexLeftSet = new TreeSet<>();
        Set<Vertex> vertexRightSet = new TreeSet<>();
        int lenLeft = 10;
        int lenRight = 10;

        for (int i = 0; i < lenLeft; i++) {
            vertexLeftSet.add(new Vertex((long)i, RandomStringUtils.random(lenLeft, true, true), Partition.LEFT));
        }

        for (int i = 0; i < lenLeft; i++) {
            vertexRightSet.add(new Vertex((long)i, RandomStringUtils.random(lenRight, true, true), Partition.RIGHT));
        }

        biclique = new Biclique(vertexLeftSet, vertexRightSet);

        Set<Vertex> verticesL = biclique.getLeftSet();
        Set<Vertex> verticesR = biclique.getRightSet();
        assertEquals(vertexLeftSet, verticesL);
        assertEquals(vertexRightSet, verticesR);
    }

    @Test
    public void testContainsEdge(){
        Biclique biclique;
        Set<Vertex> vertexSet = new HashSet<>();
        Vertex[] verticesL = RandomGenerate.randomGenerateVertices(5, Partition.LEFT, vertexSet);
        Vertex[] verticesR = RandomGenerate.randomGenerateVertices(5, Partition.RIGHT, vertexSet);
        Vertex[] verticesLFake = RandomGenerate.randomGenerateVertices(10, Partition.LEFT, vertexSet);
        Vertex[] verticesRFake = RandomGenerate.randomGenerateVertices(10, Partition.RIGHT, vertexSet);

        biclique = new Biclique(new TreeSet<>(Arrays.asList(verticesL)), new TreeSet<>(Arrays.asList(verticesR)));

        assertEquals(true, biclique.containsEdge(new Edge(verticesL[0], verticesR[0])));
        assertEquals(false, biclique.containsEdge(new Edge(verticesLFake[5], verticesR[0])));
    }
}