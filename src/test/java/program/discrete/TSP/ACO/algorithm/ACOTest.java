package program.discrete.TSP.ACO.algorithm;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import question.discrete.TSP.ACO.algorithm.ACO;
import question.discrete.TSP.ACO.dataStructure.Ant;
import evaluator.impl.DistanceFunc;
import utils.Matrix;
import utils.Statistics;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class ACOTest {
    public int numOfRun = 1;
    private final int dimension = 31;
    private final int lowerBound = 1;
    private final int upperBound = 31;
    private final double alpha = 1.0;
    private final double beta = 5.0;
    private final double rho = 0.1;
    private final int q = 100;
    private final int iteration = 2000;
    private final DistanceFunc evaluator = new DistanceFunc();
    private int popSize = 50;

    @Test
    void optimize() throws IOException {
        ACO ACO = new ACO();
        Statistics statistics = new Statistics();
        List<Double> meanConvergence = Stream.generate(()->0.0).limit(iteration).collect(Collectors.toList());
        long begin = System.currentTimeMillis();
        List<Double> sum = Stream.generate(() -> 0.0).limit(iteration).collect(Collectors.toList());
        for (int i = 0; i < numOfRun; i++) {
            List<Ant> convergence = ACO.optimize(popSize, dimension, lowerBound, upperBound, alpha, beta, rho, q, iteration, evaluator);
            statistics.record(convergence.stream().map(Ant::getTotalDistance).collect(Collectors.toList()));
            meanConvergence = Matrix.plus(meanConvergence,convergence.stream().map(Ant::getTotalDistance).collect(Collectors.toList()));
        }
        long totalTime = System.currentTimeMillis()-begin;
        statistics.anaysis();
        meanConvergence = meanConvergence.stream().map((x) -> x / numOfRun).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < meanConvergence.size(); i++) {
            info.append(i+1+"\t"+popSize*(i+1)+"\t"+meanConvergence.get(i)+"\n");
        }

        FileUtils.write(new File("convergence/TSP/ACO.txt"),info,"UTF-8",false);
        FileUtils.write(new File("TSP/ACO.txt"),
                statistics.getBestFun()+"\t"
                    +statistics.getWorstFun()+"\t"
                    +statistics.getMeanFun()+"\t"
                    +statistics.getStd()+"\t"
                    +statistics.getMeanBestInFirst()+"\t"+(totalTime)/1000/numOfRun,
                "UTF-8",false);
    }

}