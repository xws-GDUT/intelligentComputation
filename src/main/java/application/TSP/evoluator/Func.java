package application.TSP.evoluator;


import application.TSP.pojo.Graph;
import application.TSP.pojo.Vertex;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/12/10
 */
public class Func implements Evaluator<Individual> {
    private static Graph GRAPH = null;
    static {
        Graph graph = new Graph();
        List<Vertex> vertexList = new ArrayList<>();
        Vertex vertex1 = new Vertex("1",1304.0,2312.0);
        Vertex vertex2 = new Vertex("2",3639.0,1315.0);
        Vertex vertex3 = new Vertex("3",4177.0,2244.0);
        Vertex vertex4 = new Vertex("4",3712.0,1399.0);
        Vertex vertex5 = new Vertex("5",3488.0,1535.0);
        Vertex vertex6 = new Vertex("6",3326.0,1556.0);
        Vertex vertex7 = new Vertex("7",3238.0,1229.0);
        Vertex vertex8 = new Vertex("8",4196.0,1044.0);
        Vertex vertex9 = new Vertex("9",4312.0,790.0);
        Vertex vertex10 = new Vertex("10",4386.0,570.0);
        Vertex vertex11 = new Vertex("11",3007.0,1970.0);
        Vertex vertex12 = new Vertex("12",2562.0,1756.0);
        Vertex vertex13 = new Vertex("13",2788.0,1491.0);
        Vertex vertex14 = new Vertex("14",2381.0,1676.0);
        Vertex vertex15 = new Vertex("15",1332.0,695.0);
        Vertex vertex16 = new Vertex("16",3715.0,1678.0);
        Vertex vertex17 = new Vertex("17",3918.0,2179.0);
        Vertex vertex18 = new Vertex("18",4061.0,2370.0);
        Vertex vertex19 = new Vertex("19",3780.0,2212.0);
        Vertex vertex20 = new Vertex("20",3676.0,2578.0);
        Vertex vertex21 = new Vertex("21",4029.0,2838.0);
        Vertex vertex22 = new Vertex("22",4263.0,2931.0);
        Vertex vertex23 = new Vertex("23",3429.0,1908.0);
        Vertex vertex24 = new Vertex("24",3507.0,2376.0);
        Vertex vertex25 = new Vertex("25",3394.0,2643.0);
        Vertex vertex26 = new Vertex("26",3439.0,3201.0);
        Vertex vertex27 = new Vertex("27",2935.0,3240.0);
        Vertex vertex28 = new Vertex("28",3140.0,3550.0);
        Vertex vertex29 = new Vertex("29",2545.0,2357.0);
        Vertex vertex30 = new Vertex("30",2778.0,2826.0);
        Vertex vertex31 = new Vertex("31",2370.0,2975.0);

        vertexList.add(vertex1);
        vertexList.add(vertex2);
        vertexList.add(vertex3);
        vertexList.add(vertex4);
        vertexList.add(vertex5);
        vertexList.add(vertex6);
        vertexList.add(vertex7);
        vertexList.add(vertex8);
        vertexList.add(vertex9);
        vertexList.add(vertex10);
        vertexList.add(vertex11);
        vertexList.add(vertex12);
        vertexList.add(vertex13);
        vertexList.add(vertex14);
        vertexList.add(vertex15);
        vertexList.add(vertex16);
        vertexList.add(vertex17);
        vertexList.add(vertex18);
        vertexList.add(vertex19);
        vertexList.add(vertex20);
        vertexList.add(vertex21);
        vertexList.add(vertex22);
        vertexList.add(vertex23);
        vertexList.add(vertex24);
        vertexList.add(vertex25);
        vertexList.add(vertex26);
        vertexList.add(vertex27);
        vertexList.add(vertex28);
        vertexList.add(vertex29);
        vertexList.add(vertex30);
        vertexList.add(vertex31);

        graph.setVertexList(vertexList);

        for (int i = 0; i < vertexList.size(); i++) {
            for (int j = 0; j < vertexList.size(); j++) {
                vertexList.get(i).addEdge(vertexList.get(j));
            }
        }
        GRAPH = graph;
    }

    @Override
    public void evaluate(List<Individual> pop) {
        for (int i = 0; i < pop.size(); i++) {
            Individual individual = pop.get(i);
            List<Double> solution = individual.getSolution();
            double totalDistance = 0.0;
            for (int j = 0; j < solution.size()-1; j++) {
                Vertex vertex = GRAPH.getVertex(String.valueOf(solution.get(j).intValue()));
                double dist = vertex.distanceFrom(String.valueOf(solution.get(j + 1).intValue()));
                totalDistance+=dist;
            }
            individual.setFitness(totalDistance);

        }
    }

}
