package application.TSP.operator;

import intelligentComputation.Bound;

import java.util.List;

/**
 *@Description 抽象的变异器
 *@Author 许万森
 *@email wansenxu@163.com
 */

public abstract class Mutator<T> {
    public void updateFloatFactor(double value){

    }
    public double getFloatFactor(){
        return 0;
    }
    public abstract void mutate(List<T> pop, double lowerBound,double upperBound);
}