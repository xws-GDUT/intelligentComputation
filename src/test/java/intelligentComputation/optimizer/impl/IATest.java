package intelligentComputation.optimizer.impl;

import intelligentComputation.Bound;
import intelligentComputation.Individual;
import intelligentComputation.evoluator.impl.F1;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class IATest {

    @Test
    void optimize() {
        IA IA = new IA();
        List<Individual> convergence = IA.optimize(100, 10, 2000, new F1(), new Bound(-20.0, 20.0));

    }
}