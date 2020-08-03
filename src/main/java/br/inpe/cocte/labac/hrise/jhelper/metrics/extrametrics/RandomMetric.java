package br.inpe.cocte.labac.hrise.jhelper.metrics.extrametrics;

import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.pseudorandom.PseudoRandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import br.inpe.cocte.labac.hrise.jhelper.util.ExtraPseudoRandom;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.Calculator;

/**
 *
 * @author vinicius
 */
public class RandomMetric extends Calculator{

    protected PseudoRandomGenerator random;
    public RandomMetric(int numberOfObjectives) {
        super(numberOfObjectives);
        random=new JavaRandomGenerator();
        long seed=ExtraPseudoRandom.getInstance().getSeed();
        if(seed!=-1){
            random.setSeed(seed);
        }
    }

    @Override
    public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
        return random.nextDouble();
    }
    
}
