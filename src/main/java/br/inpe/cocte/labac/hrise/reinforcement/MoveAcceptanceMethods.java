package br.inpe.cocte.labac.hrise.reinforcement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;


public class MoveAcceptanceMethods {
	
	private double lowerUtility;
	private double upperUtility;
	private double lowerQuality;
	private int memoryLength;
	private double alpha;
	private List<LQValue> allQuality;
	private Map<String, Double> allUtility; 
		
	private static MoveAcceptanceMethods instance = null;
	protected MoveAcceptanceMethods() { 
	     this.allQuality = new ArrayList<LQValue>();
		 this.allUtility = new LinkedHashMap<String,Double>();
		 this.setLowUpBounds(1000);
		 this.memoryLength = 2;
		 this.alpha = 0.5;
	}
	
	
	public static MoveAcceptanceMethods getInstance() {
	    if(instance == null) {
	        instance = new MoveAcceptanceMethods();
	    }
	    	      
	    return instance;
	}
	
	
	public void initialiseUtilityValues(String[] llh, double[] ut) {
		for (int i = 0; i < llh.length; i++) {
			allUtility.put(llh[i], ut[i]);
		}
	}
	
	
	public void initialiseUtilityValues(String[] llh, double beta) {
		for (int i = 0; i < llh.length; i++) {
			allUtility.put(llh[i], beta*upperUtility);
		}
	}
	
	
    private void setLowUpBounds(int n, int f) {
        this.lowerUtility = 0.0;	
        this.upperUtility = (double) n*5.0*f;
        this.lowerQuality = 0.0; // better Euclidean distance
    }
	
    
    private void setLowUpBounds(int f) {
        this.lowerUtility = 0.0;	
        this.upperUtility = (double) f;
        this.lowerQuality = 0.9; 
    }
    
    
    public void setMemoryLenght(int m) {
    	this.memoryLength = m;
    }
    
    
    public int getMemoryLenght() {
    	return this.memoryLength;
    }
    
    
    public void setCurrentQuality(LQValue llhQuality) {
    	allQuality.add(llhQuality);
    }
    
       
    public double getCurrentQuality (int pos) {
       	LQValue lastElement = new LQValue(); 
    	if (pos == -1) { // last
    		
    		 lastElement = allQuality.get(allQuality.size()-1); 
    	} else { // defined position 
    		 lastElement = allQuality.get(pos);
        }
    	   	
        return lastElement.getQuality();	
    }

    
    public List<LQValue> getAllQuality(){
    	return this.allQuality;
    }
    
    public int getAllQualitySize() {
    	return this.allQuality.size();
    }
    
    
    public Map<String,Double> getAllUtility(){
    	return this.allUtility;
    }
    
    
    public void clearAllQuality() {
    	this.allQuality.clear();
    }
    
    
    public void clearAllUtility() {
    	this.allUtility.clear();
    }
    
    
    public double getAlpha() {
    	return this.alpha;
    }
    
    
    public void initialiseAllDataMoveAcceptance() {
    	 this.allQuality.clear();
    	 this.allUtility.clear();
    }
    
