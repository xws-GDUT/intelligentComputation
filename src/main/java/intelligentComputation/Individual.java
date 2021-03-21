package intelligentComputation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Individual implements Cloneable,Comparable<Individual>{
    private List<Double> solution;
    private double fitness;


    @Override
    public Individual clone(){
        Individual individual = null;
        try {
            individual = (Individual)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<Double> clonedSolution = new ArrayList<>();
        for (int i = 0; i < solution.size(); i++) {
            clonedSolution.add(solution.get(i));
        }
        individual.solution = clonedSolution;
        individual.fitness = this.fitness;
        return individual;
    }

    @Override
    public int compareTo(Individual o) {
        if(this.getFitness()==o.getFitness()){
            return 0;
        }else if(this.getFitness()>o.getFitness()){
            return 1;
        }else{
            return -1;
        }
    }
}
