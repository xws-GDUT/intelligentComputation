package program.continuous.DE.algorithm;

import program.Evaluator;
import program.continuous.DE.dataStructure.Chromosome;
import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class F1 implements Evaluator<Chromosome> {
    @Override
    public double evaluate(Chromosome chromosome) {
        List<Double> genes = chromosome.getGenes();
        double sum = 0;
        for (int j = 0; j < genes.size(); j++) {
            sum+= Math.pow(genes.get(j),2);
        }
        return sum;
    }
}