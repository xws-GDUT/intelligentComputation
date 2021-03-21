package intelligentComputation.optimizer.impl;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.operator.crossover.Crossover;
import intelligentComputation.operator.mutator.Mutator;
import intelligentComputation.operator.selector.Selector;
import intelligentComputation.optimizer.Optimizer;
import intelligentComputation.util.Clone;
import intelligentComputation.util.ECUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
@Accessors(chain = true)
public class GA2 extends Optimizer<Individual> {

    private Selector selector;
    private Mutator mutator;
    private Crossover crossover;

    /**
     * GA2: 君主方案（二项式交叉）+ 域内随机变异 + 从新旧种群中选择最优个体作为新种群
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
        evaluator.evaluate(pop);                                            //评价种群
        for (int i = 0; i < iterations; i++) {
            bestPerGeneration.add( Collections.min(pop).clone());
            Individual clonedBestIndividual =  Collections.min(bestPerGeneration).clone();
            convergence.add(clonedBestIndividual);
            System.out.println(i+1+"\t"+clonedBestIndividual.getFitness());

            List<Individual> clonePop = Clone.clonePop(pop);
            crossover.cross(clonePop);                  //交叉
            mutator.mutate(clonePop,bound);             //变异
            evaluator.evaluate(clonePop);               //评价种群
            List<Individual> offspring = selector.select(pop,clonePop);   //从新旧种群中选择最优个体作为新种群
            pop=offspring;
        }
        return convergence;
    }
}
