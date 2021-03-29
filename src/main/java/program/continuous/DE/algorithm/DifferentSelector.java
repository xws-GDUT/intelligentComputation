package program.continuous.DE.algorithm;


import program.Selector;
import program.continuous.DE.dataStructure.Chromosome;

import java.util.ArrayList;
import java.util.List;

/**
 *@Description 从父代和子代中选择最优个体最为下一代个体
 *@Author 许万森
 *@email wansenxu@163.com
 */

public class DifferentSelector implements Selector<Chromosome> {
    @Deprecated
    public List<Chromosome> select(List<Chromosome> pop) {
        return null;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> pop, List<Chromosome> offspring) {
        List<Chromosome> nextPop=new ArrayList<>();
        for (int i=0;i<pop.size();i++){
            if(pop.get(i).getFitness()>offspring.get(i).getFitness()){
                nextPop.add(offspring.get(i));
            }else{
                nextPop.add(pop.get(i));
            }
        }
        return nextPop;
    }
}