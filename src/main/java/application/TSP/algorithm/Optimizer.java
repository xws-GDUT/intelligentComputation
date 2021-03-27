package application.TSP.algorithm;

import application.TSP.evoluator.Evaluator;

import java.util.List;

public abstract class Optimizer<T> {
    public abstract List<T> optimize(int popSize, int dimension, int iterations, Evaluator evaluator, double lowerBound, double upperBound,double rateOfCrossover,double rateOfMutation);
}
