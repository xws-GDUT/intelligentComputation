package program.discrete.ACO.dataStructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vertex {
    private int vid;
    private double x;
    private double y;
    private List<Edge> edges = new ArrayList<>();

    public Vertex(int vid, double x, double y) {
        this.vid = vid;
        this.x = x;
        this.y = y;
    }
    public void addEdge(Vertex vertex){
        if(!this.equals(vertex)){
            Edge edge = new Edge();
            edge.setVid(vertex.getVid());
            //计算欧式距离作为边的权值
            double detaX = this.x-vertex.getX();
            double detaY = this.y-vertex.getY();
            edge.setDistance(Math.sqrt(Math.pow(detaX,2)+Math.pow(detaY,2)));
            edges.add(edge);
        }
    }

    /**
     * 获取两个顶点间的距离
     * @param vid 目的顶底id
     * @return
     */
    public double getDistance(int vid){
        double distance = Double.MAX_VALUE;
        for (Edge e : edges) {
            if(e.getVid()==(vid)){
                distance = e.getDistance();
            }
        }
        return distance;
    }


    /**
     * 获取两个顶点间边上存在的信息素
     * @param vid
     * @return
     */
    public double getPheromone(int vid){
        double pheromone = 0.0;
        for (Edge edge : edges) {
            if(edge.getVid() == vid){
                pheromone = edge.getPheromone();
            }
        }
        return  pheromone;
    }

    public Edge getEdge(int vid){
        Edge edge = null;
        for (Edge e : edges) {
            if(e.getVid() == vid){
                edge = e;
            }
        }
        return edge;
    }
}
