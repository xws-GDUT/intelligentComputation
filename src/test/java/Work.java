import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.evoluator.impl.F1;
import intelligentComputation.operator.crossover.impl.BinCrossover;
import intelligentComputation.operator.crossover.impl.DefaultCrossover;
import intelligentComputation.operator.crossover.impl.EmperorCrossover;
import intelligentComputation.operator.mutator.impl.DefaultMutator;
import intelligentComputation.operator.mutator.impl.RandDifferentMutator;
import intelligentComputation.operator.selector.impl.DifferentSelector;
import intelligentComputation.operator.selector.impl.RouletteSelector;
import intelligentComputation.optimizer.impl.DifferentEvolutionOptimizer;
import intelligentComputation.optimizer.impl.GeneticAlgorithmOptimizer;
import intelligentComputation.optimizer.impl.SelfAdaptionDifferentEvolutionOptimizer;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class Work {
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
        List<Double> bestFits = new ArrayList<>();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = DE31.optimize(50, 10, 2000, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
        }
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=Math.sqrt(Math.pow(bestFits.get(i)-meanFun,2));
        }
        FileUtils.write(new File("convergence/DE31.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+s/numOfRun,"UTF-8");
    }
    @Test
    public void DE32() throws IOException {
        Evaluator f1 = new F1();
        Bound<Double> bound = new Bound(-20.0,20.0);

        DifferentEvolutionOptimizer DE32 = new DifferentEvolutionOptimizer();
        DE32.setCrossover(new BinCrossover().setRateOfCrossover(0.1))
                .setMutator(new RandDifferentMutator().setFloatFactor(0.5).setDifferentVector(1))
                .setSelector(new DifferentSelector());


        //运行算法处理数据
        List<Double> bestFits = new ArrayList<>();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = DE32.optimize(50, 10, 2000, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
        }
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=Math.sqrt(Math.pow(bestFits.get(i)-meanFun,2));
        }
        FileUtils.write(new File("convergence/DE32.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+s/numOfRun,"UTF-8");
    }
    @Test
    public void GA21() throws IOException {
        Evaluator f1 = new F1();
        Bound<Double> bound = new Bound(-20.0,20.0);

        GeneticAlgorithmOptimizer GA21 = new GeneticAlgorithmOptimizer();
        GA21.setCrossover(new DefaultCrossover().setRateOfCrossover(0.8))
                .setMutator(new DefaultMutator().setRateOfMutation(0.1))
                .setSelector(new RouletteSelector());

        //运行算法处理数据
        List<Double> bestFits = new ArrayList<>();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = GA21.optimize(50, 10, 2000, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
        }
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=Math.sqrt(Math.pow(bestFits.get(i)-meanFun,2));
        }
        FileUtils.write(new File("convergence/GA21.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+s/numOfRun,"UTF-8");

    }
    @Test
    public void GA22() throws IOException {
        Evaluator f1 = new F1();
        Bound<Double> bound = new Bound(-20.0,20.0);

        GeneticAlgorithmOptimizer GA22 = new GeneticAlgorithmOptimizer();
        GA22.setCrossover(new EmperorCrossover().setRateOfCrossover(0.8))
                .setMutator(new DefaultMutator().setRateOfMutation(0.1))
                .setSelector(new RouletteSelector());

        //运行算法处理数据
        List<Double> bestFits = new ArrayList<>();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = GA22.optimize(50, 10, 2000, f1, bound);
            bestFits.add(Collections.min(convergence).getFitness());
        }
        double bestFun = Collections.min(bestFits);
        double worstFun = Collections.max(bestFits);
        double meanFun = bestFits.stream().reduce(0.0, Double::sum)/numOfRun;
        double s = 0.0;
        for (int i = 0; i < bestFits.size(); i++) {
            s+=Math.sqrt(Math.pow(bestFits.get(i)-meanFun,2));
        }
        FileUtils.write(new File("convergence/GA22.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+s/numOfRun,"UTF-8");
    }




}
