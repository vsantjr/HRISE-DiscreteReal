package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import lombok.Getter;
import lombok.Setter;
import org.uma.jmetal.util.front.Front;

public class AlgorithmEffortCalculator extends Calculator {

    protected long timeEffort;
    protected int popSize;

    public AlgorithmEffortCalculator(int numObj, long timeE, int popS) {
        super(numObj);
        this.timeEffort = timeE;
        this.popSize = popS;
        //this.evaluations = 0;
        this.lowerValuesAreBetter=true;
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<< TIME EFFORT: " + this.timeEffort);
    }

    @Override
    public double calculate(Front front, double[] doubles, double[] doubles1) {
    	double algEffortValue = 0.0;
		algEffortValue = (double)this.timeEffort / (double)this.popSize;
		return algEffortValue;
        //if (this.evaluations > 0 && timeEffort > 0) {
          //  return (((double) timeEffort) / ((double) this.evaluations));
        //}
        //return Double.MAX_VALUE;
    }
    
    /*public double execute(long timeExpend, int evaluations) {
        this.timeExpend=timeExpend;
        this.evaluations=evaluations;
        return calculate(null, null, null);
    }*/
}
