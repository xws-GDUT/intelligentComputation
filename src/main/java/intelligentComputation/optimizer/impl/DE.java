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

        //1. 初始化种群
        List<Individual> pop= ECUtils.initPop(popSize,bound,dimension);
        //2. 评价初始化种群
        evaluator.evaluate(pop);
        bestPerGeneration.add(Collections.min(pop).clone());
        //3. 迭代生成新种群
        for (int k = 0; k < iterations-1; k++) {
            //3.1 变异
            List<Individual> mutatedPop = mutator.mutate(pop,bound);
            //3.2 交叉
            List<Individual> offspring = crossover.cross(pop, mutatedPop);
            //3.3 评价新种群
            evaluator.evaluate(offspring);
            //3.4 选择
            pop=selector.select(pop, offspring);

            //记录每一代的最优个体，输出算法的收敛趋势
            bestPerGeneration.add(Collections.min(pop).clone());
            System.out.println(k+1+"\t"+Collections.min(pop).getFitness());
        }
        return bestPerGeneration;
    }
}
