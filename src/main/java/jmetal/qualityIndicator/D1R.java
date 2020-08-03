package jmetal.qualityIndicator;

public class D1R {
	jmetal.qualityIndicator.util.MetricsUtilPlus utils_;
	
	  public D1R() {
		  utils_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();

	  }
	  public double D1RdistanceNoNormalization(double[][] front, double[][] referenceFront){
		  double sum = 0.0;
		    for (int i = 0 ; i < referenceFront.length; i++) {
		        sum += utils_.distanceToClosestPointforD1R(referenceFront[i],
		        		front);
		    }

		    // STEP 4. Divide the sum by the maximum number of points of the reference Pareto front
		    return sum / referenceFront.length;
	  }
	  
	  public double D1Rdistance(double[][] front, double[][] referenceFront){
		  
		     double[][] normalizedFront;
			 double[][] normalizedReferenceFront;

		   
		      double[] maximumValue;
		      double[] minimumValue;
		      int numberOfObj = front[0].length;
		      // STEP 1. Obtain the maximum and minimum values of the Pareto front
		      maximumValue = utils_.getMaximumValues(referenceFront,numberOfObj);
		      minimumValue = utils_.getMinimumValues(referenceFront,numberOfObj);

		      // STEP 2. Get the normalized front and true Pareto fronts
		      normalizedFront = utils_.getNormalizedFront(front, maximumValue, minimumValue);
		      normalizedReferenceFront =
		    		  utils_.getNormalizedFront(referenceFront, maximumValue, minimumValue);
		    

		    // STEP 3. Sum the distances between each point of the reference Pareto front and the dominated
		    // region of the front
		    double sum = 0.0;
		    for (int i = 0 ; i < normalizedReferenceFront.length; i++) {
		        sum += utils_.distanceToClosestPointforD1R(normalizedReferenceFront[i],
		            normalizedFront);
		    }

		    // STEP 4. Divide the sum by the maximum number of points of the reference Pareto front
		    return sum / normalizedReferenceFront.length;
		  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
