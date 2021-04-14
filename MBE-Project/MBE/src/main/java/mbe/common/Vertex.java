package mbe.common;

import java.util.HashMap;

/**
 * @Description: data structure for vertex in bipartite graph
 * 
 * @ClassName: Vertex
 * @author: Jiri Yu
 * @date: 2021/4/12 
 */
public class Vertex implements Comparable<Vertex>{
    private final Long id;
    private final String value;

    public Vertex(Long id){
        this.id = id;
        this.value = null;
    }

    public Vertex(Long id, String value){
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString(){
        return id.toString() + ":" + value;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        Vertex vertex = (Vertex) object;
        if(this.id == vertex.id && this.value == vertex.value){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(Vertex vertex){
        return this.value.compareTo(vertex.value);
    }
}
