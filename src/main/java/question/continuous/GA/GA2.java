package question.continuous.GA;

import lombok.Data;
import evaluator.Evaluator;

import java.util.*;

@Data
public class GA2 {

    /**
     * GA+君主交叉策略
     * @param popSize 种群的大小
     * @param dimension 个体的维度
     * @param iterations 迭代次数
     * @param evaluator 目标函数
     * @return 种群每一代的收敛情况
     */
    public List<Chromosome> optimize(int popSize,  double rateOfCrossover, double rateOfMutation, int iterations, Evaluator evaluator) {
        TreeSet<Chromosome> bestPerGeneration = new TreeSet<>();  //记录每一代最优个体的集合
        List<Chromosome> convergence = new ArrayList<>();     //记录算法收敛初始的集合

        //1. 种群初始化
        List<Chromosome> pop = initPop(popSize,evaluator.getDimension(),evaluator.getLowerBound(),evaluator.getUpperBound(),rateOfCrossover,rateOfMutation);
        //2. 评价种群
        evaluate(pop,evaluator);
        bestPerGeneration.add(Collections.min(pop).clone());
        convergence.add(bestPerGeneration.first());
        System.out.println("1\t"+bestPerGeneration.first().getFitness());
        //3. 演化循环
        for (int i = 1; i < iterations; i++) {
            //3.2 交叉
            cross(pop);
            //3.2 变异
            mutate(pop);
            //3.2 评价
            evaluate(pop,evaluator);
            //3.1 选择
            List<Chromosome> offspring = select(pop);
            pop=offspring;

            bestPerGeneration.add(Collections.min(pop).clone());
            convergence.add(bestPerGeneration.first());
            System.out.println(i+1+"\t"+bestPerGeneration.first().getFitness());
        }
        System.out.println("累计评价次数："+evaluator.getNumOfEvaluate());
        return convergence;
    }

    private void evaluate(List<Chromosome> pop, Evaluator evaluator) {
        for (Chromosome chromosome : pop) {
            chromosome.evauate(evaluator);
        }
    }

    private List<Chromosome> initPop(int popSize, int dimension, double lowBound, double upperBound, double rateOfCrossover, double rateOfMutation) {
        List<Chromosome> pop = new ArrayList<>(popSize);
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

    private void cross(List<Chromosome> pop) {
        Chromosome best = Collections.min(pop).clone();
        for (int i = 0; i < pop.size(); i++) {
            pop.get(i).cross(best.clone());
        }
    }
    /**
     * 轮盘赌选择
     * @param pop
     * @return
     */
    private  List<Chromosome> select(List<Chromosome> pop) {
        List<Chromosome> offspring = new ArrayList<>();;

        Double ReciprocalOfSumFit = 0.0;
        for (int i = 0; i < pop.size(); i++) {
            ReciprocalOfSumFit += 1.0 / pop.get(i).getFitness();
        }
        //计算每个个体被选中的概率
        List<Double> probability = new ArrayList<Double>();
        for (int i = 0; i < pop.size(); i++) {
            probability.add(1.0 / pop.get(i).getFitness() / ReciprocalOfSumFit);
        }

        for (int i = 0; i < pop.size(); i++) {
            int k = -1; //轮盘选择到的个体的下标
            double p = new Random().nextDouble();
            double sumProbability = 0;
            for (int j = 0; j < pop.size(); j++) {
                sumProbability+=probability.get(j);
                if(sumProbability>p){
                    k=j;
                    break;
                }
            }
            offspring.add(pop.get(k).clone());
        }
        return offspring;

    }

}

