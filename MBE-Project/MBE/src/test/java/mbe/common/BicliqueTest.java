package mbe.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

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

        System.out.println(biclique.getLeftSet());
        System.out.println(biclique.getRightSet());
    }
}