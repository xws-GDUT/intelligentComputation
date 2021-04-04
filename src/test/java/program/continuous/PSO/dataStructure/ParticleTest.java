package program.continuous.PSO.dataStructure;

import org.junit.jupiter.api.Test;
import program.func.F1;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class ParticleTest {

    @Test
    void testClone() {
        Particle particle = new Particle(10, -20.0, 20.0, 10.0, -10.0, new F1());
        Particle clone = particle.clone();
        for (int i = 0; i < clone.getPosition().size(); i++) {
            clone.getPosition().set(i,1.0);
        }
        for (int i = 0; i < clone.getBestPosition().size(); i++) {
            clone.getBestPosition().set(i,1.0);
        }
        System.out.println("test");
    }
}