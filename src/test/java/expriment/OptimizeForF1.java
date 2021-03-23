package expriment;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.evoluator.impl.F1;
import intelligentComputation.operator.crossover.impl.BinCrossover;
import intelligentComputation.operator.crossover.impl.EmperorCrossover;
import intelligentComputation.operator.crossover.impl.OnePointCrossover;
import intelligentComputation.operator.mutator.impl.RandDifferentMutator;
import intelligentComputation.operator.mutator.impl.RandMutator;
import intelligentComputation.operator.selector.impl.DifferentSelector;
import intelligentComputation.operator.selector.impl.RouletteSelector;
import intelligentComputation.operator.selector.impl.TopSelector;
import intelligentComputation.optimizer.Optimizer;
import intelligentComputation.optimizer.impl.*;
import intelligentComputation.util.Matrix;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class OptimizeForF1 {
    public static Evaluator f1 = new F1();
    public static Bound bound = new Bound(-20.0,20.0);
    public static int numOfRun = 20;
    private final int popSize = 50;
    private final int dimension = 10;
    private final int iterations = 2000;

    @Test
    public void SGA() throws IOException {

        //1. 装配SGA优化器
        Optimizer<Individual> SGA = new SGA()
                .setSelector(new RouletteSelector())
                .setCrossover(new OnePointCrossover().setRateOfCrossover(0.8))
                .setMutator(new RandMutator().setRateOfMutation(0.1));

        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        int numOfEvaluate = 0 ;  //记录评价次数
        List<Double> bestFits = new ArrayList<>();
        long begin = System.currentTimeMillis();
        List<Double> sumConvergenceForFitness = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            sumConvergenceForFitness.add(0.0);
        }
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = SGA.optimize(popSize, dimension, iterations, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            sumConvergenceForFitness= Matrix.plus(sumConvergenceForFitness,convergenceForFitness);
            Double bestFitness = Collections.min(convergenceForFitness);

            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        List<Double> meanConvergenceForFitness = sumConvergenceForFitness.stream().map(x -> x /= numOfRun).collect(Collectors.toList());
//        StringBuilder convergen = new StringBuilder();
//        for (int i = 0; i < meanConvergenceForFitness.size(); i++) {
//            convergen.append(i+1+"\t"+popSize*(i+1)+"\t"+meanConvergenceForFitness.get(i)+"\n");
//        }
        long totalTime = System.currentTimeMillis()-begin;
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);

        FileUtils.write(new File("SGA.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*popSize+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
//        FileUtils.write(new File("convergence/SGA.txt"),convergen,"UTF-8");
    }
    @Test
    public void GA2() throws IOException {
        Optimizer<Individual> GA2 = new GA2()
                .setCrossover(new EmperorCrossover().setRateOfCrossover(0.8))
                .setMutator(new RandMutator().setRateOfMutation(0.1))
                .setSelector(new TopSelector());


        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        List<Double> bestFits = new ArrayList<>();
        List<Double> sumConvergenceForFitness = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            sumConvergenceForFitness.add(0.0);
        }
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = GA2.optimize(popSize, dimension, iterations, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            sumConvergenceForFitness= Matrix.plus(sumConvergenceForFitness,convergenceForFitness);
            Double bestFitness = Collections.min(convergenceForFitness);
            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        List<Double> meanConvergenceForFitness = sumConvergenceForFitness.stream().map(x -> x /= 20.0).collect(Collectors.toList());
//        StringBuilder convergen = new StringBuilder();
//        for (int i = 0; i < meanConvergenceForFitness.size(); i++) {
//            convergen.append(i+1+"\t"+popSize*(i+1)+"\t"+meanConvergenceForFitness.get(i)+"\n");
//        }
        long totalTime = System.currentTimeMillis()-begin;
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);

        FileUtils.write(new File("GA2.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*popSize+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
//        FileUtils.write(new File("convergence/GA2.txt"),convergen,"UTF-8");
    }
    @Test
    public void SDE() throws IOException {
        Optimizer<Individual> SDE = new DE()
                .setSelector(new DifferentSelector())
                .setCrossover(new BinCrossover().setRateOfCrossover(0.1))
                .setMutator(new RandDifferentMutator().setDifferentVector(1).setFloatFactor(0.5));

        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        List<Double> bestFits = new ArrayList<>();
        List<Double> sumConvergenceForFitness = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            sumConvergenceForFitness.add(0.0);
        }
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = SDE.optimize(popSize, dimension, iterations, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            sumConvergenceForFitness= Matrix.plus(sumConvergenceForFitness,convergenceForFitness);
            Double bestFitness = Collections.min(convergenceForFitness);
            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        List<Double> meanConvergenceForFitness = sumConvergenceForFitness.stream().map(x -> x /= 20.0).collect(Collectors.toList());
//        StringBuilder convergen = new StringBuilder();
//        for (int i = 0; i < meanConvergenceForFitness.size(); i++) {
//            convergen.append(i+1+"\t"+popSize*(i+1)+"\t"+meanConvergenceForFitness.get(i)+"\n");
//        }
        long totalTime = System.currentTimeMillis()-begin;
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);

        FileUtils.write(new File("SDE.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*popSize+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
//        FileUtils.write(new File("convergence/SDE.txt"),convergen,"UTF-8");
    }
    @Test
    public void DE2() throws IOException {
        Optimizer<Individual> DE2 = new DE2()
                .setSelector(new DifferentSelector())
                .setCrossover(new BinCrossover().setRateOfCrossover(0.1))
                .setMutator(new RandDifferentMutator().setDifferentVector(1).setFloatFactor(0.4));

        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        List<Double> bestFits = new ArrayList<>();
        List<Double> sumConvergenceForFitness = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            sumConvergenceForFitness.add(0.0);
        }
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = DE2.optimize(popSize, dimension, iterations, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            sumConvergenceForFitness= Matrix.plus(sumConvergenceForFitness,convergenceForFitness);
            Double bestFitness = Collections.min(convergenceForFitness);
            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        List<Double> meanConvergenceForFitness = sumConvergenceForFitness.stream().map(x -> x /= 20.0).collect(Collectors.toList());
//        StringBuilder convergen = new StringBuilder();
//        for (int i = 0; i < meanConvergenceForFitness.size(); i++) {
//            convergen.append(i+1+"\t"+popSize*(i+1)+"\t"+meanConvergenceForFitness.get(i)+"\n");
//        }
        long totalTime = System.currentTimeMillis()-begin;
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);

        FileUtils.write(new File("DE2.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*popSize+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
//        FileUtils.write(new File("convergence/DE2.txt"),convergen,"UTF-8");
    }

    @Test
    void IA() throws IOException {
        IA IA = new IA();
        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        List<Double> bestFits = new ArrayList<>();
        List<Double> sumConvergenceForFitness = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            sumConvergenceForFitness.add(0.0);
        }
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = IA.optimize(100, dimension, iterations, new F1(), new Bound(-20.0, 20.0));
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            sumConvergenceForFitness= Matrix.plus(sumConvergenceForFitness,convergenceForFitness);
            Double bestFitness = Collections.min(convergenceForFitness);
            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        List<Double> meanConvergenceForFitness = sumConvergenceForFitness.stream().map(x -> x /= 20.0).collect(Collectors.toList());
//        StringBuilder convergen = new StringBuilder();
//        convergen.append(1+"\t"+100+"\t"+meanConvergenceForFitness.get(0)+"\n");
//        for (int i = 1; i < meanConvergenceForFitness.size(); i++) {
//            convergen.append(i+1+"\t"+(100+(600*i))+"\t"+meanConvergenceForFitness.get(i)+"\n");
//        }
        long totalTime = System.currentTimeMillis()-begin;
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);

        FileUtils.write(new File("IA.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*50+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
//        FileUtils.write(new File("convergence/IA.txt"),convergen,"UTF-8");
    }


}
