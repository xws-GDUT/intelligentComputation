package program.func;

import program.Evaluator;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class F1 implements Evaluator<Double>{
    public double evaluate(List<Double> solution) {
        double sum = 0;
        for (int j = 0; j < solution.size(); j++) {
            sum+= Math.pow(solution.get(j),2);
        }
        return sum;
    }

}
