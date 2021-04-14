package mbe.common;

/**
 * @Description: data structure for edge in bipartite graph
 *
 * @ClassName: Edge
 * @author: Jiri Yu
 * @date: 2021/4/4 
 */
public class Edge {
    private final Vertex X;
    private final Vertex Y;

    public Edge(Long X, Long Y){
        this.X = new Vertex(X);
        this.Y = new Vertex(Y);
    }

    public Edge(Vertex X, Vertex Y){
        this.X = X;
        this.Y = Y;
    }

    public Vertex getX() {
        return X;
    }

    public Vertex getY() {
        return Y;
    }

    @Override
    public String toString(){
        return X + " - " + Y;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        Edge edge = (Edge)object;
        if(X == edge.X && Y == edge.Y){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
}
