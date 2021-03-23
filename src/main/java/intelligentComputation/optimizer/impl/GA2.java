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
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        //1. 初始化种群
        List<Individual> pop = ECUtils.initPop(popSize, bound, dimension);
        //2. 评价种群
        evaluator.evaluate(pop);

        convergence.add( Collections.min(pop).clone());
        //************日志记录******************
        StringBuilder log = new StringBuilder();
        int numOfEvaluate =  pop.size() ;
        System.out.println("1\t"+convergence.get(0).getFitness());
        log.append("1\t"+numOfEvaluate+"\t"+Collections.min(pop).getFitness()+"\n");
        //************日志记录******************
        //3. 种群演化
        for (int i = 0; i < iterations-1; i++) {
            List<Individual> tmpPoP = ECUtils.clonePop(pop);
            //3.1 交叉
            crossover.cross(tmpPoP);
            //3.2 变异
            mutator.mutate(tmpPoP,bound);
            //3.3 评价种群
            evaluator.evaluate(tmpPoP);
            numOfEvaluate += tmpPoP.size();
            //3.4 从新旧种群中选择最优个体作为新种群
            List<Individual> offspring = selector.select(pop,tmpPoP);
            pop=offspring;

            //************日志记录******************
            bestPerGeneration.add(Collections.min(pop).clone());
            convergence.add(Collections.min(bestPerGeneration).clone());
            System.out.println(i+1+"\t"+Collections.min(bestPerGeneration).getFitness());
            log.append(i+2+"\t"+numOfEvaluate+"\t"+convergence.get(i+1).getFitness()+"\n");
            //************日志记录******************
        }

        //************日志记录******************
        try {
            FileUtils.write(new File("convergence/GA2.txt"),log,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //************日志记录******************
        return convergence;
    }
}
