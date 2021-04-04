package program.discrete.TSP.GA.algorithm;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import program.discrete.TSP.ACO.dataStructure.Ant;
import program.discrete.TSP.GA.dataStructure.Chromosome;
import program.func.DistanceFunc;
import utils.Matrix;
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
class GATest {


    private final int dimension = 31;
    private final int rateOfMutation = 1 / dimension;
    private final int popSize = 50;
    private final int lowerBound = 1;
    private final int upperBound = 31;
    private final double rateOfCrossover = 0.1;
    private final int iteration = 2000;
    private final int numOfRun = 20;
    @Test
    void optimize() throws IOException {
        GA GA = new GA();
        Statistics statistics = new Statistics();
        List<Double> meanConvergence = Stream.generate(()->0.0).limit(iteration).collect(Collectors.toList());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Chromosome> convergence = GA.optimize(popSize, dimension, lowerBound, upperBound, rateOfCrossover, rateOfMutation, new DistanceFunc(), iteration);
            statistics.record(convergence.stream().map(Chromosome::getFitness).collect(Collectors.toList()));
            meanConvergence = Matrix.plus(meanConvergence,convergence.stream().map(Chromosome::getFitness).collect(Collectors.toList()));
        }
        long totalTime = System.currentTimeMillis()-begin;
        statistics.anaysis();
        meanConvergence = meanConvergence.stream().map((x) -> x / numOfRun).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < meanConvergence.size(); i++) {
            info.append(i+1+"\t"+popSize*(i+1)+"\t"+meanConvergence.get(i)+"\n");
        }
        FileUtils.write(new File("convergence/TSP/GA.txt"),info,"UTF-8",false);
        FileUtils.write(new File("TSP/GA.txt"),
                statistics.getBestFun()+"\t"
                        +statistics.getWorstFun()+"\t"
                        +statistics.getMeanFun()+"\t"
                        +statistics.getStd()+"\t"
                        +statistics.getMeanBestInFirst()+"\t"+(totalTime)/1000/numOfRun,
                "UTF-8",false);

    }
}