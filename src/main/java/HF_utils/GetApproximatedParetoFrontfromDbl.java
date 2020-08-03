package HF_utils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

public class GetApproximatedParetoFrontfromDbl {
	jmetal.qualityIndicator.util.MetricsUtilPlus utils_;
	public GetApproximatedParetoFrontfromDbl(){
		utils_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();
	}

	public int compareTwoPoints(double[] point1, double[] point2){
		
		if(point1.length != point2.length){
			System.err.println("Uncomparable points");
			System.exit(0);
		}
			int dominate1 ; // dominate1 indicates if some objective of point1 
            // dominates the same objective in point2. 
			int dominate2 ; // dominate2 is the complementary of dominate1.
			dominate1 = 0 ; 
		    dominate2 = 0 ;
		    int flag; //stores the result of the comparison
		    double value1, value2;
		    for (int i = 0; i < point1.length; i++) {
		      value1 = point1[i];
		      value2 = point2[i];
		      if (value1 < value2) {
		        flag = -1;
		      } else if (value1 > value2) {
		        flag = 1;
		      } else {
		        flag = 0;
		      }
		      
		      if (flag == -1) {
		        dominate1 = 1;
		      }
		      
		      if (flag == 1) {
		        dominate2 = 1;           
		      }
		    }
		            
		    if (dominate1 == dominate2) {            
		      return 0; //No one dominate the other
		    }
		    if (dominate1 == 1) {
		      return -1; // solution1 dominate
		    }
		    return 1;    // solution2 dominate 	
	}//dominance comparator
	
