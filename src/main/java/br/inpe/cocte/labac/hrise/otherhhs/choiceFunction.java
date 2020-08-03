package br.inpe.cocte.labac.hrise.otherhhs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class choiceFunction {
	
	public choiceFunction(){	
	}
	public int getMaxChoiceFunction(double[][] measureMatrix1, int order1,
			double[][] measureMatrix2, int order2, double[][] measureMatrix3,
			int order3, double[][] measureMatrix4, int order4,
			int numberOfMeasurements, int alpha, long[] elapsedTimeArray){
		 //choiceFunctionf1 cf1 = new choiceFunctionf1();
		
		 /*get f1 of choice function regarding all algorithms*/
		 choiceFunctionf1 chFunf1 = new choiceFunctionf1();
		 int[][] CFF1 = chFunf1.choiceFunctionF1(measureMatrix1, 0, measureMatrix2, 1, measureMatrix3, 1, measureMatrix4, 1, numberOfMeasurements);
		 
		
		 long[] CF = new long[measureMatrix1.length];
		 long[][] maxCFvalue = new long[1][2];
		 
		 maxCFvalue[0][0] = 0;
		 maxCFvalue[0][1] = 0;
		 /*get the algorithm index that has the maximum choice function value i.e. the choose algorithm index*/
		 for(int i = 0; i < measureMatrix1.length; i++){
			  CF[i] = CFF1[i][1]*alpha + (elapsedTimeArray[i]/1000); // in seconds
			  if(CF[i] > maxCFvalue[0][1]){
				  maxCFvalue[0][0] = i;
				  maxCFvalue[0][1] = CF[i];
			  }
		 }
		int chosenAlgorithm = (int) maxCFvalue[0][0];
		
		
        System.out.println("---------------- CF Final -------------------");
		 
		 for (int k = 0; k < measureMatrix1.length; k++) {
			 System.out.println(CF[k]);
		 }
		
		 List<Integer> sameScore = new ArrayList<Integer>();
		 for (int isc = 0; isc < measureMatrix1.length; isc++ ) {
			 if (maxCFvalue[0][1] == CF[isc]) {
				 sameScore.add(isc);
			 }
		 }
		 
		 if (sameScore.size() > 1) {
			 chosenAlgorithm = sameScore.get(getRandomLLH(sameScore.size()));
			 System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Random for CF");
			 
		 }
		//System.out.println("Index: " +  maxCFvalue[0][0] );
        //System.out.println("Same Score: " + sameScore);
        sameScore.clear();
		 
		 
		return chosenAlgorithm;
	}


	 private int getRandomLLH(int high){
	   	  Random r = new Random();
	   	  int low = 0;
	   	//int high = 152;
	   	  int result = r.nextInt(high-low) + low;
	   	  return result;
	   }
	
}
