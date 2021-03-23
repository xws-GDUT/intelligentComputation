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

@Data
@Accessors(chain = true)
public class SGA extends Optimizer<Individual> {

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
//        StringBuilder log = new StringBuilder();

        //1. 种群初始化
        List<Individual> pop = ECUtils.initPop(popSize, bound, dimension);
        //2. 评价种群
        evaluator.evaluate(pop);
        convergence.add(Collections.min(pop).clone());
        System.out.println("1\t"+convergence.get(0).getFitness());
//        log.append("1\t"+"\t"+convergence.get(0)+"\n");
        for (int i = 0; i < iterations-1; i++) {
            List<Individual> offspring = selector.select(pop);   //选择
            crossover.cross(offspring);                          //交叉
            mutator.mutate(offspring,bound);                     //变异
            evaluator.evaluate(offspring);                      //评价种群
            pop=offspring;

            //记录实验数据
            bestPerGeneration.add(Collections.min(pop).clone());
            convergence.add(Collections.min(bestPerGeneration).clone());
            System.out.println(i+2+"\t"+Collections.min(bestPerGeneration).getFitness());
//            log.append(i+2+"\t"+Constant.NUM_OF_EVALUATE+"\t"+convergence.get(i+1)+"\n");
        }
        return convergence;
    }
}
