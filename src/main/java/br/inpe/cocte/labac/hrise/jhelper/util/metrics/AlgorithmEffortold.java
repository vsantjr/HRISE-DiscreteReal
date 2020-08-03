package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import lombok.Getter;
import lombok.Setter;
import org.uma.jmetal.util.front.Front;

public class AlgorithmEffortold extends Calculator {

    protected @Getter @Setter long timeExpend;
    protected @Getter @Setter int evaluations;

    public AlgorithmEffortold(int numObj) {
        super(numObj);
        this.timeExpend = 0;
        this.evaluations = 0;
        this.lowerValuesAreBetter=true;
    }

    @Override
    public double calculate(Front front, double[] doubles, double[] doubles1) {
        if (this.evaluations > 0 && timeExpend > 0) {
            return (((double) timeExpend) / ((double) this.evaluations));
        }
        return Double.MAX_VALUE;
    }
    
    public double execute(long timeExpend, int evaluations) {
        this.timeExpend=timeExpend;
        this.evaluations=evaluations;
        return calculate(null, null, null);
    }
}
