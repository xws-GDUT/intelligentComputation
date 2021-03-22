package intelligentComputation.operator.mutator.impl;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.operator.mutator.Mutator;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Random;

/**
 *@Description 对解空间要变异的分量在取值范围内随机赋值
 *@Author 许万森
 *@email wansenxu@163.com
 */

@Data
@Accessors(chain = true)
public class RandMutator extends Mutator {
    private double rateOfMutation;
    @Override
    public List<Individual> mutate(List<Individual> pop, Bound bound) {
        double lowerBound =  bound.getLowerBound();
        double upperBound =  bound.getUpperBound();
        for (int i = 0; i < pop.size(); i++) {
            for (int j = 0; j < pop.get(i).getSolution().size(); j++) {
                double p = Math.random();
                if (p < rateOfMutation) {
                    double value = lowerBound+new Random().nextDouble()*(upperBound-lowerBound);
                    pop.get(i).getSolution().set(j,value);
                }
            }
        }
        return pop;
    }
}
