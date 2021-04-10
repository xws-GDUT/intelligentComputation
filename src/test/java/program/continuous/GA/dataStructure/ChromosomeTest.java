package program.continuous.GA.dataStructure;

import org.junit.jupiter.api.Test;
import program.Evaluator;
import program.func.F1;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class ChromosomeTest {
    @Test
    void evauate() {
        Chromosome chromosome = new Chromosome(10, -20.0, 20.0, 0.8, 0.1);
        chromosome.evauate( new F1());
        System.out.println(chromosome);
    }

    @Test
    void cross() {
        Chromosome chromosome = new Chromosome(10, -20.0, 20.0, 0.8, 0.1);
        Chromosome chromosome1 = new Chromosome(10, -20.0, 20.0, 0.8, 0.1);
        System.out.println(chromosome);
        System.out.println(chromosome1);
        chromosome.cross(chromosome1);
        System.out.println(chromosome1);
        System.out.println(chromosome);
    }
    @Test
    void mutate() {
        Chromosome chromosome = new Chromosome(10, -20.0, 20.0, 0.8, 0.5);
        System.out.println(chromosome);
        chromosome.mutate();
        System.out.println(chromosome);
    }

    @Test
    void testEquals() {
        Chromosome chromosome = new Chromosome(10, -20.0, 20.0, 0.8, 0.1);
        Chromosome clone = chromosome.clone();
        System.out.println(clone.equals(chromosome));
    }
}