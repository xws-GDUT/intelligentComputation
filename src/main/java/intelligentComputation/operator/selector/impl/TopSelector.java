package intelligentComputation.operator.selector.impl;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import intelligentComputation.Individual;
import intelligentComputation.operator.selector.Selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *@Description 从父代和子代中选择最优个体最为下一代个体
 *@Author 许万森
 *@email wansenxu@163.com
 */

public class TopSelector extends Selector {
    @Override
    public List<Individual> select(List<Individual> pop,List<Individual> offspring) {
        List<Individual> nextPop = new ArrayList<>();
        int size = pop.size();
        Collections.sort(pop);
        Collections.sort(offspring);
        int j=0,k=0;
        for (int i = 0; i < size; i++) {

            if(pop.get(j).compareTo(offspring.get(k))<0){
                nextPop.add(pop.get(j));
                j++;
            }else{
                nextPop.add(offspring.get(k));
                k++;
            }
        }
        pop.addAll(nextPop);

//        List<Individual> nextPop = pop.subList(0, size);
//        for (int i=0;i<pop.size();i++){
//            if(pop.get(i).getFitness()>offspring.get(i).getFitness()){
//                nextPop.add(offspring.get(i));
//            }else{
//                nextPop.add(pop.get(i));
//            }
//        }
        return nextPop;
    }
}
