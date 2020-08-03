package HF_utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Heuristic {
	
	private ArrayList<Integer> nextLLH = new ArrayList<Integer>(); 
	private ArrayList<Integer> popNO = new ArrayList<Integer>();
	private ArrayList<Integer> AS = new ArrayList<Integer>();
	Iterator nextLLhIterator = nextLLH.iterator();
	Iterator popNOIterator = popNO.iterator();
	Iterator ASIternator = AS.iterator();
	
	public Heuristic(){
		super();
	}
	public Heuristic(int numberOfLLH, int populationNumber, int populationInterval, int ASnumber){
		this.intialiseNextLLH(numberOfLLH);
		this.intialisePopNO(populationNumber, populationInterval);
		this.intialiseAS(ASnumber);	
	}


	public ArrayList<Integer> getNextLLH() {
		return nextLLH;
	}
	

	public int getElementInNextLLH(int index){
		int temp = this.nextLLH.get(index);
		return temp;
	}


	public void intialiseNextLLH(int numberOfLLH) {
		for(int i = 0; i < numberOfLLH; i++){
			this.nextLLH.add(i);
		}
	}
	
	public void addNextLLH(int index){
		this.nextLLH.add(index);
	}
	
	public void removeLLH(int index){
		this.nextLLH.remove(index);
	}


	public ArrayList<Integer> getPopNO() {
		return popNO;
	}

	public int getElementPopNO(int index){
		int temp = this.popNO.get(index);
		return temp;
	}

	public void intialisePopNO(int maximumPopulation, int interval) {
		for(int i = interval; i <= maximumPopulation; i=i+interval){
			this.popNO.add(i);
		}
	
	}
	
	public void addPopNO(int populationNumber){
		this.popNO.add(populationNumber);
	}


	public ArrayList<Integer> getAS() {
		return AS;
	}
	
	public int getElementAS(int index){
		int temp = this.AS.get(index);
		return temp;
	}


	public void intialiseAS(int ASnumber) {
		for(int i = 0; i < ASnumber; i++){
			this.AS.add(i);
		}
	}
	
	public void addAS(int element){
		this.AS.add(element);
	}
		
}
