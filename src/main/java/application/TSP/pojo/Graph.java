package application.TSP.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Graph {
    private List<Vertex> vertexList;

    public Vertex getVertex(String name){
        Vertex vertex = null;
        for (Vertex v : vertexList) {
            if(v.getName().equals(name)){
                vertex=v;
                break;
            }
        }
        return vertex;
    }
}


