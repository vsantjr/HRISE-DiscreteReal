package br.inpe.cocte.labac.hrise.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.management.JMException;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import br.inpe.cocte.labac.hrise.qualityreal.QualityIndicatorsRealProblems;
import br.inpe.cocte.labac.hrise.util.ProblemsWrapperLARILA;
import br.inpe.cocte.labac.hrise.util.SaveFiles;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This is the Runner for the HRISE family and all other algorithms described in [1], considering Discrete Optimisation and Real problems. 
 * This code supports two types of problems related to Graphical User Interface (GUI) Software Testing: code-driven testing and use 
 * case-driven testing. With respect to the code-driven testing, the generation of test cases for 24 problem instances (GUIs) of the 
 * TerraAmazon software product is addressed. Regarding the use case-driven testing, there are 8 problem instances where 4 are use cases 
 * for the LibreOffice Writer, 3 are for WhatsApp, and 1 is for YouTube. as YOUx). application, (identied as LOFx), 3 are for WhatsApp (identied as WUPx)1 is for YouTube (identied as YOUx).8 problem instances  
 *
 *[1] SANTIAGO JÚNIOR, VALDIVINO ALEXANDRE DE ; ÖZCAN, ENDER ; DE CARVALHO, VINICIUS RENAN. Hyper-Heuristics based on Reinforcement Learning,
 * Balanced Heuristic Selection and Group Decision Acceptance. APPLIED SOFT COMPUTING, p. 1-48, 2020. Submitted (Pre-print, Revision 1).
 *
 * @author Valdivino Alexandre de Santiago Júnior
 */

public class HRISERunnerDiscreteReal {

