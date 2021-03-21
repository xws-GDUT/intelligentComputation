package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;

import java.util.List;
import java.util.Random;

/**
 *@Description
 *@Author 许万森
 *@email wansenxu@163.com
 *交叉策略: 奇偶方案+二项式交叉  解决随机方案存在已经交叉后的个体继续与其他个体进行交叉
 */

public class DefaultCrossover extends Crossover {
    @Override
    public void cross(List<Individual> pop) {
        for (int i = 0; i < pop.size(); i+=2) {
            double p = new Random().nextDouble();
            if(p < rateOfCrossover){
                if (pop.get(i+1)==null){
                    break;
                }
                binomialCross(pop.get(i),pop.get(i+1));
            }
        }
    }
}
