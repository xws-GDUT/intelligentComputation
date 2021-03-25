package intelligentComputation.optimizer.impl;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.optimizer.Optimizer;
import intelligentComputation.util.ECUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class IA extends Optimizer<Individual> {
    private double rateOfMutation = 0.7;
    private double threshollOfSimilarity = 0.2;
    private double numOfClone = 10;
    private double a=1.0;
    private double b=1.0;

    @Override
    public List<Individual> optimize(int popSize, int dimension, int iterations, Evaluator evaluator, Bound bound) {
        List<Individual> bestIndividualPerGeneration = new ArrayList<>();
        double deta = (bound.getUpperBound()-bound.getLowerBound())/2.0;

        //1. 初始化种群
        List<Individual> pop = ECUtils.initPop(popSize, bound, dimension);
        //2. 评价种群
        evaluator.evaluate(pop);
        bestIndividualPerGeneration.add(Collections.min(pop).clone());

        //************日志记录******************
        StringBuilder log = new StringBuilder();
        int numOfEvaluate = pop.size(); ;
        System.out.println("1\t"+bestIndividualPerGeneration.get(0).getFitness());
        log.append("1\t"+numOfEvaluate+"\t"+Collections.min(pop).getFitness()+"\n");
        //************日志记录******************
        //3. 免疫循环
        for (int i = 0; i < iterations-1; i++) {
            //3.1 计算个体浓度
            for (int j = 0; j < pop.size(); j++) {
                double degreeOfSimilarity = 0.0 ;
                int numOfSimilarity = 0;
                //3.1.1 计算个体间的亲和度 = 个体间的相似度
                Individual individual1 = pop.get(j);
                for (int k = 0; k < pop.size(); k++) {
                    Individual individual = pop.get(k);
                    double sum = 0.0;
                    for (int l = 0; l < individual1.getSolution().size(); l++) {
                        sum += Math.pow((double)individual1.getSolution().get(l)-(double)individual.getSolution().get(l),2);
                    }
                    degreeOfSimilarity = Math.sqrt(sum);
                    if(degreeOfSimilarity<threshollOfSimilarity){
                        numOfSimilarity++;
                    }
                }
                //3.1.2 计算浓度
                individual1.setConcentration((double)numOfSimilarity/pop.size());
            }
            //3.2 计算激励度
            for (int j = 0; j < pop.size(); j++) {
                Individual individual = pop.get(j);
                individual.setIncentiveStrength(a*individual.getFitness()-b*individual.getConcentration());
            }
            //3.3. 免疫选择
            List<Individual> immunePop = new ArrayList<>();
            pop.sort((o1,o2)->{
                if(o1.getIncentiveStrength()>o2.getIncentiveStrength()){
                    return 1;
                }else if(o1.getIncentiveStrength()<o2.getIncentiveStrength()){
                    return -1;
                }else {
                    return 0;
                }
            });
            List<Individual> firstHalf = pop.subList(0,popSize/2);
            double neighborhoodRange=deta/(i+1.0);
            for (int j = 0; j < firstHalf.size(); j++) {
                //4.1 克隆
                List<Individual> clonedPoP = new ArrayList<>();
                for (int k = 0; k < numOfClone; k++) {
                    clonedPoP.add(pop.get(j).clone());
                }
                //4.2 变异
                double lowerBound =  bound.getLowerBound();
                double upperBound =  bound.getUpperBound();
                for (int k = 1; k < clonedPoP.size(); k++) {
                    List<Double> solution = clonedPoP.get(k).getSolution();
                    for (int l = 0; l < solution.size(); l++) {
                        double p = Math.random();
                        if (p < rateOfMutation) {
                            double value = solution.get(l)+(new Random().nextDouble()-0.5)*neighborhoodRange;
                            if(value<lowerBound || value>upperBound){
                                value = lowerBound+new Random().nextDouble()*(upperBound-lowerBound);
                            }
                            solution.set(l,value);
                        }
                    }
                }
                //4.3 克隆抑制
                evaluator.evaluate(clonedPoP);
                numOfEvaluate+=clonedPoP.size();
                immunePop.add(Collections.min(clonedPoP));
            }
            //5. 刷新种群
            //5.1 随机生成种群的另一半种群
            List<Individual> flashPop = ECUtils.initPop(popSize/2, bound, dimension);
            //5.2  计算随机生成种群的另一半种群的亲和度
            evaluator.evaluate(flashPop);
            numOfEvaluate+=flashPop.size();
            //5.3  合并免疫种群和随机生成种群的另一半种群作为刷新的种群
            immunePop.addAll(flashPop);
            pop = immunePop;

            //************日志记录******************
            System.out.println(i+2+"\t"+Collections.min(pop).getFitness());
            bestIndividualPerGeneration.add(Collections.min(pop).clone());
            log.append(i+2+"\t"+numOfEvaluate+"\t"+bestIndividualPerGeneration.get(i+1).getFitness()+"\n");
            //************日志记录******************
        }

        //************日志记录******************
        try {
            FileUtils.write(new File("convergence/IA.txt"),log,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //************日志记录******************
        return bestIndividualPerGeneration;
    }
}
