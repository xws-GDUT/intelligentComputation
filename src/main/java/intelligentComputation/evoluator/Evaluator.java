package intelligentComputation.evoluator;

import java.util.List;

public interface Evaluator<T> {
    void evaluate(List<T> pop);
}
