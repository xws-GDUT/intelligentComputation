package evaluator.impl;

import evaluator.Evaluator;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class F1 implements Evaluator<Double>{
    private int numOfEvaluate = 0;

    public double evaluate(List<Double> solution) {
        numOfEvaluate++; //累计评价次数
        double sum = 0;
        for (int j = 0; j < solution.size(); j++) {
            sum+= Math.pow(solution.get(j),2);
        }
        return sum;
    }

    public int getNumOfEvaluate() {
        return numOfEvaluate;
    }
}
