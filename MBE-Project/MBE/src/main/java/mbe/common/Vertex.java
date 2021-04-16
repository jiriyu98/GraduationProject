package mbe.common;

import java.util.HashMap;

/**
 * @description: data structure for vertex in bipartite graph
 * 
 * @className: Vertex
 * @author: Jiri Yu
 * @date: 2021/4/12 
 */
public class Vertex implements Comparable<Vertex>{
    private final Long id;
    private final String value;
    private final String partition;

    public Vertex(Long id){
        this.id = id;
        this.value = "";
        this.partition = Partition.NONE;
    }

    public Vertex(Long id, String value){
        this.id = id;
        this.value = value;
        this.partition = Partition.NONE;
    }

    public Vertex(Long id, String value, String partition){
        this.id = id;
        this.value = value;
        this.partition = partition;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getPartition() {
        return partition;
    }

    @Override
    public String toString(){
        return id.toString() + ":" + value + partition;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        Vertex vertex = (Vertex) object;
        if(this.id == vertex.id && this.value == vertex.value && this.partition == vertex.partition){
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