	// **************************************************
    // Main method.
    // **************************************************
	public static <S> void main(String[] args) throws FileNotFoundException, ConfigurationException, JMException, IOException, ClassNotFoundException {
		
		/** Handling directories.
	     */
		String baseSrcDir = "/Users/valdivino/Documents/Des/ProjEclipse/HRISE-DiscreteReal"; // The main Source Dir. Change it according to your platform.
		String baseDestDir = "/Users/valdivino/Documents/Des/Rst/HRISE/ResultsExec"; // The main Destination Dir. Change it according to your platform.
		FileUtils.deleteDirectory(new File("graph"));
	    new File("graph").mkdirs();
	    FileUtils.deleteDirectory(new File("result"));
        FileUtils.deleteDirectory(new File("exec"));
        FileUtils.deleteDirectory(new File("pareto_fronts_known"));
        
        
        FileUtils.deleteDirectory(new File(baseDestDir+"/ALLRES")); // Destination/Output dir: populations (solutions), objective functions, quality indicators
        FileUtils.deleteDirectory(new File(baseDestDir+"/EFG")); // Destination/Output dir: graph (EFG)
        FileUtils.deleteDirectory(new File(baseDestDir+"/EXEC")); // Destination/Output dir: utilisation rate (HRISE family, HH-RILA), accepted populations (HRISE family) 
        FileUtils.deleteDirectory(new File(baseDestDir+"/PFKNOWN")); // Destination/Output dir: all Known Pareto Fronts
        FileUtils.deleteDirectory(new File(baseDestDir+"/PFTRUEKNOWN")); // Destination/Output dir: all True Known Pareto Fronts
        
        /** 
	     * Store the quality indicators considering Real problems.
	     */
	    QualityIndicatorsRealProblems qualIndReal = QualityIndicatorsRealProblems.getInstance();
		
		/**
	     * The code-driven problem instances are identified as strings of the form: "gu1", "gu2", ..., "gu24". The use case-driven problem 
	     * instances are identified as: "lofx" (LibreOffice), "wupx" (WhatsApp), and "youx" (YouTube). Moreover, they should be addressed in 
	     * groups as shown below and they are case sensitive (strings). Just uncomment the groups below in case you need to run for a 
	     * different sequence of problem instances.  
	     */
		//String[] allProblems = {"gu1", "gu2", "gu3", "gu4", "gu5"};
		//String[] allProblems = {"gu6", "gu7", "gu8", "gu9", "gu10"};
		//String[] allProblems = {"gu11", "gu12", "gu13", "gu14", "gu15"};
		//String[] allProblems = {"gu16", "gu17", "gu18", "gu19", "gu20"};
		//String[] allProblems = {"gu21", "gu22", "gu23", "gu24"};
		//String[] allProblems = {"lof1", "lof2", "lof3", "lof4"};
		//String[] allProblems = {"wup1", "wup2", "wup3", "you1"};
		String[] allProblems = {"gu1"};
		/**
		 * Number of Trials.
		 */
		String numberTrials = "3";
						
		for (int indP = 0; indP < allProblems.length; indP++) {
						
			/**
		     * Arguments for IBEA, NSGA-II, SPEA2, HH-ALL, HH-CF:
		     * 1. Number of Trials;
		     * 2. Problem Instance;
		     * 3. Algorithm;
		     * 4. Number of Iterations;
		     * 5. Number of Decision Points;
		     * 6. Base Source Dir;
		     * 7. Base Destination Dir.
		     */
			
			String[] newArgsIBEA = {numberTrials, allProblems[indP], "IBEA", "1000", "1", baseSrcDir, baseDestDir};
			new LLHsSingleMainReal(newArgsIBEA);
			
			String[] newArgsNSGAII = {numberTrials, allProblems[indP], "NSGAII", "1000", "1", baseSrcDir, baseDestDir};
			new LLHsSingleMainReal(newArgsNSGAII);
			
			String[] newArgsSPEA2 = {numberTrials, allProblems[indP], "SPEA2", "1000", "1", baseSrcDir, baseDestDir};
			new LLHsSingleMainReal(newArgsSPEA2);
					
			String[] newArgsHHALL = {numberTrials, allProblems[indP], "HH-ALL", "1000", "25", baseSrcDir, baseDestDir};
			new HHALLMainReal(newArgsHHALL);
			
			String[] newArgsHHCF = {numberTrials, allProblems[indP], "HH-CF", "1000", "25", baseSrcDir, baseDestDir};
			new HHCFMainReal(newArgsHHCF);
			
			/**
		     * Arguments for HRMA, HRISE_R, HRISE_M:
		     * 1. Number of Trials;
		     * 2. Problem Instance;
		     * 3. Algorithm;
		     * 4. Number of Iterations;
		     * 5. Number of Decision Points;
		     * 6. alpha;
		     * 7. eta_a;
		     * 8. Base Source Dir;
		     * 9. Base Dest Dir.
		     */
			
			String[] newArgsHRMA = {numberTrials, allProblems[indP], "HRMA", "1000", "7", "0.0", "0", baseSrcDir, baseDestDir};
			new HRISEMainReal(newArgsHRMA);
			
			String[] newArgsHRISERES = {numberTrials, allProblems[indP], "HRISE_R", "1000", "7", "0.1", "3", baseSrcDir, baseDestDir};
			new HRISEMainReal(newArgsHRISERES);
			
			String[] newArgsHRISEMAJ = {numberTrials, allProblems[indP], "HRISE_M", "1000", "7", "1.0", "2", baseSrcDir, baseDestDir};
			new HRISEMainReal(newArgsHRISEMAJ);
			
											
		}
				
		/**
	     * Arguments for HH-RILA:
	     * 1. Number of Trials;
	     * 2. Iterations/LLH;
	     * 3. Seed;
	     * 4. Algorithm;
	     * 5. Problem Instances;
	     * 6. Number of Algorithms (LLHs);
	     * 7. Base Source Dir;
		 * 8. Base Dest Dir.
	     */
		FileUtils.deleteDirectory(new File(baseSrcDir+"/HF_Config_Benchmark/Results/HH-RILA"));
	    String[] newArgsHHRILA = new String[8];
		
		int maxAllClasses = -1;
		String cprob = " ";
		if (allProblems[0].contains("vc")) {
			maxAllClasses = 1;
			cprob = "vc";
		} else { // #1
			if (allProblems[0].contains("wr")) {
				maxAllClasses = 1;
				cprob = "wr";
		     } else { // #2
		    	 if (allProblems[0].contains("cs")) {
						maxAllClasses = 2;
						cprob = "cssp";
		          } else { // #3
		        	  if (allProblems[0].equals("gu1")) {
		        		  maxAllClasses = 1;
		        		  cprob = "gu1-5";
		        	  } else { // #4
			        	  if (allProblems[0].equals("gu6")) {
			        		  maxAllClasses = 1;
			        		  cprob = "gu6-10";
			        	  } else { // #5
				        	  if (allProblems[0].equals("gu11")) {
				        		  maxAllClasses = 1;
				        		  cprob = "gu11-15";
				        	  } else { // #6
					        	  if (allProblems[0].equals("gu16")) {
					        		  maxAllClasses = 1;
					        		  cprob = "gu16-20";
					        	  } else { // #7
						        	  if (allProblems[0].equals("gu21")) {
						        		  maxAllClasses = 1;
						        		  cprob = "gu21-24";
						        	  } else { // #8
							        	  if (allProblems[0].equals("lof1")) {
							        		  maxAllClasses = 1;
							        		  cprob = "lof1-4";
							        	  } else { // #9
							        		  if (allProblems[0].equals("wup1")) {
							        			  maxAllClasses = 2;
								        		  cprob = "wupyou";
							        	      }	else {
		        		                            System.out.println("Error Class Prob");
		        	                             } 
							        	  }	// #9  
						        	  } // #8	  
					        	  } // #7	  
		        	           } // #6
			        	  } // #5	  
		        	  } // #4	  
		          } // #3
		        	 
		     } // #2
		 } // #1	
		
		/**
		 * Run HH-RILA
		 */
        for (int oneClass = 0; oneClass < maxAllClasses; oneClass++) {
        	newArgsHHRILA[0] = numberTrials; // Number of Trials
            newArgsHHRILA[1] = "10"; // Iterations/LLH
            newArgsHHRILA[2] = "-1"; // Seed
            newArgsHHRILA[3] = "1";  // Algorithm: HH-RILA (1)
            newArgsHHRILA[4] = getClassProb(cprob, oneClass);
            newArgsHHRILA[5] = "3"; // Number of LLHs
            newArgsHHRILA[6] = baseSrcDir;
            newArgsHHRILA[7] = baseDestDir;
            HHLARILAMainReal.main(newArgsHHRILA);
        	selectedLLHs(Integer.parseInt(newArgsHHRILA[0]), newArgsHHRILA[4], baseSrcDir, baseDestDir);
        } 
			
        /**
	     * Calculate the quality indicators for the real problems, considering the True Known Pareto Front.  
	     */
		String[] allAlgs = {"HRISE_M", "HRISE_R", "HRMA", "IBEA", "NSGAII", "SPEA2", "HH-ALL", "HH-CF", "HH-RILA" };
		for (int indQ = 0; indQ < allProblems.length; indQ++) {
			qualIndReal.calculateQualityIndicatorsReal(allAlgs, allProblems[indQ], baseSrcDir, baseDestDir);
		}
		
		/**
	     * Copy the EFGs.
	     */
		System.out.println("........... Copying Results: EFG ..............................................................");
		copyResultDirectoryRunner(baseSrcDir + "/graph",
				            baseDestDir + "/EFG/");
				
		System.out.println("@@@@@@@@@@@@@@ THE END!");
		 
		
	}
	