	public double[][] getApproxFront(double[][] solutionFront){
		//remove duplicated points
		double[][] uniqueFront = utils_.removeDuplicatePointsonFront(solutionFront);
		int uniqueFrontSize = uniqueFront.length;
		int numberOfObj = solutionFront[0].length;
		
		double[][] ParetoFront = new double[uniqueFrontSize][numberOfObj];
		
		  // iDominate[k] contains the list of solutions dominated by k
	    List<Integer> [] iDominate = new List[uniqueFrontSize];
	    
	    // dominateMe[i] contains the number of solutions dominating i        
	    int [] dominateMe = new int[uniqueFrontSize];
		
	    // front[i] contains the list of individuals belonging to the front i
	    List<Integer>  front = new ArrayList<Integer>();
	    
	    
	    // flagDominate is an auxiliar variable
	    int flagDominate = 0;  
		
	    //-> Fast non dominated sorting algorithm
	    for (int p = 0; p < uniqueFrontSize; p++) {
	    // Initialice the list of individuals that i dominate and the number
	    // of individuals that dominate me
	      iDominate[p] = new LinkedList<Integer>();
	      dominateMe[p] = 0;            
	      // For all q individuals , calculate if p dominates q or vice versa
	      for (int q = 0; q < uniqueFrontSize; q++) {

	          flagDominate =compareTwoPoints(uniqueFront[p],uniqueFront[q]);                                
	        
	        
	        if (flagDominate == -1) {
	          iDominate[p].add(new Integer(q));
	        } else if (flagDominate == 1) {
	                    dominateMe[p]++;   
	        }
	      }
	            
	      // If nobody dominates p, p belongs to the first front
	      if (dominateMe[p] == 0) {
	        front.add(new Integer(p));
	      }	      
	    }
	    

	    
	    for(int i = 0; i < front.size(); i++){
	    	int valueIndex = front.get(i).intValue();
	    	ParetoFront[i] = uniqueFront[valueIndex];
	    }
	 
	    return ParetoFront;
	}
	public static void main(String[] args) throws IOException {
		
		GetApproximatedParetoFrontfromDbl APF = new GetApproximatedParetoFrontfromDbl();
		
		// TODO Auto-generated method stub
		int maxFileHH = 30;
		int maxFileNSGAII = 30;
		int maxFileSPEA2 = 30;
		int maxFileIBEA = 30;
		int tempMax = maxFileNSGAII;
		String fileName;
		Map<Integer, double[][]> finalFront = new HashMap<>();
		
		for(int i = 0; i < maxFileHH; i++){
			//fileName = "DataTest/HyperVolumeCompute/SPEA2/SPEA2_2_50_50/run_"+ i +"/SPEA2_2_50_50_Run_"+i+"_final_Pareto_front.fun";
			fileName = "HF_Config_Power_Area_EMST/run_"+ i+"/HF_ObtainedParetoFront_Final_"+i+".txt";
			//fileName = "DataTest/HyperVolumeCompute/HH/run_"+ i+"/HF_ObtainedParetoFront_Final_"+i+".txt";
			double[][] solutionFront = APF.utils_.readFront(fileName);
			double[][] uniqueFront = APF.utils_.removeDuplicatePointsonFront(solutionFront);
			finalFront.put(new Integer(i), uniqueFront);
		}
		
//		for(int i = 0; i < maxFileHH; i++){
//			fileName = "DataTest/HyperVolumeCompute/HH/run_"+ i+"/HF_ObtainedParetoFront_Final_"+i+".txt";
//			double[][] solutionFront = APF.utils_.readFront(fileName);
//			double[][] uniqueFront = APF.utils_.removeDuplicatePointsonFront(solutionFront);
//			finalFront.put(new Integer(i), uniqueFront);
//		}
//		
//		for(int j = maxFileHH; j < (maxFileHH + maxFileNSGAII); j++){
//			fileName = "DataTest/HyperVolumeCompute/NSGAII/NSGAII_2_30_50/run_"+ (j -maxFileHH)+"/NSGAII_2_30_50_Run_"+(j -maxFileHH)+"_final_Pareto_front.fun";
//			double[][] solutionFront = APF.utils_.readFront(fileName);
//			double[][] uniqueFront = APF.utils_.removeDuplicatePointsonFront(solutionFront);
//			finalFront.put(new Integer(j), uniqueFront);
//		}
//		
//		int tempMin = maxFileHH+ maxFileNSGAII;
//		int tempMax = tempMin + maxFileSPEA2;
//		for(int k = tempMin; k < tempMax; k++){
//			fileName = "DataTest/HyperVolumeCompute/SPEA2/SPEA2_2_30_50/run_"+ (k -tempMin)+"/SPEA2_2_30_50_Run_"+(k -tempMin)+"_final_Pareto_front.fun";
//			double[][] solutionFront = APF.utils_.readFront(fileName);
//			double[][] uniqueFront = APF.utils_.removeDuplicatePointsonFront(solutionFront);
//			finalFront.put(new Integer(k), uniqueFront);
//		}
//		
//		tempMin = tempMin + maxFileSPEA2;
//		tempMax = tempMax + maxFileIBEA;
//		for(int l = tempMin; l < tempMax; l++){
//			fileName = "DataTest/HyperVolumeCompute/IBEA/IBEA_2_30_50/run_"+ (l -tempMin)+"/IBEA_2_30_50_Run_"+(l -tempMin)+"_final_Pareto_front.fun";
//			double[][] solutionFront = APF.utils_.readFront(fileName);
//			double[][] uniqueFront = APF.utils_.removeDuplicatePointsonFront(solutionFront);
//			finalFront.put(new Integer(l), uniqueFront);
//		}
//		
		int size = 0;
		
		for(int index = 0; index < finalFront.size(); index++){
			size +=  finalFront.get(new Integer(index)).length;
		}
		
		double[][] approximatedFront = new double[size][];
		int desPos = 0;
		for(int key = 0; key < tempMax; key++){
			double[][] tempArray = finalFront.get(new Integer(key));
			if(key ==0){
				desPos = 0;
			}else{
				desPos += finalFront.get(new Integer(key-1)).length;
			}
			System.arraycopy(tempArray, 0, approximatedFront, desPos, tempArray.length);
		}
//		String fileWriting = "DataTest/HyperVolumeCompute/NSGAII/ApproximatedFrontFull.txt";
//		APF.utils_.writeFront(fileWriting, approximatedFront);
		
		//double[][] uniqueApproximatedFront = APF.utils_.removeDuplicatePointsonFront(approximatedFront);
		double[][] nonDominatedFront = APF.getApproxFront(approximatedFront);
		double[][] nonDominatedFrontUnique = APF.utils_.removeDuplicatePointsonFront(nonDominatedFront);
		//String fileWriting = "DataTest/HyperVolumeCompute/SPEA2/SPEA2_2_50_50/SPEA2_2_50_50ApproximatedFrontUnique.txt";
		//String fileWriting = "DataTest/HyperVolumeCompute/HH/RandomChoiceBA50_ApproximatedFrontUnique.txt";
		String fileWriting = "HF_Config_Power_Area_EMST/ApproximatedFrontUnique.txt";
		APF.utils_.writeFront(fileWriting, nonDominatedFrontUnique);
		
	}

}
