package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;

import java.util.List;
import java.util.Random;

/**
 *@Description
 *@Author 许万森
 *@email wansenxu@163.com
 *交叉策略: 随机方案+二项式交叉
 * 该策略存在问题：随机方案存在已经交叉后的个体继续与其他个体进行交叉
 */

public class RandCrossover extends Crossover {
    @Override
    public void cross(List<Individual> pop) {
        for (int i = 0; i < pop.size(); i++) {
            //随机生成一个与当前个体交叉的个体的下标
            int position;
            do{
                position = new Random().nextInt(pop.get(0).getSolution().size());
            }while (i==position);
            double p = new Random().nextDouble();
            if(p < rateOfCrossover){
                binomialCross(pop.get(i),pop.get(position));
            }
        }
    }
}