	// **************************************************
    // @return Returns the group of problem instances for the HH-RILA algorithm. 
    // **************************************************
	public static String getClassProb(String pairCl, int specCl) {
		String groupProbInstances = " ";
		if (pairCl.equals("vc") && (specCl == 0)) {
			groupProbInstances = "VC"; //prClass = "VC";
		} else { // #1
			if (pairCl.equals("wr") && (specCl == 0)) {
			   groupProbInstances = "WR";
	        } else { //#2
	        	if (pairCl.equals("cssp") && (specCl == 0)) {
	 			   groupProbInstances = "CS";
	 		    } else { //#3
	 		    	if (pairCl.equals("cssp") && (specCl == 1)) {
	 	 			   groupProbInstances = "SP";
	 		    	} else { //#4
	 		    		if (pairCl.equals("gu1-5") && (specCl == 0)){
	 		    			groupProbInstances = "GU1-5";
	 		    		} else { //#5
		 		    		if (pairCl.equals("gu6-10") && (specCl == 0)){
		 		    			groupProbInstances = "GU6-10";
		 		    		} else { //#6
			 		    		if (pairCl.equals("gu11-15") && (specCl == 0)){
			 		    			groupProbInstances = "GU11-15";
			 		    		} else { //#7
				 		    		if (pairCl.equals("gu16-20") && (specCl == 0)){
				 		    			groupProbInstances = "GU16-20";
				 		    		} else { //#8
					 		    		if (pairCl.equals("gu21-24") && (specCl == 0)){
					 		    			groupProbInstances = "GU21-24";
					 		    		} else { //#9
						 		    		if (pairCl.equals("lof1-4") && (specCl == 0)){
						 		    			groupProbInstances = "LOF1-4";
						 		    		} else { //#10
						 		    			if (pairCl.equals("wupyou") && (specCl == 0)) {
						 		    				   groupProbInstances = "WUP1-3";
						 		 		        } else { //#11
						 		 		    	    if (pairCl.equals("wupyou") && (specCl == 1)) {
						 		 	 			       groupProbInstances = "YOU1";
						 		 		    	    }	else { 
	 		    		                                 System.out.println("Error: Problem!");
	 		    		                               }
						 		 		        } //#11  
						 		 		   } //#10  	    
					 		    		} //#9	
				 		    		} //#8	
			 		    		} //#7	
		 		    		} //#6	
	 		    	   } //#5	
	 		    	} //#4	
	 		    } //#3
		   } // #2
		} // #1
		
		return groupProbInstances;
		
	}
	
