package intelligentComputation.optimizer.impl;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.operator.crossover.Crossover;
import intelligentComputation.operator.mutator.Mutator;
import intelligentComputation.operator.selector.Selector;
import intelligentComputation.optimizer.Optimizer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
@Accessors(chain = true)
public class GeneticAlgorithmOptimizer extends Optimizer<Individual> {

    private Selector selector;
    private Mutator mutator;
    private Crossover crossover;

    /**
     * 通过遗传算法得到最优个体
     * @param popSize 种群的大小
     * @param dimension 个体的维度
     * @param iterations 迭代次数
     * @param evaluator 目标函数
     * @param bound 个体的取值范围
     * @return 种群每一代的收敛情况
     */
    @Override
    public List<Individual> optimize(int popSize, int dimension, int iterations, Evaluator evaluator, Bound bound) {
        List<Individual> bestPerGeneration = new ArrayList<>();
        List<Individual> convergence = new ArrayList<>();
        List<Individual> pop = initPop(popSize, bound, dimension);

        evaluator.evaluate(pop);
        for (int i = 0; i < iterations; i++) {
            Individual clonedBestIndividual = null;
            bestPerGeneration.add( Collections.min(pop).clone());
            clonedBestIndividual =  Collections.min(bestPerGeneration).clone();
            convergence.add(clonedBestIndividual);
            String info = i+1+"\t"+clonedBestIndividual.getFitness();
            System.out.println(info);

            List<Individual> offspring = selector.select(pop);
            crossover.cross(offspring);
            mutator.mutate(offspring,bound);
            evaluator.evaluate(offspring);
            pop=offspring;
        }
//        List<Double> collect = bestPerGeneration.stream().map(Individual::getFitness).collect(Collectors.toList());
//        System.out.println(collect);
        return convergence;
    }
    public List<Individual> initPop(int popsize, Bound<Double> bound, int dimension) {
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < popsize; i++) {
            Individual individual = new Individual();
            List<Double> solution = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                solution.add(bound.getLowerBound()+new Random().nextDouble()*(bound.getUpperBound()-bound.getLowerBound()));
            }
            individual.setSolution(solution);
            pop.add(individual);
        }
        return pop;
    }
}
