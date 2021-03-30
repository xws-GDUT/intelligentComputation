package program.discrete.ACO.algorithm;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import program.discrete.ACO.dataStructure.Ant;
import utils.Matrix;

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
    private final int iteration = 200;
    private final Func evaluator = new Func();
    private int popSize = 50;

    @Test
    void optimize() throws IOException {
        long begin = System.currentTimeMillis();
        FileUtils.write(new File("ACO.txt"),"","UTF-8",false);
        List<Double> recordBestPerRun = new ArrayList<>();
        double best;
        double worst;
        double mean;

        ACO ACO = new ACO();
        int totalNumOfBestGeneration = 0;
        List<Double> sum = Stream.generate(() -> 0.0).limit(iteration).collect(Collectors.toList());
        for (int i = 0; i < numOfRun; i++) {
            List<Ant> bestAntPerGeneration = ACO.optimize(popSize, dimension, lowerBound, upperBound, alpha, beta, rho, q, iteration, evaluator);
            List<Double> shortestDistance = bestAntPerGeneration.stream().map(Ant::getTotalDistance).collect(Collectors.toList());
            recordBestPerRun.add(shortestDistance.get(shortestDistance.size()-1));
            totalNumOfBestGeneration+=shortestDistance.indexOf(shortestDistance.get(shortestDistance.size()-1));
            sum = Matrix.plus(sum, shortestDistance);
        }
        long end = System.currentTimeMillis();
        best = Collections.min(recordBestPerRun);
        worst = Collections.max(recordBestPerRun);
        mean = recordBestPerRun.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        List<Double> meanPerGeneration = sum.stream().map((x) -> x / numOfRun).collect(Collectors.toList());
        Double meanBest = meanPerGeneration.get(meanPerGeneration.size() - 1);
        int numOfMeanBestGeneration = meanPerGeneration.indexOf(meanBest);
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < meanPerGeneration.size(); i++) {
            info.append(i+1+"\t"+meanPerGeneration.get(i)+"\n");
        }
        FileUtils.write(new File("ACO.txt"),info,"UTF-8",true);
        FileUtils.write(new File("total.txt"),best+"\t"+worst+"\t"+mean+"\t"
                +totalNumOfBestGeneration/numOfRun+"\t"+(end-begin)/1000/numOfRun,"UTF-8",false);
    }
    @Test
    public void test(){
//        List<Integer> tmp = new ArrayList<>(2000);
//        tmp.stream().map(n)
//        Stream.iterate(0,(x)->x=0).forEach(System.out::println);
//        List<Integer> collect = Stream.generate(() -> new Random().nextInt(100)).limit(100).collect(Collectors.toList());
//        System.out.println(collect);
//        List<Integer> collect1 = collect.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
//        System.out.println(collect1);
//        List<Integer> collect2 = collect1.stream().limit(50).collect(Collectors.toList());
//        System.out.println(collect2);

    }

}