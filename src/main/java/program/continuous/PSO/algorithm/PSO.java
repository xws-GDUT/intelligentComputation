package program.continuous.PSO.algorithm;

import lombok.Data;
import lombok.experimental.Accessors;
import program.Evaluator;
import program.continuous.PSO.dataStructure.Particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
@Accessors(chain = true)
public class PSO {
    private double W;
    private double c1;
    private double c2;
    double gBestFitness = Double.MAX_VALUE;
    List<Double> gBestPosition = null;

    public void optimize(int popSize, int dimension,int iteration,double xmax, double xmin, double vmax, double vmin, Evaluator evaluator){

        //初始化种群并初始化全局最优位置和最优值
        List<Particle> pop = initPop(popSize,dimension,xmax,xmin,vmax,vmin,evaluator);
        // 初始化全局最优位置和最优值
        Particle bestParticle = Collections.min(pop).clone();
        gBestFitness = bestParticle.getFitness();
        gBestPosition = bestParticle.getPosition();
        System.out.println(1+"\t"+gBestFitness);
        // 按照公式依次迭代直至满足精度或者迭代次数
        for (int i = 0; i < iteration; i++) {
            // 更新各个粒子的位置与速度值
            moveParticle(pop);
            // 评价种群并更新全局最优位置和最优值
            evaluate(pop,evaluator);
//            bestParticle = Collections.min(pop);
            if(Collections.min(pop).getFitness()<bestParticle.getFitness()){
                bestParticle = Collections.min(pop).clone();
                gBestFitness = bestParticle.getFitness();
                gBestPosition = bestParticle.getPosition();
            }
            System.out.println(i+2+"\t"+gBestFitness);
        }
        // 计算动态惯性权重值
    }

    private void evaluate(List<Particle> pop, Evaluator evaluator) {
        for (Particle particle : pop) {
            particle.evaluate(evaluator);
        }
    }

    private void moveParticle(List<Particle> pop) {
        for (Particle particle : pop) {
            List<Double> pBestPosition = particle.getBestPosition();
            List<Double> position = particle.getPosition();
            List<Double> v = particle.getV();
            for (int i = 0; i < v.size(); i++) {
                double p1 = new Random().nextDouble();
                double p2 = new Random().nextDouble();
                double tmpV = W * v.get(i) + c1 * p1 * (pBestPosition.get(i) - position.get(i)) + c2 * p2 * (gBestPosition.get(i) - position.get(i));
                double tmpX = position.get(i) + v.get(i);

                // 粒子速度、位置越界处理
                if (tmpV > particle.getVMAX() || tmpV < particle.getVMIN()) {
                    tmpV = new Random().nextDouble() * (particle.getVMAX() - particle.getVMIN()) + particle.getVMIN();
                }
                if (tmpX > particle.getUpperBound() || tmpX < particle.getLowerBound()) {
                    tmpX = new Random().nextDouble() * (particle.getUpperBound() - particle.getLowerBound()) + particle.getLowerBound();
                }
                v.set(i, tmpV);
                position.set(i, tmpX);
            }
        }
    }
    private List<Particle> initPop(int popSize, int dimension, double xmax, double xmin, double vmax, double vmin,Evaluator evaluator) {
        List<Particle> pop = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            pop.add(new Particle(dimension,xmax,xmin,vmax,vmin,evaluator));
        }
        return pop;
    }
}

