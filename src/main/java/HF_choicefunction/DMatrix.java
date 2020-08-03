package HF_choicefunction;

import java.io.IOException;

import HF_utils.GetApproximatedParetoFrontfromDbl;
import java.util.List;
import jmetal.qualityIndicator.HyperVolumeMinimizationProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;


public class DMatrix<S extends Solution<?>> {
	
	jmetal.qualityIndicator.util.MetricsUtilPlus utilities_;
	
	public DMatrix(){
		utilities_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();
	}
	
	public double DMatrixValue12Dbl(double[][] front1, double[][] front2, double[] referencePoint) throws IOException{

		//get unique fronts without duplicated points
		
		int size1 = front1.length;
		int size2 = front2.length;
		int numberOfObj = front1[0].length;
		
		double[][] tempValueofMergeFront = utilities_.mergeFronts(front1, size1, front2, size2, numberOfObj);
		//get merged front values
		GetApproximatedParetoFrontfromDbl PF = new GetApproximatedParetoFrontfromDbl();
		double[][] paretoFrontDbl = PF.getApproxFront(tempValueofMergeFront);	
		
		//calculate hypervolume of mergedFront
		HyperVolumeMinimizationProblem HM = new HyperVolumeMinimizationProblem();
		
		double mergedFrontHD = HM.hypervolume(paretoFrontDbl, numberOfObj, referencePoint);
		//double uniqueFront1HD = HM.hypervolume(front1, numberOfObj, referencePoint);
		double uniqueFront2HD = HM.hypervolume(front2, numberOfObj, referencePoint);
	
		//D(front1, fornt2), = HD(merge(front1, front2) - HD(front2))
		double D12 = mergedFrontHD - uniqueFront2HD;
		if(D12 < 0){
			String filename = "DataTest/ErrorLog/mergedParetoFront.txt";
			utilities_.writeFront(filename, paretoFrontDbl);
			filename = "DataTest/ErrorLog/mergedFront.txt";
			utilities_.writeFront(filename, tempValueofMergeFront);
			filename = "DataTest/ErrorLog/excluedFront.txt";
			utilities_.writeFront(filename, front2);
			System.err.println("DMatrix error");
			System.exit(0);
		} 
		return D12;	
	}
	
	public double DMatrixValue12(List<S> front1, List<S> front2, double[] referencePoint){
		//get unique fronts without duplicated points
		double[][]  tempValueofFront1 = SolutionListUtils.writeObjectivesToMatrix(front1);
		double[][]  tempValueofFront2 = SolutionListUtils.writeObjectivesToMatrix(front2);
		
		int size1 = tempValueofFront1.length;
		int size2 = tempValueofFront2.length;
		int numberOfObj = front1.get(0).getNumberOfObjectives();
		
		double[][] tempValueofMergeFront = utilities_.mergeFronts(tempValueofFront1, size1, tempValueofFront2, size2, numberOfObj);
		//get merged front values
		GetApproximatedParetoFrontfromDbl PF = new GetApproximatedParetoFrontfromDbl();
		double[][] paretoFrontDbl = PF.getApproxFront(tempValueofMergeFront);	
		
		//calculate hypervolume of mergedFront
		HyperVolumeMinimizationProblem HM = new HyperVolumeMinimizationProblem();
		
		double mergedFrontHD = HM.hypervolume(paretoFrontDbl, numberOfObj, referencePoint);
		double uniqueFront1HD = HM.hypervolume(tempValueofFront1, numberOfObj, referencePoint);
		double uniqueFront2HD = HM.hypervolume(tempValueofFront2, numberOfObj, referencePoint);
	
		//D(front1, fornt2), = HD(merge(front1, front2) - HD(front2))
		double D12 = mergedFrontHD - uniqueFront2HD;
		if(D12 < 0) System.err.println("DMatrix error");
		return D12;
	}
	
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DMatrix dmatrix = new DMatrix();
		String fileName1 = "HF_Config_Power_Area_EMST/FSAMNSGAIIIBEAPF.txt";
		String fileName2 = "HF_Config_Power_Area_EMST/FSAMSPEA2IBEAPF.txt";
		double[][] solutions1 = dmatrix.utilities_.readFront(fileName1);
		double[][] solutions2 = dmatrix.utilities_.readFront(fileName2);
		
		double[][] uniqueSolutions1 = dmatrix.utilities_.removeDuplicatePointsonFront(solutions1);
		double[][] uniqueSolutions2 = dmatrix.utilities_.removeDuplicatePointsonFront(solutions2);
		
		double[] referencePoint = {9,9.5,1.7};//30turbines
		
		double d12 = dmatrix.DMatrixValue12Dbl(uniqueSolutions1, uniqueSolutions2, referencePoint);
		System.out.println("Objective space covered by solutions1 but not by solutions2: "+ d12);
		
		double d21 = dmatrix.DMatrixValue12Dbl(uniqueSolutions2, uniqueSolutions1, referencePoint);
		System.out.println("Objective space covered by solutions2 but not by solutions1: "+ d21);
	}

}
