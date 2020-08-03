package br.inpe.cocte.labac.hrise.reinforcement;

public class PrevPerformance {



		private String llh;
		private int decisionPoint;
		
		public PrevPerformance(){
			
		}
		
       public PrevPerformance(String l, int d){
    	   this.llh = l;
    	   this.decisionPoint = d;
			
		}
		
		public void setLLH(String value) {
			this.llh = value;
		}
		
		public String getLLH() {
			return this.llh;
		}
		
		public void setDecisionPoint(int value) {
			this.decisionPoint = value;
		}
		
		public double getDecisionPoint() {
			return this.decisionPoint;
		}
		
		
	
	
}
