package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Author xws
 * @email wansenxu@163.com
 * 交叉策略：君主方案+二项式交叉
 */

public class EmperorCrossover extends Crossover {
    @Override
    public void cross(List<Individual> pop) {
        Individual bestIndividual = Collections.min(pop).clone();
        for (int i = 0; i < pop.size(); i++) {
            double p = new Random().nextDouble();
            if(p < rateOfCrossover){
                Individual cloneBestIndividual = bestIndividual.clone();
                binomialCross(cloneBestIndividual,pop.get(i));
            }
        }
    }
}
