package program.discrete.ACO.algorithm;

import program.discrete.ACO.dataStructure.Graph;
import program.discrete.ACO.dataStructure.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class Constant {
    public static Graph GRAPH;
    public static void initGraph(){
        GRAPH = new Graph();
        List<Vertex> vertexes = new ArrayList<>();
        vertexes.add( new Vertex( 1,1304.0,2312.0));
        vertexes.add( new Vertex( 2 ,3639.0,1315.0));
        vertexes.add( new Vertex( 3 ,4177.0,2244.0));
        vertexes.add( new Vertex( 4 ,3712.0,1399.0));
        vertexes.add( new Vertex( 5 ,3488.0,1535.0));
        vertexes.add( new Vertex( 6 ,3326.0,1556.0));
        vertexes.add( new Vertex( 7 ,3238.0,1229.0));
        vertexes.add( new Vertex( 8 ,4196.0,1044.0));
        vertexes.add( new Vertex( 9 ,4312.0,790.0));
        vertexes.add( new Vertex( 10 ,4386.0,570.0));
        vertexes.add( new Vertex( 11 ,3007.0,1970.0));
        vertexes.add( new Vertex( 12 ,2562.0,1756.0));
        vertexes.add( new Vertex( 13 ,2788.0,1491.0));
        vertexes.add( new Vertex( 14 ,2381.0,1676.0));
        vertexes.add( new Vertex( 15 ,1332.0,695.0));
        vertexes.add( new Vertex( 16 ,3715.0,1678.0));
        vertexes.add( new Vertex( 17 ,3918.0,2179.0));
        vertexes.add( new Vertex( 18 ,4061.0,2370.0));
        vertexes.add( new Vertex( 19 ,3780.0,2212.0));
        vertexes.add( new Vertex( 20 ,3676.0,2578.0));
        vertexes.add( new Vertex( 21 ,4029.0,2838.0));
        vertexes.add( new Vertex( 22 ,4263.0,2931.0));
        vertexes.add( new Vertex( 23 ,3429.0,1908.0));
        vertexes.add( new Vertex( 24 ,3507.0,2376.0));
        vertexes.add( new Vertex( 25 ,3394.0,2643.0));
        vertexes.add( new Vertex( 26 ,3439.0,3201.0));
        vertexes.add( new Vertex( 27 ,2935.0,3240.0));
        vertexes.add( new Vertex( 28 ,3140.0,3550.0));
        vertexes.add( new Vertex( 29 ,2545.0,2357.0));
        vertexes.add( new Vertex( 30 ,2778.0,2826.0));
        vertexes.add( new Vertex( 31 ,2370.0,2975.0));

        for (int i = 0; i <  vertexes.size(); i++) {
            for (int j = 0; j <  vertexes.size(); j++) {
                vertexes.get(i).addEdge(vertexes.get(j));
            }
        }
        GRAPH.setVertexList(vertexes);
    }
}
