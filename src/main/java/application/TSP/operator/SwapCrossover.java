package application.TSP.operator;

import application.TSP.pojo.Individual;
import intelligentComputation.operator.crossover.Crossover;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *@Description
 *@Author 许万森
 *@email wansenxu@163.com
 *交叉策略: 奇偶方案+二项式交叉  解决随机方案存在已经交叉后的个体继续与其他个体进行交叉
 */

public class SwapCrossover extends Crossover<Individual> {
    @Override
    public void cross(List<Individual> pop) {
        if(pop.size()<=1){
            System.out.println("交叉失败");
            return;
        }

        int lowerBound = 0;
        int upperBound = 0;
        if(pop.get(0)!=null){
            lowerBound = (int) pop.get(0).getLowBound();
            upperBound = (int) pop.get(0).getUpperBound();
        }
        for (int i = 0; i < pop.size(); i+=2) {
            for (int j = 0; j < pop.get(0).getSolution().size(); j++) {
                double p = new Random().nextDouble();
                if(p < rateOfCrossover){
                    int value1 = (int) pop.get(i+1).getSolution().get(j);
                    int value2 = (int) pop.get(i).getSolution().get(j);
                    if(value1 == value2){
                        break;
                    }

                    int index1 =pop.get(i).getSolution().indexOf(value1);
                    int index2 = pop.get(i+1).getSolution().indexOf(value2);
                    if(j==0){
                        int value3 = (int)pop.get(i).getSolution().get(j);
                        pop.get(i).getSolution().set(0,value1);
                        pop.get(i).getSolution().set(pop.get(i).getSolution().size()-1,value1);
                        pop.get(i).getSolution().set(index1,value3);

                        int value4 = (int)pop.get(i+1).getSolution().get(j);
                        pop.get(i+1).getSolution().set(0,value2);
                        pop.get(i+1).getSolution().set(pop.get(i+1).getSolution().size()-1,value2);
                        pop.get(i+1).getSolution().set(index2,value4);
                    }
                    if(index1 == 0){
                        int value3 = (int)pop.get(i).getSolution().get(j);
                        pop.get(i).getSolution().set(0,value3);
                        pop.get(i).getSolution().set(pop.get(i).getSolution().size()-1,value3);
                        pop.get(i).getSolution().set(j,value1);
                    }else {
                        Collections.swap(pop.get(i).getSolution(),j,index1);
                        Collections.swap(pop.get(i+1).getSolution(),j,index2);
                    }
                    if(index2 == 0){
                        int value4 = (int)pop.get(i+1).getSolution().get(j);
                        pop.get(i+1).getSolution().set(0,value4);
                        pop.get(i+1).getSolution().set(pop.get(i+1).getSolution().size()-1,value4);
                        pop.get(i+1).getSolution().set(j,value2);
                    }else{
                        Collections.swap(pop.get(i).getSolution(),j,index1);
                        Collections.swap(pop.get(i+1).getSolution(),j,index2);
                    }

                    //bug
                }
            }
        }
    }
}
