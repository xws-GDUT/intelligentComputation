package program.func;

import program.Evaluator;
import program.discrete.TSP.Constant;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class DistanceFunc implements Evaluator<Integer> {
    private int numOfEvaluate = 0;

    @Override
    public double evaluate(List<Integer> solution) {
//        List<Integer> paths = ant.getPaths();
        numOfEvaluate++;
        double totalDistance = 0.0;
        for (int i = 0; i < solution.size(); i++) {
            if(i==solution.size()-1){ //回到起点
                totalDistance += Constant.GRAPH.getDistance(solution.get(i),solution.get(0));
            }else{
                totalDistance += Constant.GRAPH.getDistance(solution.get(i),solution.get(i+1));
            }
        }
        return totalDistance;
    }

    @Override
    public int getNumOfEvaluate() {
        return numOfEvaluate;
    }

}
