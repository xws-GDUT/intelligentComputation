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
public class OnePointCrossover extends Crossover<Individual> {
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

    /**
     * 单点交叉   开始交叉的点通过随机生成
     * @param i1
     * @param i2
     */
    private void onePointCross(Individual i1,Individual i2){
        int point = new Random().nextInt(i1.getSolution().size());
        for (int i = point; i < i1.getSolution().size(); i++) {
            double temp = (double) i1.getSolution().get(i);
            i1.getSolution().set(i,i2.getSolution().get(i));
            i2.getSolution().set(i,temp);
        }
    }

}
