package expriment;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.evoluator.impl.F1;
import intelligentComputation.operator.crossover.impl.OnePointCrossover;
import intelligentComputation.operator.mutator.impl.RandMutator;
import intelligentComputation.operator.selector.impl.RouletteSelector;
import intelligentComputation.optimizer.Optimizer;
import intelligentComputation.optimizer.impl.GA;
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

    @Test
    public void SGA() throws IOException {

        Optimizer<Individual> GA = new GA()
                .setSelector(new RouletteSelector())
                .setCrossover(new OnePointCrossover().setRateOfCrossover(0.8))
                .setMutator(new RandMutator().setRateOfMutation(0.1));

        int sumMeanbestGen = 0;  //每次运行获得至今最优解的代数之和
        List<Double> bestFits = new ArrayList<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Individual> convergence = GA.optimize(50, 10, 2000, f1, bound);
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

        FileUtils.write(new File("SGA.txt"),bestFun+"\t"+worstFun+"\t"
                +meanFun+"\t"+stdFun+"\t"+sumMeanbestGen/numOfRun+"\t"+sumMeanbestGen*50+"\t"
                +totalTime/(double)numOfRun*(sumMeanbestGen/(double)numOfRun/2000.0)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
    }

}
