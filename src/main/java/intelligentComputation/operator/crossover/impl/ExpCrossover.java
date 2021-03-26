package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *@Description 指数交叉
 *@Author 许万森
 *@email wansenxu@163.com
 */



public class ExpCrossover extends Crossover<Individual> {
    @Override
    public List cross(List<Individual> pop, List<Individual> mutatedPop) {
        List<Individual> offspring = new ArrayList<>();
        for(int i=0;i<pop.size();i++){
            Individual individual = new Individual();
            List<Double> solution = new ArrayList<>();
            int popisitonOfCrossover = new Random().nextInt(pop.get(0).getSolution().size());
            int k=0;
            for(k=0;k<popisitonOfCrossover;k++){
                solution.add((Double) pop.get(i).getSolution().get(k));
            }
            solution.add((Double) mutatedPop.get(i).getSolution().get(k));
            for(k= k+1 ;k< pop.get(0).getSolution().size(); k++) {
                double p = Math.random();
                if (p <= rateOfCrossover) {
                    solution.add((Double) mutatedPop.get(i).getSolution().get(k));
                }else{
                    break;
                }
            }
            while(k < pop.get(0).getSolution().size()){
                solution.add((Double) pop.get(i).getSolution().get(k));
                k++;
            }

            individual.setSolution(solution);
            offspring.add(individual);
        }
        return offspring;
    }
}
