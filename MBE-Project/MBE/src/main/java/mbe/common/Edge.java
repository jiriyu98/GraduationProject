package mbe.common;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * @description: data structure for edge in bipartite graph
 *
 * @className: Edge
 * @author: Jiri Yu
 * @date: 2021/4/4 
 */
public class Edge {
    @JsonUnwrapped(prefix = "L")
    private final Vertex left;
    @JsonUnwrapped(prefix = "R")
    private final Vertex right;

    public Edge(){
        this.left = null;
        this.right = null;
    }

    public Edge(Long left, Long right){
        this.left = new Vertex(left);
        this.right = new Vertex(right);
    }

    public Edge(Vertex left, Vertex right){
        this.left = left;
        this.right = right;
    }

    public Vertex getLeft() {
        return left;
    }

    public Vertex getRight() {
        return right;
    }

    @Override
    public String toString(){
        return "<" + left + " - " + right + ">";
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        Edge edge = (Edge)object;
        if(left.equals(edge.left) && right.equals(edge.right)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
}
