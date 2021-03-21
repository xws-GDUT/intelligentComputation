package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;

import java.util.List;
import java.util.Random;

/**
 * @Author xws
 * @email wansenxu@163.com、
 * 交叉策略：单点交叉
 */
public class OnePointCrossover extends Crossover {
    @Override
    public void cross(List<Individual> pop) {
        for (int i = 0; i < pop.size(); i+=2) {
            double p = new Random().nextDouble();
            if(p < rateOfCrossover){
                if(pop.get(i+1)==null){
                    break;
                }
                onePointCross(pop.get(i),pop.get(i+1));
            }
        }
    }

}
