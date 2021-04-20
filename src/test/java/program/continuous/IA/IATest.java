package program.continuous.IA;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import question.continuous.IA.IA;
import question.continuous.IA.Antibody;
import evaluator.impl.F1;
import utils.Matrix;
import utils.Statistics;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class IATest {
    public static int numOfRun = 20;
    private final int dimension = 10;
    private final int iteration = 182;
    private final int numOfNum = 20;
    @Test
    void optimize() throws IOException {
        IA IA = new IA();
        Statistics statistics = new Statistics();
        List<Double> meanConvergence = Stream.generate(()->0.0).limit(iteration).collect(Collectors.toList());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfRun; i++) {
            List<Antibody> convergence = IA.optimize(100, dimension, -20.0, 20.0, 0.7, 1.0, 1.0, iteration, 10, 0.2, new F1());
            statistics.record(convergence.stream().map(Antibody::getFitness).collect(Collectors.toList()));
            meanConvergence = Matrix.plus(meanConvergence,convergence.stream().map(Antibody::getFitness).collect(Collectors.toList()));
        }
        long totalTime = System.currentTimeMillis()-begin;
        statistics.anaysis();
        meanConvergence = meanConvergence.stream().map((x) -> x /= numOfNum).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < meanConvergence.size(); i++) {
            info.append(i+1+"\t"+meanConvergence.get(i)+"\n");
        }
        FileUtils.write(new File("IA.txt"),statistics.getBestFun()+"\t"+statistics.getWorstFun()+"\t"
                +statistics.getMeanFun()+"\t"+statistics.getStd()+"\t"+statistics.getMeanBestInFirst()+"\t"+"未计算"+"\t"
                +totalTime/(double)numOfRun*((double)statistics.getMeanBestInFirst()/iteration)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
        FileUtils.write(new File("convergence/IA.txt"),info,"UTF-8",false);
    }
}