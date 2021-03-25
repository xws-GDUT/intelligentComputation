package application.TSP.algorithm;

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
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

        //1. 种群初始化
        List<Individual> pop = initPop(popSize, bound);
        //2. 评价种群
        evaluator.evaluate(pop);
        convergence.add(Collections.min(pop).clone());

        //************日志记录******************
        StringBuilder log = new StringBuilder();
        int numOfEvaluate = pop.size() ;
        System.out.println("1\t"+convergence.get(0).getFitness());
        log.append("1\t"+numOfEvaluate+"\t"+Collections.min(pop).getFitness()+"\n");
        //************日志记录******************
        //3. 演化循环
        for (int i = 0; i < iterations-1; i++) {

            //3.1 选择
            List<Individual> offspring = selector.select(pop);
            //3.2 交叉
            crossover.cross(offspring);
            //3.2 变异
            mutator.mutate(offspring,bound);
            //3.2 评价
            evaluator.evaluate(offspring);
            numOfEvaluate+=pop.size();
            pop=offspring;

            //************日志记录******************
            bestPerGeneration.add(Collections.min(pop).clone());
            convergence.add(Collections.min(bestPerGeneration).clone());
            System.out.println(i+2+"\t"+Collections.min(bestPerGeneration).getFitness());
            log.append(i+2+"\t"+numOfEvaluate+"\t"+convergence.get(i+1).getFitness()+"\n");
            //************日志记录******************
        }

        return convergence;
    }
    public List<Individual> initPop(int popsize, Bound bound){
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < popsize; i++) {
            Individual individual = new Individual();
            List<Double> solution = new ArrayList<>();
            for (int j = (int) bound.getLowerBound(); j<=bound.getUpperBound(); j++){
                solution.add((double) j);
            }
            Collections.shuffle(solution);
            individual.setSolution(solution);
            pop.add(individual);
        }
        return pop;
    }
}
