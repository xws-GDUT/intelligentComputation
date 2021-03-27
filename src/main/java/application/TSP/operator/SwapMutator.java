package application.TSP.operator;

import application.TSP.pojo.Individual;
import intelligentComputation.Bound;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *@Description
 *@Author 许万森
 *@email wansenxu@163.com
 *交叉策略: 奇偶方案+二项式交叉  解决随机方案存在已经交叉后的个体继续与其他个体进行交叉
 */
@Data
@Accessors(chain = true)
public class SwapMutator extends Mutator<Individual> {
    private double rateOfMutation;
    @Override
    public void mutate(List<Individual> pop,double lowerBound,double upperBound) {
        if(pop.size()<=1){
            System.out.println("交叉失败");
            return;
        }
        for (int i = 0; i < pop.size(); i+=2) {
//            for (int j = 0; j < pop.get(0).getSolution().size(); j++) {
//                double p = new Random().nextDouble();
//                if(p < rateOfCrossover){
//                    int value = (int) pop.get(i+1).getSolution().get(j);
//                    int index1 =pop.get(i).getSolution().indexOf(value);
//                    Collections.swap(pop.get(i).getSolution(),j,index1);
//                    int value2 = (int) pop.get(i).getSolution().get(j);
//                    int index2 = pop.get(i+1).getSolution().indexOf(value2);
//                    Collections.swap(pop.get(i+1).getSolution(),j,index2);
//                }
//            }
            double p = new Random().nextDouble();
            if(p < rateOfMutation) {
                int positon = new Random().nextInt(31) + 1;
                int value = (int) pop.get(i).getSolution().get(positon);
                int index1 = pop.get(i).getSolution().indexOf(value);
                Collections.swap(pop.get(i).getSolution(),positon,index1);
            }
        }
    }
}
