package mbe.common;

import java.io.Serializable;

/**
 * @description: data structure for vertex in bipartite graph
 * 
 * @className: Vertex
 * @author: Jiri Yu
 * @date: 2021/4/12 
 */
public class Vertex implements Comparable<Vertex>, Serializable {
    private final Long id;
    private final String value;
    private final String partition;

    public Vertex(){
        this.id = null;
        this.value = null;
        this.partition = null;
    }

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
        if(this.id.equals(vertex.id) && this.value.equals(vertex.value) && this.partition.equals(vertex.partition)){
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
        // TODO(jiriyu): it may be costly, can I find a better way?
        int cmp = this.value.compareTo(vertex.value);
        if(cmp == 0){
            return this.id.compareTo(vertex.id);
        }
        return cmp;
    }
}
