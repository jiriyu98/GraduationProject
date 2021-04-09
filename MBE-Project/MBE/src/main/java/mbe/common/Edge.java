package mbe.common;

/**
 * @Description: 
 * @ClassName: Edge
 * @author: Jiri Yu
 * @date: 2021/4/4 
 */
public class Edge {
    private static long countEdge = 0L;
    private final long X;
    private final long Y;

    public Edge(long X, long Y){
        this.X = X;
        this.Y = Y;
    }

    public long getX() {
        return X;
    }

    public long getY() {
        return Y;
    }

    public long getCountEdge(){
        this.countEdge += 1;
        return this.countEdge;
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


}
