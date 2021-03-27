package application.TSP.algorithm;

import application.TSP.operator.Mutator;
import application.TSP.pojo.Individual;
import application.TSP.evoluator.Evaluator;
import intelligentComputation.operator.crossover.Crossover;
import intelligentComputation.operator.selector.Selector;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Accessors(chain = true)
public class SGA extends Optimizer<Individual> {

    private Selector selector;
    private Crossover crossover;
    private Mutator mutator;

    /**
     * 通过遗传算法得到最优个体
     * @param popSize 种群的大小
     * @param dimension 个体的维度
     * @param iterations 迭代次数
     * @param evaluator 目标函数
     * @return 种群每一代的收敛情况
     */
    @Override
    public List<Individual> optimize(int popSize, int dimension, int iterations, Evaluator evaluator, double lowerBound, double upperBound,double rateOfCrossover,double rateOfMutation) {
        List<Individual> bestPerGeneration = new ArrayList<>();  //记录每一代最优个体的集合
        List<Individual> convergence = new ArrayList<>();     //记录算法收敛初始的集合

        //1. 种群初始化
        List<Individual> pop = initPop(popSize,dimension,lowerBound,upperBound,rateOfCrossover,rateOfMutation);
        //2. 评价种群
        evaluate(pop,evaluator);
        convergence.add(Collections.min(pop).clone());

        //************日志记录******************
        StringBuilder log = new StringBuilder();
        int numOfEvaluate = pop.size() ;
        System.out.println("1\t"+convergence.get(0).getFitness());
        System.out.println(convergence.get(0).getSolution());
        log.append("1\t"+numOfEvaluate+"\t"+Collections.min(pop).getFitness()+"\n");
        //************日志记录******************
        //3. 演化循环
        for (int i = 0; i < iterations-1; i++) {

            //3.1 选择
            List<Individual> offspring = selector.select(pop);
            //3.2 交叉
            cross(offspring);
//            crossover.cross(offspring);
            //3.2 变异
//            mutator.mutate(offspring,lowerBound,upperBound);
            mutate(offspring);
            //3.2 评价
            evaluate(offspring,evaluator);
            numOfEvaluate+=pop.size();
            pop=offspring;

            //************日志记录******************
            bestPerGeneration.add(Collections.min(pop).clone());
            convergence.add(Collections.min(bestPerGeneration).clone());
            System.out.println(i+2+"\t"+Collections.min(bestPerGeneration).getFitness());
            System.out.println(Collections.min(bestPerGeneration).getSolution());
            log.append(i+2+"\t"+numOfEvaluate+"\t"+convergence.get(i+1).getFitness()+"\n");
            //************日志记录******************
        }

        return convergence;
    }
    public List<Individual> initPop(int popsize,int dimension,double lowerBound,double upperBound,double rateOfCrossover,double rateOfMutation){
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < popsize; i++) {
            Individual individual = new Individual(dimension,lowerBound,upperBound,rateOfCrossover,rateOfMutation);
            pop.add(individual);
        }
        return pop;
    }

    /**
     * 评价种群
     * @param pop
     */
    public void evaluate(List<Individual> pop,Evaluator evaluator){
        for (Individual individual : pop) {
            individual.evaluate(evaluator);
        }
    }

    public void cross(List<Individual> pop){
        for (int i = 0; i < pop.size(); i+=2) {
            pop.get(i).cross(pop.get(i+1));
        }
    }
    public void mutate(List<Individual> pop){
        for (int i = 0; i < pop.size(); i++) {
            pop.get(i).mutate();
        }
    }

}
