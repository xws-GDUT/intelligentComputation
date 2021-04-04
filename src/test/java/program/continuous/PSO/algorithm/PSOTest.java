package program.continuous.PSO.algorithm;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import program.continuous.PSO.dataStructure.Particle;
import program.func.F1;
import utils.Matrix;
import utils.Statistics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @Author xws
 * @email wansenxu@163.com
 */
class PSOTest {

    public int popSize = 50;
    public int dimension = 10;
    public int iteration = 2000;
    public double c1 = 1.5;
    public double c2 = 1.5;
    public double w = 0.8;
    public double lowerBound = -20.0;
    public double upperBound = 20.0;
    public double VMAX = 10.0;
    public double VMIN = -10.0;
    public int numOfRun = 20 ;
    @Test
    void optimize() throws IOException {
        Statistics statistics = new Statistics();
        PSO PSO = new PSO().setW(w).setC1(c1).setC2(c2);

        List<Double> meanConvergence = Stream.generate(()->0.0).limit(iteration).collect(Collectors.toList());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Particle> convergence = PSO.optimize(popSize, dimension, iteration, lowerBound, upperBound, VMAX, VMIN, new F1());
            statistics.record(convergence.stream().map(Particle::getFitness).collect(Collectors.toList()));
            meanConvergence = Matrix.plus(meanConvergence,convergence.stream().map(Particle::getFitness).collect(Collectors.toList()));
        }
        long totalTime = System.currentTimeMillis()-begin;
        statistics.anaysis();
        meanConvergence = meanConvergence.stream().map((x) -> x /= numOfRun).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < meanConvergence.size(); i++) {
            info.append(i+1+"\t"+popSize*(i+1)+"\t"+meanConvergence.get(i)+"\n");
        }
        FileUtils.write(new File("PSO.txt"),statistics.getBestFun()+"\t"+statistics.getWorstFun()+"\t"
                +statistics.getMeanFun()+"\t"+statistics.getStd()+"\t"+statistics.getMeanBestInFirst()+"\t"+"未计算"+"\t"
                +totalTime/(double)numOfRun*((double)statistics.getMeanBestInFirst()/iteration)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
        FileUtils.write(new File("convergence/PSO.txt"),info,"UTF-8",false);
    }
}