package br.inpe.cocte.labac.hrise.util;

public class LogRunningLLH {

	private String llh;
	private double quality;
	
	public LogRunningLLH(){
		
	}
	
	public void setRunningLLH(String value) {
		this.llh = value;
	}
	
	public String getRunningLLH() {
		return this.llh;
	}
	
	public void setRunningQuality(double value) {
		this.quality = value;
	}
	
	public double getRunningQuality() {
		return this.quality;
	}
	
	
}