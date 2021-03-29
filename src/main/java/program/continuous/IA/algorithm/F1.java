package program.continuous.IA.algorithm;

import program.Evaluator;
import program.continuous.IA.dataStructure.Antibody;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class F1 implements Evaluator<Antibody> {
    @Override
    public double evaluate(Antibody antibody) {
        List<Double> genes = antibody.getGenes();
        double sum = 0;
        for (int j = 0; j < genes.size(); j++) {
            sum+= Math.pow(genes.get(j),2);
        }
        return sum;
    }
}
