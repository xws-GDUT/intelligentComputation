package program.discrete.TSP;

import lombok.Data;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Graph {
    private List<Vertex> vertexList;

    /**
     * 根据顶底id查找顶点
     * @param vid
     * @return
     */
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

    /**
     * 计算两个顶点间的距离
     * @param v1
     * @param v2
     * @return
     */
    public double getDistance(int v1,int v2){
        double distance = Double.MAX_VALUE;
        for (Vertex vertex : vertexList) {
            if(vertex.getVid() == v1){
                distance = vertex.getDistance(v2);
            }
        }
        return distance;
    }

    /**
     * 获取两个点间的边
     * @param v1
     * @param v2
     * @return
     */
    public Edge getEdge(int v1, int v2){
        Edge edge = null;
        for (Vertex vertex : vertexList) {
            if(vertex.getVid() == v1){
                edge = vertex.getEdge(v2);
            }
        }
        return edge;
    }

    /**
     * 根据周游的路径更新边上的信息素
     * @param paths
     * @param rho
     * @param Q
     */
    public void updatePhoromone(List<List<Integer>> paths,double rho,double Q){
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


