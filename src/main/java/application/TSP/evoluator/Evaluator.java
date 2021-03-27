package application.TSP.evoluator;

import application.TSP.pojo.Individual;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public interface Evaluator {
    double evaluate(Individual individual);
}
