package program.continuous.IA.algorithm;

import intelligentComputation.util.ECUtils;
import org.apache.commons.io.FileUtils;
import program.Evaluator;
import program.continuous.IA.dataStructure.Antibody;

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
public class IA{
//    private double rateOfMutation = 0.7;
//    private double threshollOfSimilarity = 0.2;
//    private double numOfClone = 10;
//    private double a=1.0;
//    private double b=1.0;

    public List<Antibody> optimize(int popSize, int dimension, double lowerBound, double upperBound, double rateOfMutation,double a,double b,int iterations,int numOfClone,double threshollOfSimilarity,Evaluator evaluator) {
        List<Antibody> bestAntibodyPerGeneration = new ArrayList<>();
        double deta = (lowerBound-upperBound)/2.0;

        //1. 初始化种群
        List<Antibody> pop = initPop(popSize,dimension,lowerBound,upperBound,rateOfMutation);
        //2. 评价种群
//        evaluator.evaluate(pop);
        evaluate(pop,evaluator);
        bestAntibodyPerGeneration.add(Collections.min(pop).clone());

        //************日志记录******************
        StringBuilder log = new StringBuilder();
        log.append("1\t"+evaluator.getNumOfEvaluate()+"\n");
        System.out.println("1\t"+bestAntibodyPerGeneration.get(0).getFitness());
        //************日志记录******************
        //3. 免疫循环
        for (int i = 0; i < iterations-1; i++) {
            //3.1 计算个体浓度
            for (int j = 0; j < pop.size(); j++) {
                double degreeOfSimilarity = 0.0 ;
                int numOfSimilarity = 0;
                //3.1.1 计算个体间的亲和度 = 个体间的相似度
                Antibody antibody1 = pop.get(j);
                for (int k = 0; k < pop.size(); k++) {
                    Antibody individual = pop.get(k);
                    double sum = 0.0;
                    for (int l = 0; l < antibody1.getGenes().size(); l++) {
                        sum += Math.pow(antibody1.getGenes().get(l)-individual.getGenes().get(l),2);
                    }
                    degreeOfSimilarity = Math.sqrt(sum);
                    if(degreeOfSimilarity<threshollOfSimilarity){
                        numOfSimilarity++;
                    }
                }
                //3.1.2 计算浓度
                antibody1.setConcentration((double)numOfSimilarity/pop.size());
            }
            //3.2 计算激励度
            for (int j = 0; j < pop.size(); j++) {
                Antibody individual = pop.get(j);
                individual.setIncentiveStrength(a*individual.getFitness()+b*individual.getConcentration());
            }
            //3.3. 免疫选择
            List<Antibody> immunePop = new ArrayList<>();
            pop.sort((o1,o2)->{
                if(o1.getIncentiveStrength()>o2.getIncentiveStrength()){
                    return 1;
                }else if(o1.getIncentiveStrength()<o2.getIncentiveStrength()){
                    return -1;
                }else {
                    return 0;
                }
            });
            List<Antibody> firstHalf = pop.subList(0,popSize/2);
            double neighborhoodRange=deta/(i+1.0);
            for (int j = 0; j < firstHalf.size(); j++) {
                //4.1 克隆
                List<Antibody> clonedPoP = new ArrayList<>();
                for (int k = 0; k < numOfClone; k++) {
                    clonedPoP.add(pop.get(j).clone());
                }
                //4.2 变异
                for (int k = 1; k < clonedPoP.size(); k++) {
                    List<Double> solution = clonedPoP.get(k).getGenes();
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
                evaluate(clonedPoP,evaluator);
                immunePop.add(Collections.min(clonedPoP));
            }
            //5. 刷新种群
            //5.1 随机生成种群的另一半种群
            List<Antibody> flashPop = initPop(popSize/2,dimension,lowerBound,upperBound,rateOfMutation);
            //5.2  计算随机生成种群的另一半种群的亲和度
            evaluate(flashPop,evaluator);
            //5.3  合并免疫种群和随机生成种群的另一半种群作为刷新的种群
            immunePop.addAll(flashPop);
            pop = immunePop;

            //************日志记录******************
            System.out.println(i+2+"\t"+Collections.min(pop).getFitness());
            bestAntibodyPerGeneration.add(Collections.min(pop).clone());
            log.append(i+2+"\t"+evaluator.getNumOfEvaluate()+"\n");
            //************日志记录******************
        }

        //************日志记录******************
        try {
            FileUtils.write(new File("convergence/IA-.txt"),log,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //************日志记录******************
        return bestAntibodyPerGeneration;
    }

    private void evaluate(List<Antibody> pop, Evaluator evaluator) {
        for (Antibody antibody : pop) {
            antibody.evaluate(evaluator);
        }
    }

    private List<Antibody> initPop(int popSize, int dimension, double lowerBound, double upperBound, double rateOfMutation) {
        List<Antibody> pop = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            pop.add(new Antibody(dimension,lowerBound,upperBound,rateOfMutation));
        }
        return pop;
    }
}
