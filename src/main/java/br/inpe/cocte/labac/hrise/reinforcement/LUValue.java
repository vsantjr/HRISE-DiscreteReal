package br.inpe.cocte.labac.hrise.reinforcement;

public class LUValue {

	private String llh;
	private double utility;
	
	public LUValue(){
		
	}
	
	public void setLLH(String value) {
		this.llh = value;
	}
	
	public String getLLH() {
		return this.llh;
	}
	
	public void setQuality(double value) {
		this.utility = value;
	}
	
	public double getQuality() {
		return this.utility;
	}
	
	
}