    public boolean selectHyperMoveAcceptance(String hh, LQValue llhQuality, int iter, int maxIter) {
    	
    	boolean accept = false;
    	switch(hh) {
	     case "HRISE_A" :
           System.out.println(" ***** HRISE_A (AUTHORITY) *******");
           accept = this.moveResponsibility(llhQuality, iter, maxIter);
           break;
        case "HRISE_V" :
       	   //selhh = "WIN"; 
        	System.out.println("**** HRISE_V (MAJORITY) **********");	
       	   accept = this.moveMajority(llhQuality, iter, maxIter);
           break;
         case "HRGA" :
        	   //selhh = "WIN"; 
         	System.out.println("**** HRGA (Random Group Decision Acceptance) ********* ");	
        	accept = this.moveRandom(llhQuality, iter, maxIter);
            break;   
        default :
           System.out.println("%%%%% Invalid Hyper Move Acceptance!");
       }	
	 
       return accept;	
     }	
    
    
    public boolean selectHyperMoveAcceptanceAlpha(String hh, LQValue llhQuality, int iter, int maxIter, double a) {
    	this.alpha = a;
    	boolean accept = false;
    	switch(hh) {
	     case "HRISE_R" :
           System.out.println("#### HRISE_R (RESPONSIBILIYY) ####");
           accept = this.moveResponsibility(llhQuality, iter, maxIter);
           break;
        case "HRISE_M" :
       	   System.out.println("#### HRISE_M (MAJORITY) ####");	
       	   accept = this.moveMajority(llhQuality, iter, maxIter);
           break;
         case "HRMA" :
           	System.out.println("#### HRMA (RANDOM) ####");	
        	accept = this.moveRandom(llhQuality, iter, maxIter);
            break;   
        default :
           System.out.println("#### Invalid Move Acceptance Method!");
       }	
	 
       return accept;	
     }	
    
        
	public boolean moveResponsibility(LQValue llhQuality, int iter, int maxIter){
		   
		 double quality = -1.0; // initial value 
		 boolean accept = false;
		
	     quality = llhQuality.getQuality(); 
	     
	     /////// Try GDA: Begin ///////
	     if (quality > getCurrentQuality(-1)) {
	    	 
	    	 accept = true;
	    	 System.out.println("ACCEPT - GDA First");
	     } else {
	    	 
	    	 double propIter = (iter / (double) maxIter);
	    	 double minusPI = 1.0 - propIter;
	    	 double secQuality = lowerQuality + (allQuality.get(0).getQuality()-lowerQuality)*minusPI;
	    	 if (quality > secQuality) {
	    		 accept = true;
	    		 
	    		 System.out.println("ACCEPT - GDA Second");
	    	 }
	     } ////// Try GDA: End ///////
	     
	     /////// Try QUALIFIED LATE ACCEPTANCE: Begin //////
	     if (!accept) {
	    	 int pos;
	    	 if (allQuality.size() <= memoryLength) {
	    		 pos = 0;
	    	 } else {
	    		 pos = allQuality.size() - memoryLength;
	    	 }
	    		 
	    	 if (quality > getCurrentQuality(pos)) {
		    	 
		    	 accept = true;
		    	 System.out.println("ACCEPT - QUALIFIED LATE");
		     } 
		    	 
	         
	     } /////// Try QUALIFIED LATE ACCEPTANCE: End ///////
	     
	     ////// Try MEAN QUALIFIED LATE ACCEPTANCE: Begin //////
	     if (!accept) {
	    	 int startPos;
	    	 int endPos = allQuality.size();
	    	 double sum = 0.0;
	    	 double meanQual;
	    	 if (allQuality.size() <= memoryLength) {
	    		 startPos = 0;
	    	 } else {
	    		 startPos = allQuality.size() - memoryLength;
	    	 }
	    	 
	    	 for (int i = startPos; i < (endPos - startPos); i++) {
	    		 sum += getCurrentQuality(i);
	    	 }
	    	 
	    	 meanQual = sum / (double) (endPos - startPos); 
	    		 
	    	 if (quality > meanQual) {
		    	 
		    	 accept = true;
		    	 System.out.println("ACCEPT - MEAN QUALIFIED LATE");
		     } 
		    	 
	         
	     }  ////// Try MEAN QUALIFIED LATE ACCEPTANCE: End //////
	     
	     if(accept)
	    	 rewardUtilityValue(llhQuality.getLLH(), iter, maxIter);
	     else
	    	 penaliseUtilityValue(llhQuality.getLLH(), iter, maxIter);
	     
		return accept; 
		   
	}	   
	
	public boolean moveMajority(LQValue llhQuality, int iter, int maxIter){
		   
		 double quality = -1.0; // initial value 
		 boolean accept = false;
		 double[] vote = new double[3]; // 3 move acceptance methods: GDA, QUALIFIED LATE, MEAN QUALIFIED LATE
		 Arrays.fill(vote, 0.0);
		  
	     quality = llhQuality.getQuality(); 
	     
	     /////// Try GDA: Begin ///////
	     if (quality > getCurrentQuality(-1)) {
	    	 
	    	 vote[0] = 1.0;
	    	 System.out.println("ACCEPT - GDA First");
	     } else {
	    	 
	    	 double propIter = (iter / (double) maxIter);
	    	 double minusPI = 1.0 - propIter;
	    	 double secQuality = lowerQuality + (allQuality.get(0).getQuality()-lowerQuality)*minusPI;
	    	 if (quality > secQuality) {
	    	  	 
	    	 
	    		 vote[0] = 1.0;
	    		 System.out.println("ACCEPT - GDA Second");
	    	 }
	     } ////// Try GDA: End ///////
	     
	     /////// Try QUALIFIED LATE ACCEPTANCE: Begin //////
	     int pos;
    	 if (allQuality.size() <= memoryLength) {
    		 pos = 0;
    	 } else {
    		 pos = allQuality.size() - memoryLength;
    	 }
    		 
    	 if (quality > getCurrentQuality(pos)) {
	    	 
	    	 vote[1] = 1.0;
	    	 System.out.println("ACCEPT - QUALIFIED LATE");
	     } 
	    	
         /////// Try QUALIFIED LATE ACCEPTANCE: End ///////
	     
	    	     
	     ////// Try MEAN QUALIFIED LATE ACCEPTANCE: Begin //////
    	 int startPos;
    	 int endPos = allQuality.size();
    	 double sum = 0.0;
    	 double meanQual;
    	 if (allQuality.size() <= memoryLength) {
    		 startPos = 0;
    	 } else {
    		 startPos = allQuality.size() - memoryLength;
    	 }
    	 
    	 for (int i = startPos; i < (endPos - startPos); i++) {
    		 sum += getCurrentQuality(i);
    	 }
    	 
    	 meanQual = sum / (double) (endPos - startPos); 
    		 
    	 if (quality > meanQual) {
	    	 
	    	 vote[2] = 1.0;
	    	 System.out.println("ACCEPT - MEAN QUALIFIED LATE");
	     } 
	    	 
         ////// Try MEAN QUALIFIED LATE ACCEPTANCE: End //////
	     
	     double sumVote = 0.0;
	     for (int i = 0; i < vote.length; i++) {
	    	 sumVote += vote[i];
	     }
	     
	     if (sumVote > 1.0) {
	    	 rewardUtilityValue(llhQuality.getLLH(), iter, maxIter);
	    	 accept = true;
	     } else {
	    	 penaliseUtilityValue(llhQuality.getLLH(), iter, maxIter);
	    	 accept = false;
	     }	 
	     
	     
		return accept; 
		   
	}	   
	
	
	public void rewardUtilityValue(String llh, int iter, int maxIter){
		
		double utility = allUtility.get(llh);
		
		double propIter = (iter / (double) maxIter);
   	    double minusPI = 1.0 - propIter;
		
		
   	    utility = utility + alpha*minusPI;
		if ((utility >= lowerUtility) && (utility <= upperUtility))
		   allUtility.put(llh, utility);
	
	}
	
