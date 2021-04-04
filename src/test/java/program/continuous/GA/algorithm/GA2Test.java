package program.continuous.GA.algorithm;

import intelligentComputation.util.Matrix;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import program.continuous.GA.dataStructure.Chromosome;
import program.func.F1;
import utils.Statistics;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class GA2Test {
    public static int numOfRun = 20;
    private final int popSize = 50;
    private final int dimension = 10;
    private final int iteration = 2000;
    private final int numOfNum = 20;
    @Test
    void optimize() throws IOException {
        GA2 GA2 = new GA2().setSelector(new RouletteSelector());
        Statistics statistics = new Statistics();
        List<Double> meanConvergence = Stream.generate(()->0.0).limit(iteration).collect(Collectors.toList());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfNum; i++) {
            List<Chromosome> convergence = GA2.optimize(50, 10, -20.0, 20.0, 0.8, 0.1, iteration, new F1());
            statistics.record(convergence.stream().map(Chromosome::getFitness).collect(Collectors.toList()));
            meanConvergence = Matrix.plus(meanConvergence,convergence.stream().map(Chromosome::getFitness).collect(Collectors.toList()));
        }
        long totalTime = System.currentTimeMillis()-begin;
        statistics.anaysis();
        meanConvergence = meanConvergence.stream().map((x) -> x /= numOfNum).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < meanConvergence.size(); i++) {
            info.append(i+1+"\t"+popSize*(i+1)+"\t"+meanConvergence.get(i)+"\n");
        }

        FileUtils.write(new File("GA2.txt"),statistics.getBestFun()+"\t"+statistics.getWorstFun()+"\t"
                +statistics.getMeanFun()+"\t"+statistics.getStd()+"\t"+statistics.getMeanBestInFirst()+"\t"+"未计算"+"\t"
                +totalTime/(double)numOfRun*((double)statistics.getMeanBestInFirst()/iteration)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
        FileUtils.write(new File("convergence/GA2.txt"),info,"UTF-8",false);
    }
}