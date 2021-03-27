package de;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
public class DE{

    private Selector selector;  //选择算子

    /**
     * 通过差分进化算法得到最优个体
     * @param popSize 种群的大小
     * @param dimension 个体的维度
     * @param iterations 迭代次数
     * @param evaluator 目标函数
     * @return 种群每一代的收敛情况
     */
    public List<Chromosome> optimize(int popSize, int dimension,double lowerBound,double upperBound,double rateOfCrossover,double rateOfMutation,int iterations, Evaluator evaluator){
        List<Chromosome> bestPerGeneration = new ArrayList<>();  //记录每一代最优个体的集合
        //1. 初始化种群
        List<Chromosome> pop= initPop(popSize,dimension,lowerBound,upperBound,rateOfCrossover,rateOfMutation);
        //2. 评价初始化种群
        evaluate(pop,evaluator);
        bestPerGeneration.add(Collections.min(pop).clone());
        //************日志记录******************
        StringBuilder log = new StringBuilder();
        int numOfEvaluate = pop.size() ;
        System.out.println("1\t"+bestPerGeneration.get(0).getFitness());
        log.append("1\t"+numOfEvaluate+"\t"+Collections.min(pop).getFitness()+"\n");
        //************日志记录******************
        //3. 迭代生成新种群
        for (int k = 0; k < iterations-1; k++) {
            //3.1 变异
            List<Chromosome> mutatedPop = mutate(pop);
            //3.2 交叉
            List<Chromosome> offspring = cross(pop, mutatedPop);
            //3.3 评价新种群
            evaluate(offspring,evaluator);
            numOfEvaluate+=offspring.size();
            //3.4 选择
            pop=selector.select(pop, offspring);

            //************日志记录******************
            bestPerGeneration.add(Collections.min(pop).clone());
            System.out.println(k+2+"\t"+Collections.min(pop).getFitness());
            log.append(k+2+"\t"+numOfEvaluate+"\t"+bestPerGeneration.get(k+1).getFitness()+"\n");
            //************日志记录******************
        }
        //************日志记录******************
        try {
            FileUtils.write(new File("convergence/DE.txt"),log,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //************日志记录******************
        return bestPerGeneration;
    }

    /**
     * bin 交叉
     * @param pop
     * @param mutatedPop
     * @return
     */
    private List<Chromosome> cross(List<Chromosome> pop, List<Chromosome> mutatedPop) {
        List<Chromosome> offspring = new ArrayList<>();
        for (int i = 0; i < pop.size(); i++) {
            offspring.add(pop.get(i).cross(mutatedPop.get(i)));
        }
        return offspring;
    }

    /**
     * rand/1 变异
     * @param pop
     * @return
     */
    private List<Chromosome> mutate(List<Chromosome> pop) {
        List<Chromosome> mutatedPop = new ArrayList<>();
        for (int i = 0; i < pop.size(); i++) {
            List<Integer> indexes = getRandomExcludeNum(pop.size(),3,i);
            Chromosome c1 = pop.get(indexes.get(0));
            Chromosome c2 = pop.get(indexes.get(1));
            Chromosome c3 = pop.get(indexes.get(2));
            mutatedPop.add(pop.get(i).mutate(c1,c2,c3));
        }
        return mutatedPop;
    }

    private void evaluate(List<Chromosome> pop, Evaluator evaluator) {
        for (Chromosome chromosome : pop) {
            chromosome.evauate(evaluator);
        }
    }

    private List<Chromosome> initPop(int popSize, int dimension, double lowerBound, double upperBound, double rateOfCrossover, double rateOfMutation) {
        List<Chromosome> pop = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            pop.add(new Chromosome(dimension,lowerBound,upperBound,rateOfCrossover,rateOfMutation));
        }
        return pop;
    }
    private List<Integer> getRandomExcludeNum(int bound,int amount,int excluded){
        List<Integer> list = new ArrayList();
        list.add(excluded);
        while(list.size()<amount+1){
            int i = new Random().nextInt(bound);
            if(!list.contains(Integer.valueOf(i))){
                list.add(i);
            }
        }
        list.remove(Integer.valueOf(excluded));
        return list;
    }
}
