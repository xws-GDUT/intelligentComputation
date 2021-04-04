package utils;

import lombok.Data;
import program.continuous.PSO.dataStructure.Particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xws
 * @email wansenxu@163.com
 * 用于统计分析实验数据的类
 */
@Data
public class Statistics {

    private List<List<Double>> totalData;
    private double bestFun = Double.MAX_VALUE;   //重复运行n次中出现的最优情况
    private double worstFun = 0;;  //重复运行n次中出现的最差情况
    private double meanFun;   //重复执行n次的平均情况
    private double std = 0;       //重复执行n次的标准差
    private int meanBestInFirst; //平均首次出现最优的代数
//    private double count;       // 首次出现最优时的评价次数   to do

    public Statistics() {
        totalData = new ArrayList<>();
    }

    public void record(List<Double> data){
        totalData.add(data);
    }
    public void anaysis(){
        List<Double> tmp = new ArrayList<>(); //临时记录每次运行的结果
        int sumIndexOfBest = 0;
        for (int i = 0; i < totalData.size(); i++) {
            double min = Collections.min(totalData.get(i));
            sumIndexOfBest += totalData.get(i).indexOf(min);

            getBestAndWorst(min);
            tmp.add(min);
        }
        meanBestInFirst = sumIndexOfBest/totalData.size();

        //计算平均值
        mean(tmp);
        //计算标准差
        std(tmp);
    }
    private void getBestAndWorst(double fitness){
        if(fitness<bestFun){
            bestFun = fitness;
        }
        if(fitness >worstFun){
            worstFun = fitness;
        }
    }
    private void std(List<Double> fitnesses){
        mean(fitnesses);
        for (int i = 0; i < fitnesses.size(); i++) {
            std += Math.pow(fitnesses.get(i)-meanFun,2);
        }
        std = Math.sqrt(std);
    }
    private void mean(List<Double> fitnesses){
        meanFun = fitnesses.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
    }

}
