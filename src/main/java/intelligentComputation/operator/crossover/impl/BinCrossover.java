package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *@Description 二项式交叉
 *@Author 许万森
 *@email wansenxu@163.com
 */



public class BinCrossover extends Crossover{
//    private double rateOfCrossover;
    @Override
    public List<Individual> cross(List<Individual> pop,List<Individual> mutatedPop) {
        List<Individual> offspring = new ArrayList<>();
        for(int i=0;i<mutatedPop.size();i++){
            int popisitonOfCrossover = new Random().nextInt(pop.get(0).getSolution().size());
//            Individual tmpIndividual = mutatedPop.get(i);
            Individual individual = new Individual();
            List<Double> solution = new ArrayList<>();
            for(int j = 0; j< pop.get(0).getSolution().size(); j++){
                double p = Math.random();
                if( p > rateOfCrossover && popisitonOfCrossover != j){
                    solution.add(j,pop.get(i).getSolution().get(j));
                }else{
                    solution.add(j,mutatedPop.get(i).getSolution().get(j));
                }
            }
            individual.setSolution(solution);
            offspring.add(individual);
        }
        return offspring;
    }
}
