package application.TSP.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vertex{
    private String name;
    private double x;
    private double y;
    private List<Edge> edgeList = new ArrayList<>();

    public Vertex(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    public void addEdge(Vertex vertex){
        if(!this.equals(vertex)){
            Edge edge = new Edge();
            edge.setDestVertex(vertex.getName());
            //计算欧式距离作为边的权值
            double detaX = this.x-vertex.getX();
            double detaY = this.y-vertex.getY();
            edge.setWeight(Math.sqrt(Math.pow(detaX,2)+Math.pow(detaY,2)));
            edgeList.add(edge);
        }
    }

    public double distanceFrom(String name){
        double distance = Double.MAX_VALUE;
        for (Edge e : edgeList) {
            if(e.getDestVertex().equals(name)){
                distance = e.getWeight();
            }
        }
        return distance;
    }
}
