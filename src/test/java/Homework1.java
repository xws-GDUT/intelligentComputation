import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.evoluator.impl.F1;
import intelligentComputation.operator.crossover.impl.BinCrossover;
import intelligentComputation.operator.crossover.impl.OnePointCrossover;
import intelligentComputation.operator.crossover.impl.EmperorCrossover;
import intelligentComputation.operator.mutator.impl.RandMutator;
import intelligentComputation.operator.mutator.impl.RandDifferentMutator;
import intelligentComputation.operator.selector.impl.DifferentSelector;
import intelligentComputation.operator.selector.impl.RouletteSelector;
import intelligentComputation.operator.selector.impl.TopSelector;
import intelligentComputation.optimizer.impl.DE;
import intelligentComputation.optimizer.impl.GeneticAlgorithm2Optimizer;
import intelligentComputation.optimizer.impl.GeneticAlgorithmOptimizer;
import intelligentComputation.optimizer.impl.SelfAdaptionDifferentEvolutionOptimizer;
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
public class Homework1 {
    public static int numOfRun = 20;
    @Test
    public void DE31() throws IOException {
        Evaluator f1 = new F1();
        Bound<Double> bound = new Bound(-20.0,20.0);

        SelfAdaptionDifferentEvolutionOptimizer DE31 = new SelfAdaptionDifferentEvolutionOptimizer();
        DE31.setCrossover(new BinCrossover().setRateOfCrossover(0.1))
                .setMutator(new RandDifferentMutator().setFloatFactor(0.4).setDifferentVector(1))
                .setSelector(new DifferentSelector());

        //运行算法处理数据

        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        List<Double> bestFits = new ArrayList<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = DE31.optimize(50, 10, 2000, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            Double bestFitness = Collections.min(convergenceForFitness);
            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        long totalTime = System.currentTimeMillis()-begin;
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);


        FileUtils.write(new File("convergence/DE31.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*50+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
    }
    @Test
    public void DE32() throws IOException {
        Evaluator f1 = new F1();
        Bound<Double> bound = new Bound(-20.0,20.0);

        DE DE32 = new DE();
        DE32.setCrossover(new BinCrossover().setRateOfCrossover(0.1))
                .setMutator(new RandDifferentMutator().setFloatFactor(0.5).setDifferentVector(1))
                .setSelector(new DifferentSelector());


        //运行算法处理数据
        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        List<Double> bestFits = new ArrayList<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = DE32.optimize(50, 10, 2000, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            Double bestFitness = Collections.min(convergenceForFitness);
            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        long totalTime = System.currentTimeMillis()-begin;
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);
        FileUtils.write(new File("convergence/DE32.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*50+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
    }
    @Test
    public void GA21() throws IOException {
        Evaluator f1 = new F1();
        Bound<Double> bound = new Bound(-20.0,20.0);

        GeneticAlgorithmOptimizer GA21 = new GeneticAlgorithmOptimizer();
        GA21.setCrossover(new OnePointCrossover().setRateOfCrossover(0.8))
                .setMutator(new RandMutator().setRateOfMutation(0.1))
                .setSelector(new RouletteSelector());

        //运行算法处理数据

        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和

        List<Double> bestFits = new ArrayList<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = GA21.optimize(50, 10, 2000, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            Double bestFitness = Collections.min(convergenceForFitness);
            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        long totalTime = System.currentTimeMillis()-begin;

        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);
        FileUtils.write(new File("convergence/GA21.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*50+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");

    }
    @Test
    public void GA22() throws IOException {
        Evaluator f1 = new F1();
        Bound<Double> bound = new Bound(-20.0,20.0);

        GeneticAlgorithm2Optimizer GA22 = new GeneticAlgorithm2Optimizer();
        GA22.setCrossover(new EmperorCrossover().setRateOfCrossover(0.8))
                .setMutator(new RandMutator().setRateOfMutation(0.1))
                .setSelector(new TopSelector());

        //运行算法处理数据
        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        long begin = System.currentTimeMillis();
        List<Double> bestFits = new ArrayList<>();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = GA22.optimize(50, 10, 2000, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
            List<Double> convergenceForFitness = convergence.stream().map(Individual::getFitness).collect(Collectors.toList());
            Double bestFitness = Collections.min(convergenceForFitness);
            sumMeanbestGen += convergenceForFitness.indexOf(bestFitness);  //累加每次运行首次获得最优解的代数
        }
        long totalTime = System.currentTimeMillis()-begin;
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=(Math.pow(bestFits.get(i)-meanFun,2)/numOfRun);
        }
        double stdFun = Math.sqrt(s);

        FileUtils.write(new File("convergence/GA22.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*50+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
    }

}
