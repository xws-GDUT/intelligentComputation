package program.discrete.ACO.algorithm;

import org.junit.jupiter.api.Test;
import program.discrete.ACO.dataStructure.Ant;
import utils.Matrix;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class ACOTest {
    public int numOfRun = 20;
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
    void optimize() {
        ACO ACO = new ACO();


        List<Double> sum = Stream.generate(() -> 0.0).limit(iteration).collect(Collectors.toList());
        for (int i = 0; i < numOfRun; i++) {
            List<Ant> bestAntPerGeneration = ACO.optimize(popSize, dimension, lowerBound, upperBound, alpha, beta, rho, q, iteration, evaluator);
            List<Double> shortestDistance = bestAntPerGeneration.stream().map(Ant::getTotalDistance).collect(Collectors.toList());
            sum = Matrix.plus(sum, shortestDistance);
        }
        sum.stream().map((x)->x/numOfRun).forEach(System.out::println);
    }
    @Test
    public void test(){
//        List<Integer> tmp = new ArrayList<>(2000);
//        tmp.stream().map(n)
//        Stream.iterate(0,(x)->x=0).forEach(System.out::println);

    }

}