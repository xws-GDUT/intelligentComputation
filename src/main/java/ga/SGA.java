package ga;

import intelligentComputation.operator.selector.Selector;
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
public class SGA {

    private Selector selector;
    /**
     * 通过遗传算法得到最优个体
     * @param popSize 种群的大小
     * @param dimension 个体的维度
     * @param iterations 迭代次数
     * @param evaluator 目标函数
     * @return 种群每一代的收敛情况
     */
    public List<Chromosome> optimize(int popSize, int dimension, double lowBound, double upperBound, double rateOfCrossover, double rateOfMutation, int iterations, Evaluator evaluator) {
        List<Chromosome> bestPerGeneration = new ArrayList<>();  //记录每一代最优个体的集合
        List<Chromosome> convergence = new ArrayList<>();     //记录算法收敛初始的集合

        //1. 种群初始化
        List<Chromosome> pop = initPop(popSize,dimension,lowBound,upperBound,rateOfCrossover,rateOfMutation);
        //2. 评价种群
        evaluate(pop,evaluator);
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
            List<Chromosome> offspring = selector.select(pop);
            //3.2 交叉
            cross(offspring);
            //3.2 变异
            mutate(offspring);
            //3.2 评价
            evaluate(offspring,evaluator);
            numOfEvaluate+=pop.size();
            pop=offspring;

            //************日志记录******************
            bestPerGeneration.add(Collections.min(pop).clone());
            convergence.add(Collections.min(bestPerGeneration).clone());
            System.out.println(i+2+"\t"+Collections.min(bestPerGeneration).getFitness());
            log.append(i+2+"\t"+numOfEvaluate+"\t"+convergence.get(i+1).getFitness()+"\n");
            //************日志记录******************
        }
        //************日志记录******************
        try {
            FileUtils.write(new File("convergence/SGA.txt"),log,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //************日志记录******************
        return convergence;
    }

    private void evaluate(List<Chromosome> pop, Evaluator evaluator) {
        for (Chromosome chromosome : pop) {
            chromosome.evauate(evaluator);
        }
    }

    private List<Chromosome> initPop(int popSize, int dimension, double lowBound, double upperBound, double rateOfCrossover, double rateOfMutation) {
        List<Chromosome> pop = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            pop.add(new Chromosome(dimension,lowBound,upperBound,rateOfCrossover,rateOfMutation));
        }
        return pop;
    }

    private void mutate(List<Chromosome> offspring) {
        for (Chromosome individual : offspring) {
            individual.mutate();
        }
    }

    private void cross(List<Chromosome> offspring) {
        for (int i = 0; i < offspring.size(); i+=2) {
            offspring.get(i).cross(offspring.get(i+1));
        }
    }

}
