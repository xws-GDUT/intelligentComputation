package program.continuous.DE;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import question.continuous.DE.DE;
import question.continuous.DE.Chromosome;
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
class DETest {
    public static int numOfRun = 20;
    private final int popSize = 50;
    private final int dimension = 10;
    private final int iteration = 2000;
    private final int numOfNum = 20;

    @Test
    void optimize() throws IOException {
        DE DE= new DE();
        Statistics statistics = new Statistics();
        List<Double> meanConvergence = Stream.generate(()->0.0).limit(iteration).collect(Collectors.toList());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < numOfNum; i++) {
            List<Chromosome> convergence = DE.opimize(50,0.1,0.5,2000,new F1(-20,20,dimension));
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

        FileUtils.write(new File("DE.txt"),statistics.getBestFun()+"\t"+statistics.getWorstFun()+"\t"
                +statistics.getMeanFun()+"\t"+statistics.getStd()+"\t"+statistics.getMeanBestInFirst()+"\t"+"未计算"+"\t"
                +totalTime/(double)numOfRun*((double)statistics.getMeanBestInFirst()/iteration)/1000.0+"\t"
                +totalTime/(double)numOfRun/1000.0,"UTF-8");
        FileUtils.write(new File("convergence/DE.txt"),info,"UTF-8",false);
    }
}