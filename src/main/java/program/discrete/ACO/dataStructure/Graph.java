package program.discrete.ACO.dataStructure;

import lombok.Data;
import program.discrete.ACO.algorithm.Constant;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Graph {
    private List<Vertex> vertexList;

    public Vertex getVertex(int vid){
        Vertex vertex = null;
        for (Vertex v : vertexList) {
            if(v.getVid() == (vid)){
                vertex=v;
                break;
            }
        }
        return vertex;
    }
    public double getDistance(int v1,int v2){
        double distance = Double.MAX_VALUE;
        for (Vertex vertex : vertexList) {
            if(vertex.getVid() == v1){
                distance = vertex.getDistance(v2);
            }
        }
        return distance;
    }

    public Edge getEdge(int v1,int v2){
        Edge edge = null;
        for (Vertex vertex : vertexList) {
            if(vertex.getVid() == v1){
                edge = vertex.getEdge(v2);
            }
        }
        return edge;
    }

    public void updatePhoromone(List<List<Integer>> paths,double rho,double Q){
//        for (int i = 0; i < paths.size(); i++) {
//            if(i == paths.size()-1){
//                Edge edge = getEdge(paths.get(i), 0);
//
//                edge.setPheromone((1-rho)*edge.getPheromone());
//            }else{
//                Edge edge = getEdge(paths.get(i), paths.get(i+1));
//            }
//        }
        List<Vertex> vertexes = Constant.GRAPH.getVertexList();
        for (Vertex vertex : vertexes) {
            List<Edge> edges = vertex.getEdges();
            for (Edge edge : edges) {
                edge.setPheromone( edge.getPheromone()*(1-rho));
            }
        }
        for (List<Integer> path : paths) {
            for (int i = 0; i < path.size(); i++) {
                Edge edge = null;
                if(i == path.size()-1){
                    edge = Constant.GRAPH.getEdge(path.get(i),path.get(0));
                }else{
                    edge = Constant.GRAPH.getEdge(path.get(i),path.get(i+1));
                }
                edge.setPheromone( edge.getPheromone() + Q/edge.getDistance());
            }
        }
    }
}


