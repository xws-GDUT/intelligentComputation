package application.TSP.operator;

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

public class SwapCrossover extends Crossover {
    @Override
    public void cross(List<Individual> pop) {
        for (int i = 0; i < pop.size(); i+=2) {
            double p = new Random().nextDouble();
            if(p < rateOfCrossover){
                int pos1 = (int)(new Random().nextDouble()*30+1);
                int pos2 = (int)(new Random().nextDouble()*30+1);
                List<Integer> solution1 = pop.get(i).getSolution();
                List<Integer> solution2 = pop.get(i+1).getSolution();
                int index = solution1.indexOf(Integer.valueOf(pos1));
//                solution1.set(pos1,index);
//                solution1.set(index,pos1);
                //待完成
                solution2.indexOf(Integer.valueOf(pos2));

                if (pop.get(i+1)==null){
                    break;
                }
                binomialCross(pop.get(i),pop.get(i+1));
            }
        }

    }
//    public void swap()
}
