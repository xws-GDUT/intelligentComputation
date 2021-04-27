package question.continuous.PSO;

import lombok.Data;
import lombok.experimental.Accessors;
import evaluator.Evaluator;

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

    public List<Particle> optimize(int popSize,int iteration, Evaluator evaluator){
        int time = 0;
        List<Particle> convergence = new ArrayList<>();

        //初始化种群并初始化全局最优位置和最优值
        List<Particle> pop = initPop(popSize,evaluator.getDimension(),evaluator.getLowerBound()
                ,evaluator.getUpperBound(),evaluator.getUpperBound()/2.0,evaluator.getLowerBound()/2.0,evaluator);
        // 初始化全局最优位置和最优值
        Particle bestParticle = Collections.min(pop).clone();
        gBestFitness = bestParticle.getBestFitness();
        gBestPosition = bestParticle.getBestPosition();
        convergence.add(bestParticle);
        System.out.println(1+"\t"+gBestFitness);
        // 按照公式依次迭代直至满足精度或者迭代次数
//        while(evaluator.getNumOfEvaluate()<100000) {
        for (int i = 1; i < iteration; i++) {
            // 更新各个粒子的位置与速度值
            moveParticle(pop);
            // 评价种群并更新全局最优位置和最优值
            evaluate(pop,evaluator);
            for (Particle particle : pop) {
                if(particle.getBestFitness()<gBestFitness){
                    gBestPosition = particle.getBestPosition().stream().collect(Collectors.toList());
                    gBestFitness = particle.getBestFitness();
                    time = evaluator.getNumOfEvaluate();
                }
            }
            convergence.add(Collections.min(pop).clone());
            System.out.println(i+1+"\t"+gBestFitness);
        }
        System.out.println("首次出现最优的评价次数："+time+"\t"+"累计评价次数："+evaluator.getNumOfEvaluate());
        System.out.println("首次出现最优值："+gBestFitness);


        return convergence;
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
                // 更新粒子速度、位置并进行越界处理
                double tmpV = W * v.get(i) + c1 * p1 * (pBestPosition.get(i) - position.get(i)) + c2 * p2 * (gBestPosition.get(i) - position.get(i));
                if (tmpV > particle.getVMAX() || tmpV < particle.getVMIN()) {
                    tmpV = new Random().nextDouble() * (particle.getVMAX() - particle.getVMIN()) + particle.getVMIN();
                }
                v.set(i, tmpV);
                double tmpX = position.get(i) + v.get(i);
                if (tmpX > particle.getUpperBound() || tmpX < particle.getLowerBound()) {
                    tmpX = new Random().nextDouble() * (particle.getUpperBound() - particle.getLowerBound()) + particle.getLowerBound();
                }
                position.set(i, tmpX);
            }
        }
    }
    private List<Particle> initPop(int popSize, int dimension, double lowerBound, double upperBound, double vmax, double vmin,Evaluator evaluator) {
        List<Particle> pop = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            pop.add(new Particle(dimension,lowerBound,upperBound,vmax,vmin,evaluator));
        }
        return pop;
    }
}