	// **************************************************
    // Helps to calculate the Utilisation Rate within HH-RILA. 
    // **************************************************
	public static void selectedLLHs(int tr, String probClass, String srDir, String deDir) throws IOException {
		FileUtils.deleteDirectory(new File("exec"));
		String dir = srDir+"/HF_Config_Benchmark/Results/HH-RILA/Iter_10/run_";
    	int nprob = -1;
    	switch(probClass) {
    	   	case "WFG":
        		nprob = 9;
        		break;
        	case "DTLZ":
        		nprob =  7;
        		break;
        	case "VC":
        		nprob = 4;
        		break;
        	case "WR":
        		nprob = 6; 
        		break;
        	case "CS":
        		nprob = 3;
        		break;
        	case "SP":
        		nprob = 3;
        		break;
        	case "GU1-5":
        	case "GU6-10":
        	case "GU11-15":
        	case "GU16-20":
           	case "GU26-30":	
        		nprob = 5;
        		break;	
        	case "GU21-24":
        	case "LOF1-4":
        		nprob = 4;
        		break;	
        	case "WUP1-3":
        		nprob = 3;
        		break;	
        	case "YOU1":
        		nprob = 1;
        		break;	
        	default:
        		nprob = -1;
    	}
    	
    	for (int iPr = 0; iPr < nprob; iPr++) {
    		Map<String,Integer> cnQualSel = new LinkedHashMap<String,Integer>();
			cnQualSel.put("NSGAII", 0);
			cnQualSel.put("SPEA2", 0);
	  		cnQualSel.put("IBEA", 0);
    	    String versionHH = "HH-RILA";
	  		
    		for (int iTrial = 0; iTrial < tr; iTrial++) {
    		    Scanner scanner = new Scanner(new File(dir + iTrial+ "/"+ probClass + (iPr+1) + "_eachDPChosenHeuristicList.txt"));
    			List<Integer> tall = new ArrayList<Integer>();
    			while(scanner.hasNextInt()){
    				tall.add(scanner.nextInt());
    	   		}
    	
    			for (int iFile = 0; iFile < tall.size(); iFile++) {
    				switch(tall.get(iFile)) {
    					case 0: // NSGA-II
    						cnQualSel.put("NSGAII",cnQualSel.get("NSGAII")+1);
    						break;
    					case 1: // SPEA2
    						cnQualSel.put("SPEA2",cnQualSel.get("SPEA2")+1);
    						break;	
    					case 2: // IBEA
    						cnQualSel.put("IBEA",cnQualSel.get("IBEA")+1);
    						break;	
    					default:
    						System.out.println("Error");
    				}
    			}
    			
    		}	
    		   		
    		cnQualSel.put("NSGAII",cnQualSel.get("NSGAII") - 3*tr);
    		cnQualSel.put("SPEA2",cnQualSel.get("SPEA2") - 3*tr);
    		cnQualSel.put("IBEA",cnQualSel.get("IBEA") - 3*tr);
    		    		
    		// Calculate the percentage of selected LLHs
	  		int cnGenSel = cnQualSel.get("NSGAII") + cnQualSel.get("SPEA2") + cnQualSel.get("IBEA") ; 
	  		Map<String,Double> prQualSel = new LinkedHashMap<String,Double>();
			prQualSel.put("NSGAII", cnQualSel.get("NSGAII") / (double) cnGenSel );
			prQualSel.put("SPEA2", cnQualSel.get("SPEA2") / (double) cnGenSel );
			prQualSel.put("IBEA", cnQualSel.get("IBEA") / (double) cnGenSel );
						
			ProblemsWrapperLARILA pwl = new ProblemsWrapperLARILA(probClass, iPr);
			String toCSV = pwl.getProblemString()+"_"+pwl.getM()+",Sel,Psel\n";
			String[] algName = {"NSGAII", "IBEA", "SPEA2"};
			
			for (int line = 0; line < algName.length; line++) {
				toCSV += algName[line] + "," +
							cnQualSel.get(algName[line]) + "," +
							prQualSel.get(algName[line]) + "\n";
							
			}
						
			SaveFiles.saveToCSV(versionHH,pwl.getProblemString()+"_"+pwl.getM(),toCSV);
		  
			System.out.println("#### Copying Results: HH-RILA EXEC .......................................................................");
			copyResultDirectoryRunner(srDir + "/exec",
		            deDir + "/EXEC/");
			
						
		      		    		
    	} // Problem Instances
    }
    
	// **************************************************
    // Copies directory.
    // **************************************************
    public static void copyResultDirectoryRunner(String sourceDir, String destDir) {
		File source = new File(sourceDir);
		File dest = new File(destDir);
		try {
		    FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}

}
