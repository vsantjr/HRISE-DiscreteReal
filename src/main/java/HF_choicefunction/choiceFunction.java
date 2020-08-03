package HF_choicefunction;

public class choiceFunction {
	
	public choiceFunction(){	
	}
	public int getMaxChoiceFunction(double[][] measureMetrix1, int order1,
			double[][] measureMetrix2, int order2, double[][] measureMetrix3,
			int order3, double[][] measureMetrix4, int order4,
			int numberOfMeasurements, int alpha, long[] elapsedTimeArray){
		 choiceFunctionf1 cf1 = new choiceFunctionf1();
		
		 /*get f1 of choice function regarding all algorithms*/
		 choiceFunctionf1 choicefunctionf1 = new choiceFunctionf1();
		 int[][] CFF1 = choicefunctionf1.choiceFunctionF1(measureMetrix1, 0, measureMetrix2, 1, measureMetrix3, 1, measureMetrix4, 1, numberOfMeasurements);
		 
		
		 long[] CF = new long[measureMetrix1.length];
		 long[][] maxCFvalue = new long[1][2];
		 
		 maxCFvalue[0][0] = 0;
		 maxCFvalue[0][1] = 0;
		 /*get the algorithm index that has the maximum choice function value i.e. the choose algorithm index*/
		 for(int i =0; i < measureMetrix1.length; i++){
			  CF[i] = CFF1[i][1]*alpha + elapsedTimeArray[i];
			  if(CF[i] > maxCFvalue[0][1]){
				  maxCFvalue[0][0] = i;
				  maxCFvalue[0][1] = CF[i];
			  }
		 }
		int choosenAlgorithm = (int) maxCFvalue[0][0];
		return choosenAlgorithm;
	}


}
