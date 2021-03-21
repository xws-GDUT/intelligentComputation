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
public class SelfAdaptionDifferentEvolutionOptimizer extends Optimizer<Individual> {

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
        List<Individual> bestPerGeneration=new ArrayList<>();
        List<Individual> pop= ECUtils.initPop(popSize,bound,dimension);
        evaluator.evaluate(pop);
        double F0 = mutator.getFloatFactor();
        for (int k = 0; k < iterations; k++) {
            //将每一代的最优个体输出的convergence文件夹下
            Individual clonedBestIndividual = null;
            clonedBestIndividual =  Collections.min(pop).clone();
            bestPerGeneration.add(clonedBestIndividual);
            String info = k+1+"\t"+clonedBestIndividual.getFitness();
            System.out.println(info);

            //差分变异
            double lamba = Math.exp((1.0-iterations)/(iterations-k));
            mutator.updateFloatFactor(F0*Math.pow(2,lamba));
            List<Individual> mutatedPop = mutator.mutate(pop,bound);
            //交叉
            List<Individual> offspring = crossover.cross(pop, mutatedPop);
            evaluator.evaluate(offspring);
            //从pop和offspring种群中选择最优个体作为下一代种群的个体
            pop=selector.select(pop, offspring);
            //记录每一代的最优个体，观察该算法的收敛趋势
        }
        return bestPerGeneration;
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
