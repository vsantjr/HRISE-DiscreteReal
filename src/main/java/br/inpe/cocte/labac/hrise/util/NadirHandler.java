package br.inpe.cocte.labac.hrise.util;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.solution.Solution;

public class NadirHandler<S extends Solution<?>> {
	
	public NadirHandler() {
		
	}
	
	public List removeWorseThanNadir(List population, double[] nadir, int m) {
	       List<S> newpopulation = new ArrayList<>();
	       for (Object o : population) {
	           Solution s = (Solution) o;
	           //System.out.println("---- Solution REMOVE: " + s);
	           boolean stillOk = true;
	           for (int i = 0; i < m && stillOk; i++) {
	               if (nadir[i] < s.getObjective(i)) {
	                   //System.err.println(nadir[i]+" menor "+s.getObjective(i));
	                   stillOk = false;
	               }
	           }
	           if (stillOk) {
	               newpopulation.add((S) s);
	           }
	           //System.out.println("stillOk: " + stillOk);
	       }
	       
	       if (newpopulation.size() == 0) {
	    	   newpopulation.addAll(population);
	       }
	       
	       System.out.println("Size new Pop: " + newpopulation.size());
	       
	       return newpopulation;
	   }

}
