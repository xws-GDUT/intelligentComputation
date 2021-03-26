package intelligentComputation.operator.mutator.impl;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.operator.mutator.Mutator;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *@Description 基于最优个体进行差分变异
 *@Author 许万森
 *@email wansenxu@163.com
 */

@Data
@Accessors(chain = true)
public class BestDifferentMutator extends Mutator<Individual> {
    private int differentVector;
    private double floatFactor;
//    private Bound bound;

    @Override
    public List<Individual> mutate(List<Individual> pop, Bound bound) {
        List<Individual> mutatedPop = new ArrayList<>();
        Individual bestIndividual = null;
        bestIndividual =  Collections.min(pop).clone();
        switch (differentVector){
            case 1:
                for (int i = 0; i < pop.size(); i++) {
                    Individual individual = new Individual();
                    List<Double> solution = new ArrayList<>();
                    List<Integer> indexes = getRamdonExcludeNum(pop.size(),2,i);
                    for (int j = 0; j < pop.get(0).getSolution().size(); j++) {
                        double best = (double) bestIndividual.getSolution().get(j);
                        double i1= (double) pop.get(indexes.get(0)).getSolution().get(j);
                        double i2= (double) pop.get(indexes.get(1)).getSolution().get(j);
                        double value =best + floatFactor * (i1 - i2);
                        value=dealWithOutOfBound(value,bound);
                        solution.add(value);
                    }
                    individual.setSolution(solution);
                    mutatedPop.add(individual);
                }
                break;
            case 2:
                for (int i = 0; i < pop.size(); i++) {
                    Individual individual = new Individual();
                    List<Double> solution = new ArrayList<Double>();
                    List<Integer> indexes = getRamdonExcludeNum(pop.size(), 4, i);
                    for (int j = 0; j < pop.get(0).getSolution().size(); j++) {
                        double best = (double) bestIndividual.getSolution().get(j);
                        double i1= (double) pop.get(indexes.get(0)).getSolution().get(j);
                        double i2= (double) pop.get(indexes.get(1)).getSolution().get(j);
                        double i3= (double) pop.get(indexes.get(2)).getSolution().get(j);
                        double i4= (double) pop.get(indexes.get(3)).getSolution().get(j);
                        double value = best+floatFactor*(i1+i2-i3-i4);
                        value = dealWithOutOfBound(value, bound);
                        solution.add(value);
                    }
                    individual.setSolution(solution);
                    mutatedPop.add(individual);
                }
                break;
        }
        return mutatedPop;
    }

    public List<Integer> getRamdonExcludeNum(int bound,int amount,int excluded){
        List list = new ArrayList();
        list.add(excluded);
        while(list.size()<amount+1){
            int i = new Random().nextInt(bound);
            if(!list.contains(Integer.valueOf(i))){
                list.add(i);
            }
        }
        list.remove(Integer.valueOf(excluded));
        return list;
    }
    public double dealWithOutOfBound(Double value, Bound bound){
//        if(value<lowerBoundary ){
//            value = lowerBoundary;
//        }else if(value > upperBoundary) {
//            value = upperBoundary;
//        }
        double lowerBound = (double) bound.getLowerBound();
        double upperBound = (double) bound.getUpperBound();
        if(value<lowerBound || value > upperBound){
            value = lowerBound+new Random().nextDouble()*(upperBound-lowerBound);
        }
        return value;
    }
}
