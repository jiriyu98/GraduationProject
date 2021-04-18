package mbe.common;

import java.util.Set;
import java.util.TreeSet;

/**
 * @description: data structure for biclique in bipartite graph
 * Pay attention, we use Tree set there, which is auto-sorted in order of Vertex.value,
 * because we want to implement hashcode() method easier.
 *
 * @className: Biclique
 * @author: Jiri Yu
 * @date: 2021/4/9
 */
public class Biclique {
    private Set<Vertex> leftSet;
    private Set<Vertex> rightSet;

    public Biclique(){
        leftSet = new TreeSet<>();
        rightSet = new TreeSet<>();
    }

    public Biclique(Set<Vertex> leftSet, Set<Vertex> rightSet){
        this.leftSet = new TreeSet<>();
        this.rightSet = new TreeSet<>();

        this.leftSet.addAll(leftSet);
        this.rightSet.addAll(rightSet);
    }

    public Set<Vertex> getLeftSet() {
        return this.leftSet;
    }

    public Set<Vertex> getRightSet() {
        return this.rightSet;
    }

    @Override
    public String toString(){
        return "<" + this.leftSet + "," + this.rightSet + ">";
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        Biclique biclique = (Biclique)o;
        return this.leftSet.equals(biclique.leftSet) && this.rightSet.equals(biclique.rightSet);
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
}
