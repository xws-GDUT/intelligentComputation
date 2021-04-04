package program.discrete.TSP;

import lombok.Data;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Edge{
    private int vid;   //边终点的id
    private double distance;  //边的长度
    private double pheromone = 1.0;  //信息素

}
