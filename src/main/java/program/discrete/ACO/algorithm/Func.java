package program.discrete.ACO.algorithm;

import program.Evaluator;
import program.discrete.ACO.dataStructure.Ant;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class Func implements Evaluator<Ant> {
    @Override
    public double evaluate(Ant ant) {
        List<Integer> paths = ant.getPaths();
        double totalDistance = 0.0;
        for (int i = 0; i < paths.size(); i++) {
            if(i==paths.size()-1){ //回到起点
                totalDistance += Constant.GRAPH.getDistance(paths.get(i),paths.get(0));
            }else{
                totalDistance += Constant.GRAPH.getDistance(paths.get(i),paths.get(i+1));
            }
        }
        return totalDistance;
    }
}
