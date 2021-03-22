package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;
import intelligentComputation.util.ECUtils;

import java.util.List;

/**
 *@Description 二项式交叉
 *@Author 许万森
 *@email wansenxu@163.com
 * 交叉策略：父子映射方案+二项式交叉（随机一维分量必定交叉）
 */

public class BinCrossover extends Crossover{

    @Override
    public List<Individual> cross(List<Individual> pop,List<Individual> mutatedPop) {

        List<Individual> clonedPop = ECUtils.clonePop(pop);
        for(int i=0;i<pop.size();i++){
            binomialCross2(clonedPop.get(i),mutatedPop.get(i));
        }
        return clonedPop;
    }
}
