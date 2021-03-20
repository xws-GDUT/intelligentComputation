package intelligentComputation.optimizer;

import intelligentComputation.Bound;
import intelligentComputation.evoluator.Evaluator;

import java.util.List;

public abstract class Optimizer<T> {
    public abstract List<T> optimize(int popSize, int dimension, int iterations, Evaluator evaluator, Bound bound);
}
