package evaluator.impl;

import evaluator.Evaluator;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */

public class F1 implements Evaluator<Double>{
    private int numOfEvaluate = 0;
    private double lowerBound;
    private double upperBound;
    private int dimension;

    public F1(double lowerBound, double upperBound,int dimension) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.dimension = dimension;
    }

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

    @Override
    public double getUpperBound() {
        return upperBound;
    }

    @Override
    public double getLowerBound() {
        return lowerBound;
    }

    @Override
    public int getDimension() {
        return dimension;
    }
}