    public void penaliseUtilityValue(String llh, int iter, int maxIter){
		
		double utility = allUtility.get(llh);
		
		double propIter = (iter / (double) maxIter);
   	    double minusPI = 1.0 - propIter;
		utility = utility - alpha*minusPI;
		if ((utility >= lowerUtility) && (utility <= upperUtility))
		   allUtility.put(llh, utility);
	
	}
    
    
    public boolean moveRandom(LQValue llhQuality, int iter, int maxIter){
		   
		 double quality = -1.0; // initial value 
		 boolean accept = false;
				  
	     quality = llhQuality.getQuality(); 
	     
	     PopulationHandler popHandler = new PopulationHandler();
	     int ma = popHandler.getRandomSolution(3);
	     switch(ma) {
	       case 0 :
             
             /////// Try GDA: Begin ///////
    	     if (quality > getCurrentQuality(-1)) {
    	    	 
    	    	 accept = true;
    	    	 System.out.println("ACCEPT - GDA First");
    	     } else {
    	    	 
    	    	 double propIter = (iter / (double) maxIter);
    	    	 double minusPI = 1.0 - propIter;
    	    	 double secQuality = lowerQuality + (allQuality.get(0).getQuality()-lowerQuality)*minusPI;
    	    	 if (quality > secQuality) {
    	    		 accept = true;
    	    		 
    	    		 System.out.println("ACCEPT - GDA Second");
    	    	 }
    	     } ////// Try GDA: End ///////
                     
             break;
           
	       case 1 :
       	   
        	  /////// Try QUALIFIED LATE ACCEPTANCE: Begin //////
     	      if (!accept) {
     	    	 int pos;
     	    	 if (allQuality.size() <= memoryLength) {
     	    		 pos = 0;
     	    	 } else {
     	    		 pos = allQuality.size() - memoryLength;
     	    	 }
     	    		 
     	    	 if (quality > getCurrentQuality(pos)) {
     		    	 
     		    	 accept = true;
     		    	 System.out.println("ACCEPT - QUALIFIED LATE");
     		     } 
     		    	 
     	         
     	     } /////// Try QUALIFIED LATE ACCEPTANCE: End ///////
        	         	  
              break;
              
            case 2 :
        	            	   
         	   ////// Try MEAN QUALIFIED LATE ACCEPTANCE: Begin //////
      	       if (!accept) {
      	    	 int startPos;
      	    	 int endPos = allQuality.size();
      	    	 double sum = 0.0;
      	    	 double meanQual;
      	    	 if (allQuality.size() <= memoryLength) {
      	    		 startPos = 0;
      	    	 } else {
      	    		 startPos = allQuality.size() - memoryLength;
      	    	 }
      	    	 
      	    	 for (int i = startPos; i < (endPos - startPos); i++) {
      	    		 sum += getCurrentQuality(i);
      	    	 }
      	    	 
      	    	 meanQual = sum / (double) (endPos - startPos); 
      	    		 
      	    	 if (quality > meanQual) {
      		    	 
      		    	 accept = true;
      		    	 System.out.println("ACCEPT - MEAN QUALIFIED LATE");
      		     } 
      		    	 
      	         
      	      }  ////// Try MEAN QUALIFIED LATE ACCEPTANCE: End //////
         	            	   
               break;   
            
            default :
                 System.out.println("#### Invalid Move Acceptance Method!");
       }	
	     
	    
	       
	     
	    if(accept)
	    	 rewardUtilityValue(llhQuality.getLLH(), iter, maxIter);
	    else
	    	 penaliseUtilityValue(llhQuality.getLLH(), iter, maxIter);
	     
		return accept; 
		   
	}	   

}
