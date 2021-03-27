package ga.selector;

import ga.Chromosome;
import intelligentComputation.operator.selector.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *@Description 通过轮盘赌的方式选择下一代个体
 *@Author 许万森
 *@email wansenxu@163.com
 */

public class RouletteSelector extends Selector<Chromosome> {

    @Override
    public List<Chromosome> select(List<Chromosome> pop) {
        List<Chromosome> offspring = new ArrayList<>();;

        Double ReciprocalOfSumFit = 0.0;
        for (int i = 0; i < pop.size(); i++) {
            ReciprocalOfSumFit += 1.0 / pop.get(i).getFitness();
        }
        //计算每个个体被选中的概率
        List<Double> probability = new ArrayList<Double>();
        for (int i = 0; i < pop.size(); i++) {
            probability.add(1.0 / pop.get(i).getFitness() / ReciprocalOfSumFit);
        }

        for (int i = 0; i < pop.size(); i++) {
            int k = -1; //轮盘选择到的个体的下标
            double p = new Random().nextDouble();
            double sumProbability = 0;
            for (int j = 0; j < pop.size(); j++) {
                sumProbability+=probability.get(j);
                if(sumProbability>p){
                    k=j;
                    break;
                }
            }
            offspring.add(pop.get(k).clone());
        }
        return offspring;

    }
}
