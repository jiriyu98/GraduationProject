package mbe.common;

/**
 * @Description: data structure for edge in bipartite graph
 *
 * @ClassName: Edge
 * @author: Jiri Yu
 * @date: 2021/4/4 
 */
public class Edge<T> {
    private final Vertex<T> X;
    private final Vertex<T> Y;

    public Edge(T X, T Y){
        this.X = new Vertex<>(X);
        this.Y = new Vertex<>(Y);
    }

    public Edge(Vertex<T> X, Vertex<T> Y){
        this.X = X;
        this.Y = Y;
    }

    public Vertex<T> getX() {
        return X;
    }

    public Vertex<T> getY() {
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
