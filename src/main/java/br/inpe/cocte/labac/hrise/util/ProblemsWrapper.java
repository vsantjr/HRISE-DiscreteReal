package br.inpe.cocte.labac.hrise.util;

public class ProblemsWrapper {

	private int l; // decision/distance variables
	private int m; // number objectives
	private String problemString;
	
	
	public ProblemsWrapper(String opt){
	  switch(opt) {
        case "d1" :
      	    this.l = 7; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "DTLZ1";
            break;
        case "d2" :
      	    this.l = 12; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "DTLZ2";
            break;
        case "d3" :
      	    this.l = 12; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "DTLZ3"; 
            break;
        case "d4" :
      	    this.l = 12; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "DTLZ4";
            break; 
        case "d5" :
      	    this.l = 12; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "DTLZ5";
            break;
        case "d6" :
      	    this.l = 12; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "DTLZ6";
            break;  
        case "d7" :
      	    this.l = 22; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "DTLZ7";
            break;  
        case "w1" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG1";
            break;    
        case "w2" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG2";
            break;    
        case "w3" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG3";
            break;  
        case "w4" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG4";
            break;     
        case "w5" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG5";
            break;     
        case "w6" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG6";
            break;     
        case "w7" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG7";
            break;     
        case "w8" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG8";
            break;     
        case "w9" :
      	    this.l = 20; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "WFG9";
            break;     
        case "vc1" : // {Mass, Ain, Intrusion}
      	    this.l = 5; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "VC1";
            break;    
        case "vc2" : // {Mass, Ain}
      	    this.l = 5; // Decision/DISTANCE variables. Original = 20
            this.m = 2; // Number of objectives. Original = 2
            this.problemString = "VC2";
            break;    
        case "vc3" : // {Mass, Intrusion}
      	    this.l = 5; // Decision/DISTANCE variables. Original = 20
            this.m = 2; // Number of objectives. Original = 2
            this.problemString = "VC3";
            break;     
        case "vc4" : // {Ain, Intrusion}
      	    this.l = 5; // Decision/DISTANCE variables. Original = 20
            this.m = 2; // Number of objectives. Original = 2
            this.problemString = "VC4";
            break;     
        case "mac1" : // {f1, f2, f3, f4}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 4; // Number of objectives. Original = 2
            this.problemString = "MAC1";
            break; 
        case "mac2" : // {f1, f2, f3}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "MAC2";
            break;    
        case "mac3" : // {f1, f2, f4}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "MAC3";
            break;  
        case "mac4" : // {f1, f3, f4}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "MAC4";
            break;   
        case "mac5" : // {f2, f3, f4}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 3; // Number of objectives. Original = 2
            this.problemString = "MAC5";
            break;      
        case "wr1" : // {f1, f2, f3, f4, f5}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 5; // Number of objectives. Original = 2
            this.problemString = "WR1";
            break;      
        case "wr2" : // {f1, f2, f3, f4}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 4; // Number of objectives. Original = 2
            this.problemString = "WR2";
            break;    
        case "wr3" : // {f1, f2, f3, f5}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 4; // Number of objectives. Original = 2
            this.problemString = "WR3";
            break;     
        case "wr4" : // {f1, f2, f4, f5}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 4; // Number of objectives. Original = 2
            this.problemString = "WR4";
            break;   
        case "wr5" : // {f1, f3, f4, f5}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 4; // Number of objectives. Original = 2
            this.problemString = "WR5";
            break;  
         case "wr6" : // {f2, f3, f4, f5}
      	    this.l = 3; // Decision/DISTANCE variables. Original = 20
            this.m = 4; // Number of objectives. Original = 2
            this.problemString = "WR6";
            break; 
         case "cd1" : // {f2, f3, f4, f5}
       	     this.l = 11; // Decision/DISTANCE variables. Original = 20
             this.m = 9; // Number of objectives. Original = 2
             this.problemString = "CD1";
             break; 
         case "cs1" : // {f2, f3, f4, f5}
       	     this.l = 7; // Decision/DISTANCE variables. Original = 20
             this.m = 3; // Number of objectives. Original = 2
             this.problemString = "CS1";
             break;   
         case "cs2" : // {f2, f3, f4, f5}
       	     this.l = 7; // Decision/DISTANCE variables. Original = 20
             this.m = 2; // Number of objectives. Original = 2
             this.problemString = "CS2";
             break;
         case "cs3" : // {f2, f3, f4, f5}
       	     this.l = 7; // Decision/DISTANCE variables. Original = 20
             this.m = 2; // Number of objectives. Original = 2
             this.problemString = "CS3";
             break;  
         case "cs4" : // {f2, f3, f4, f5}
       	     this.l = 7; // Decision/DISTANCE variables. Original = 20
             this.m = 2; // Number of objectives. Original = 2
             this.problemString = "CS4";
             break;   
         case "sp1" : // {f2, f3, f4, f5}
       	     this.l = 4; // Decision/DISTANCE variables. Original = 20
             this.m = 3; // Number of objectives. Original = 2
             this.problemString = "SP1";
             break;  
         //case "sp2" : // {f2, f3, f4, f5}
       	   //  this.l = 4; // Decision/DISTANCE variables. Original = 20
             //this.m = 2; // Number of objectives. Original = 2
             //this.problemString = "SP2";
             //break;   
         case "sp2" : // {f2, f3, f4, f5}
       	     this.l = 4; // Decision/DISTANCE variables. Original = 20
             this.m = 2; // Number of objectives. Original = 2
             this.problemString = "SP2";
             break; 
         case "sp3" : // {f2, f3, f4, f5}
       	     this.l = 3; // Decision/DISTANCE variables. Original = 20
             this.m = 2; // Number of objectives. Original = 2
             this.problemString = "SP3";
             break;   
         case "gu1": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI1";
             break;   
         case "gu2": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI2";
             break; 
         case "gu3": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI3";
             break;   
         case "gu4": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI4";
             break;      
         case "gu5": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI5";
             break;   
         case "gu6": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI6";
             break;   
         case "gu7": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI7";
             break;  
         case "gu8": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI8";
             break;   
         case "gu9": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI9";
             break;  
         case "gu10": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI10";
             break;   
         case "gu11": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI11";
             break; 
         case "gu12": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI12";
             break;   
         case "gu13": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI13";
             break;  
         case "gu14": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI14";
             break;   
         case "gu15": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI15";
             break;     
         case "gu16": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI16";
             break;   
         case "gu17": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI17";
             break;  
         case "gu18": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI18";
             break;       
         case "gu19": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI19";
             break; 
         case "gu20": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI20";
             break;   
         case "gu21": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI21";
             break;  
         case "gu22": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI22";
             break;      
         case "gu23": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI23";
             break;   
         case "gu24": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI24";
             break;    
         case "gu25": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI25";
             break;    
         case "gu26": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI26";
             break;    
         case "gu27": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI27";
             break;
         case "gu28": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI28";
             break;   
         case "gu29": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI29";
             break;   
         case "gu30": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "GUI30";
             break;    
         case "lof1": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "LOF1";
             break;   
         case "lof2": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "LOF2";
             break;    
         case "lof3": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "LOF3";
             break; 
         case "lof4": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "LOF4";
             break;    
         case "wup1": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "WUP1";
             break;  
         case "wup2": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "WUP2";
             break;   
         case "wup3": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "WUP3";
             break;    
         case "you1": 
       	     this.l = 10; // Decision/DISTANCE variables. Original = 20
             this.m = 4; // Number of objectives. Original = 2
             this.problemString = "YOU1";
             break;    
        default :
      	  l = m = 0;
      	  problemString = "NONE";
      	  //versionHH = "NONE";
      	  //trials = 0;
      	  //maxIterations = decisionPoints = 0;
           System.out.println("%%%%% Invalid Problem");
       }
    } 
		
	
		
	
	public int getL() {
		return this.l;
	}
	
	public int getM() {
		return this.m;
	}
	
	public String getProblemString() {
		return this.problemString;
	}
	
	
	
	
}