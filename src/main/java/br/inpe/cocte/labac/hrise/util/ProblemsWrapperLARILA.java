package br.inpe.cocte.labac.hrise.util;

public class ProblemsWrapperLARILA {

	private int l; // decision/distance variables
	private int m; // number objectives
	private int k;
	private String problemString;
	
	
	public ProblemsWrapperLARILA(String probClass, int index){
	  switch(probClass) {
        case "DTLZ" :
        	switch(index) {
        	  case 0:
        	    this.l = 7; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "DTLZ1";
                break;
        	  case 1 :
            	this.l = 12; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "DTLZ2";
                break;  
        	  case 2 :
            	  this.l = 12; // Decision/DISTANCE variables. Original = 20
                  this.m = 3; // Number of objectives. Original = 2
                  this.problemString = "DTLZ3"; 
                  break;  
        	  case 3 :
            	  this.l = 12; // Decision/DISTANCE variables. Original = 20
                  this.m = 3; // Number of objectives. Original = 2
                  this.problemString = "DTLZ4";
                  break; 
        	  case 4 :
            	  this.l = 12; // Decision/DISTANCE variables. Original = 20
                  this.m = 3; // Number of objectives. Original = 2
                  this.problemString = "DTLZ5";
                  break;    
        	  case 5 :
            	  this.l = 12; // Decision/DISTANCE variables. Original = 20
                  this.m = 3; // Number of objectives. Original = 2
                  this.problemString = "DTLZ6";
                  break;   
        	  case 6 :
            	  this.l = 22; // Decision/DISTANCE variables. Original = 20
                  this.m = 3; // Number of objectives. Original = 2
                  this.problemString = "DTLZ7";
                  break;   
        	  default :
              	  System.out.println("%%%%% Invalid Problem INDEX");    
            }
        break;
       // ###########################################
            
        case "WFG":
        	switch(index) {
              case 0 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG1";
                break;    
              case 1 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG2";
                break;    
              case 2 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG3";
                break;  
              case 3 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG4";
                break;     
              case 4 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG5";
                break;     
             case 5 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG6";
                break;     
             case 6 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG7";
                break;     
             case 7 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG8";
                break;     
             case 8 :
      	        this.l = 20; // Decision/DISTANCE variables. Original = 20
                this.m = 3; // Number of objectives. Original = 2
                this.problemString = "WFG9";
                break;     
             default :
             	  System.out.println("%%%%% Invalid Problem INDEX");     
           }
        break;
        // ####################	
           
        case "VC":
        	switch(index) {	
        	   case 0 : // {Mass, Ain, Intrusion}
      	         this.l = 5; // Decision/DISTANCE variables. Original = 20
                 this.m = 3; // Number of objectives. Original = 2
                 this.problemString = "VC1";
                 break;    
               case 1 : // {Mass, Ain}
      	         this.l = 5; // Decision/DISTANCE variables. Original = 20
                 this.m = 2; // Number of objectives. Original = 2
                 this.problemString = "VC2";
                 break;    
               case 2 : // {Mass, Intrusion}
      	         this.l = 5; // Decision/DISTANCE variables. Original = 20
                 this.m = 2; // Number of objectives. Original = 2
                 this.problemString = "VC3";
                 break;     
               case 3 : // {Ain, Intrusion}
      	         this.l = 5; // Decision/DISTANCE variables. Original = 20
                 this.m = 2; // Number of objectives. Original = 2
                 this.problemString = "VC4";
                 break;   
               default :
              	  System.out.println("%%%%% Invalid Problem INDEX");     
              }
        break;	
       //############################################
         
        case("WR"): 
        	switch(index) {
              case 0 : // {f1, f2, f3, f4, f5}
      	        this.l = 3; // Decision/DISTANCE variables. Original = 20
                this.m = 5; // Number of objectives. Original = 2
                this.problemString = "WR1";
                break;      
              case 1 : // {f1, f2, f3, f4}
      	        this.l = 3; // Decision/DISTANCE variables. Original = 20
                this.m = 4; // Number of objectives. Original = 2
                this.problemString = "WR2";
                break;    
              case 2 : // {f1, f2, f3, f5}
      	        this.l = 3; // Decision/DISTANCE variables. Original = 20
                this.m = 4; // Number of objectives. Original = 2
                this.problemString = "WR3";
                break;     
              case 3 : // {f1, f2, f4, f5}
      	        this.l = 3; // Decision/DISTANCE variables. Original = 20
                this.m = 4; // Number of objectives. Original = 2
                this.problemString = "WR4";
                break;   
              case 4 : // {f1, f3, f4, f5}
      	        this.l = 3; // Decision/DISTANCE variables. Original = 20
                this.m = 4; // Number of objectives. Original = 2
                this.problemString = "WR5";
                break;  
              case 5 : // {f2, f3, f4, f5}
      	        this.l = 3; // Decision/DISTANCE variables. Original = 20
                this.m = 4; // Number of objectives. Original = 2
                this.problemString = "WR6";
                break; 
              default :
              	  System.out.println("%%%%% Invalid Problem INDEX");   
        	  }
        break;
        //##################################
        
        case("CS"): 
        	switch(index) {
              case 0 : // {f2, f3, f4, f5}
       	         this.l = 7; // Decision/DISTANCE variables. Original = 20
                 this.m = 3; // Number of objectives. Original = 2
                 this.problemString = "CS1";
                 break;   
              case 1 : // {f2, f3, f4, f5}
       	         this.l = 7; // Decision/DISTANCE variables. Original = 20
                 this.m = 2; // Number of objectives. Original = 2
                 this.problemString = "CS2";
                 break;
              case 2 : // {f2, f3, f4, f5}
       	         this.l = 7; // Decision/DISTANCE variables. Original = 20
                 this.m = 2; // Number of objectives. Original = 2
                 this.problemString = "CS3";
                 break;  
              default :
              	  System.out.println("%%%%% Invalid Problem INDEX");      
        	   }    
        break;
        //######################## 
        
        case("SP"): 
        	switch(index) {
           		case 0 : // {f2, f3, f4, f5}
        			this.l = 4; // Decision/DISTANCE variables. Original = 20
        			this.m = 3; // Number of objectives. Original = 2
        			this.problemString = "SP1";
        			break;  
                case 1 : // {f2, f3, f4, f5}
                	this.l = 4; // Decision/DISTANCE variables. Original = 20
                	this.m = 2; // Number of objectives. Original = 2
                	this.problemString = "SP2";
                	break; 
                case 2 : // {f2, f3, f4, f5}
                	this.l = 3; // Decision/DISTANCE variables. Original = 20
                	this.m = 2; // Number of objectives. Original = 2
                	this.problemString = "SP3";
                	break;    
                default :
                	  System.out.println("%%%%% Invalid Problem INDEX");  	
        	}
        break;
        //#####################
        
        case("GU1-5"): 
        	switch(index) {
           		case 0 : // {f2, f3, f4, f5}
        			this.l = 10; // Decision/DISTANCE variables. Original = 20
        			this.m = 4; // Number of objectives. Original = 2
        			this.problemString = "GUI1";
        			break;  
                case 1 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI2";
                	break; 
                case 2 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI3";
                	break;    
                case 3 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI4";
                	break;	
                case 4 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI5";
                	break;	
                default :
              	  System.out.println("%%%%% Invalid Problem INDEX");  	
      	    }
            break;
            //#####################
        case("GU6-10"):
        	switch(index) {
                case 0 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI6";
                	break;	
                case 1 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI7";
                	break;	
                case 2 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI8";
                	break;	
                case 3 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI9";
                	break;	
                case 4 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI10";
                	break;	
                default :
                	  System.out.println("%%%%% Invalid Problem INDEX"); 	
        } 
        break;
        //#################
        
        
        case("GU11-15"):
        	switch(index) {
                case 0 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI11";
                	break;	
                case 1 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI12";
                	break;	
                case 2 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI13";
                	break;	
                case 3 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI14";
                	break;	
                case 4 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI15";
                	break;
                default :
              	  System.out.println("%%%%% Invalid Problem INDEX"); 	
        	}
        break;
        //#########
        
        case("GU16-20"):
        	switch(index) {
        
                case 0 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI16";
                	break;	
                case 1 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI17";
                	break;	
                case 2 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI18";
                	break;	
                case 3 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI19";
                	break;	
                case 4 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI20";
                	break;	
                default :
                	  System.out.println("%%%%% Invalid Problem INDEX"); 		
        	}
        break;
        // ###########
        
        case("GU21-24"):
        	switch(index) {
                case 0 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI21";
                	break;		
                case 1 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI22";
                	break;	
                case 2 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI23";
                	break;	
                case 3 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI24";
                	break;		
                //case 4 : // {f2, f3, f4, f5}
                	//this.l = 10; // Decision/DISTANCE variables. Original = 20
                	//this.m = 4; // Number of objectives. Original = 2
                	//this.problemString = "GUI25";
                	//break;		
                default :
              	  System.out.println("%%%%% Invalid Problem INDEX"); 	
        	}
        break;
        //############
        case("GU26-30"):
        	switch(index) {
        
                case 0 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI26";
                	break;	
                case 1 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI27";
                	break;		
                case 2 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI28";
                	break;	
                case 3 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI29";
                	break;		
                case 4 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "GUI30";
                	break;			
                default :
                	  System.out.println("%%%%% Invalid Problem INDEX");  	
        	}
        break;        
        //#####################
        
        case("LOF1-4"):
        	switch(index) {
        
                case 0 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "LOF1";
                	break;	
                case 1 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "LOF2";
                	break;		
                case 2 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "LOF3";
                	break;	
                case 3 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "LOF4";
                	break;		
                		
                default :
                	  System.out.println("%%%%% Invalid Problem INDEX");  	
        	}
        break;        
        //#####################
        
        case("WUP1-3"):
        	switch(index) {
        
                case 0 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "WUP1";
                	break;	
                case 1 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "WUP2";
                	break;		
                case 2 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "WUP3";
                	break;	
                                		
                default :
                	  System.out.println("%%%%% Invalid Problem INDEX");  	
        	}
        break;        
        //#####################
        
        case("YOU1"):
        	switch(index) {
        
                case 0 : // {f2, f3, f4, f5}
                	this.l = 10; // Decision/DISTANCE variables. Original = 20
                	this.m = 4; // Number of objectives. Original = 2
                	this.problemString = "YOU1";
                	break;	
                               		
                default :
                	  System.out.println("%%%%% Invalid Problem INDEX");  	
        	}
        break;        
        //#####################
        
        
        default :
      	  l = m = 0;
      	  problemString = "NONE";
      	  
           System.out.println("%%%%% Invalid Problem PROBLEM CLASS");
       }
	
	  this.k = 2* (m - 1);
	 
    } 
		
	
		
	
	public int getL() {
		return this.l;
	}
	
	public int getM() {
		return this.m;
	}
	
	public int getK() {
		return this.k;
	}
	
	public String getProblemString() {
		return this.problemString;
	}
	
	
	
	
}