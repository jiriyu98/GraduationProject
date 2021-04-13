package mbe.common;

import java.util.Set;
import java.util.TreeSet;

/**
 * @Description: data structure for biclique in bipartite graph
 *
 * @ClassName: Biclique
 * @author: Jiri Yu
 * @date: 2021/4/9
 */
public class Biclique<T> {
    private Set<T> X;
    private Set<T> Y;

    public Biclique(){
        X = new TreeSet<>();
        Y = new TreeSet<>();
    }

    public Biclique(Set<T> X, Set<T> Y){
        X = new TreeSet<>();
        Y = new TreeSet<>();

        this.X.addAll(X);
        this.Y.addAll(Y);
    }

    public Set<T> getX() {
        return X;
    }

    public Set<T> getY() {
        return Y;
    }

    @Override
    public String toString(){
        return "<" + X + "," + Y + ">";
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        Biclique biclique = (Biclique)o;
        return X == biclique.X && Y == biclique.Y;
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
}
