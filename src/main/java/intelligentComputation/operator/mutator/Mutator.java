package intelligentComputation.operator.mutator;

import intelligentComputation.Bound;
import intelligentComputation.Individual;

import java.util.List;

/**
 *@Description 抽象的变异器
 *@Author 许万森
 *@email wansenxu@163.com
 */

public abstract class Mutator {
    public void updateFloatFactor(double value){

    }
    public double getFloatFactor(){
        return 0;
    }
    public abstract List<Individual> mutate(List<Individual> pop, Bound bound);
}
