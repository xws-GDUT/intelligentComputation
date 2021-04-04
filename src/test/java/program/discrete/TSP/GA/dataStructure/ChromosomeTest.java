package program.discrete.TSP.GA.dataStructure;

import org.junit.jupiter.api.Test;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class ChromosomeTest {

    private final int dimension = 10;

    @Test
    void cross() {
        Chromosome chromosome = new Chromosome(dimension, 1, 10, 0.2, 1.0 / dimension);
        Chromosome chromosome2 = new Chromosome(dimension, 1, 10, 0.2, 1.0 / dimension);
        System.out.println(chromosome.getGenes());
        System.out.println(chromosome2.getGenes());
        chromosome.cross(chromosome2);
        System.out.println(chromosome.getGenes());
        System.out.println(chromosome2.getGenes());
    }

    @Test
    void mutate() {
        Chromosome chromosome = new Chromosome(dimension, 1, 10, 0.2, 1.0/dimension);
        System.out.println(chromosome);
        chromosome.mutate();
        System.out.println(chromosome);

    }
}