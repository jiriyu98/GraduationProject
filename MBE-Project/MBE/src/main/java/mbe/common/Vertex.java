package mbe.common;

/**
 * @Description: data structure for vertex in bipartite graph
 * 
 * @ClassName: Vertex
 * @author: Jiri Yu
 * @date: 2021/4/12 
 */
public class Vertex<T> {
    private final T id;

    public Vertex(T id){
        this.id = id;
    }

    @Override
    public String toString(){
        return id.toString();
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        Vertex<T> vertex = (Vertex<T>) object;
        if(this.id == vertex.id){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
}
