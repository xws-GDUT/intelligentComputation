package intelligentComputation.optimizer.impl;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.operator.crossover.Crossover;
import intelligentComputation.operator.mutator.Mutator;
import intelligentComputation.operator.selector.Selector;
import intelligentComputation.optimizer.Optimizer;
import intelligentComputation.util.ECUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
@Accessors(chain = true)
public class GA extends Optimizer<Individual> {

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
        List<Individual> bestPerGeneration = new ArrayList<>();  //记录每一代最优个体的集合
        List<Individual> convergence = new ArrayList<>();     //记录算法收敛初始的集合

        List<Individual> pop = ECUtils.initPop(popSize, bound, dimension);  //初始化
        evaluator.evaluate(pop);                                   //评价种群
        for (int i = 0; i < iterations; i++) {
            bestPerGeneration.add(Collections.min(pop).clone());
            Individual bestIndividual =  Collections.min(bestPerGeneration).clone();
            convergence.add(bestIndividual);
            System.out.println(i+1+"\t"+bestIndividual.getFitness());

            List<Individual> offspring = selector.select(pop);   //选择
            crossover.cross(offspring);                          //交叉
            mutator.mutate(offspring,bound);                     //变异
            evaluator.evaluate(offspring);                      //评价种群
            pop=offspring;
        }
        return convergence;
    }
//    public List<Individual> initPop(int popsize, Bound<Double> bound, int dimension) {
//        List<Individual> pop = new ArrayList<>();
//        for (int i = 0; i < popsize; i++) {
//            Individual individual = new Individual();
//            List<Double> solution = new ArrayList<>();
//            for (int j = 0; j < dimension; j++) {
//                solution.add(bound.getLowerBound()+new Random().nextDouble()*(bound.getUpperBound()-bound.getLowerBound()));
//            }
//            individual.setSolution(solution);
//            pop.add(individual);
//        }
//        return pop;
//    }
}
