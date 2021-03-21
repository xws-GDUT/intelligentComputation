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

/**
 *@Description 差分进化算法优化器的具体实现
 *@Author 许万森
 *@email wansenxu@163.com
 */

@Data
@Accessors(chain = true)
public class DE extends Optimizer<Individual> {

    private Selector selector;  //选择算子
    private Crossover crossover;  //交叉算子
    private Mutator mutator; //变异算子

    /**
     * 通过差分进化算法得到最优个体
     * @param popSize 种群的大小
     * @param dimension 个体的维度
     * @param iterations 迭代次数
     * @param evaluator 目标函数
     * @param bound 个体的取值范围
     * @return 种群每一代的收敛情况
     */
    @Override
    public List<Individual> optimize(int popSize, int dimension, int iterations, Evaluator evaluator, Bound bound){
        List<Individual> bestPerGeneration = new ArrayList<>();  //记录每一代最优个体的集合

        List<Individual> pop= ECUtils.initPop(popSize,bound,dimension);
        evaluator.evaluate(pop);
        for (int k = 0; k < iterations; k++) {
            //记录每一代的最优个体，输出算法的收敛趋势
            Individual clonedBestIndividual =  Collections.min(pop).clone();
            bestPerGeneration.add(clonedBestIndividual);
            System.out.println(k+1+"\t"+clonedBestIndividual.getFitness());

            List<Individual> mutatedPop = mutator.mutate(pop,bound);         //变异
            List<Individual> offspring = crossover.cross(pop, mutatedPop);  //交叉
            evaluator.evaluate(offspring);                                  //评价子代种群
            pop=selector.select(pop, offspring);                           //从父代和子代种群中选择新一代种群
        }
        return bestPerGeneration;
    }
}
