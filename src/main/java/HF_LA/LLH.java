package HF_LA;

import java.util.ArrayList;
import java.util.Iterator;

public class LLH {
	
	private ArrayList<Double> transPro_ = new ArrayList<Double>();
	Iterator<Double> TransProIter = getTransPro_().iterator();
	int index_ = -1;
	private ArrayList<Double> Qvalue = new ArrayList<Double>();
	private ArrayList<Double> transTimes = new ArrayList<Double>();
	private ArrayList<Double> transferReward = new ArrayList<Double>();
	private int executedNo;
	private int successTimes;
	private int calledTimes;
	private double successProb;
	private double reward;
	private double utility;
	
	private AbstractLearningAutomata LAUpdatingModel_ = null;
	private AbstractSelectionLLHs selectionModel_=null;
	
	public LLH(int numberOfLLH, AbstractLearningAutomata wl, AbstractSelectionLLHs sl, int heuristicIndex){
		index_ = heuristicIndex;
		
		for(int i = 0; i < numberOfLLH; i++){
			getTransPro_().add(0.0);
		}
		this.intialiseTransPro(numberOfLLH);
		LAUpdatingModel_ = wl;	
		selectionModel_ = sl;
		for(int j = 0; j < numberOfLLH; j++){
			this.getQvalue().add(0.0);
			getTransTimes().add(0.0);
			getTransferReward().add(0.0);
		}		
		setExecutedNo(0);
		setReward(0.0);
		setUtility(0.0);
		setSuccessTimes(0);
		setCalledTimes(0);
		setSuccessProb(1.0);
	}
	
	//initialise this heuristic's transition probabilities to all the rest heuristics.
	public void intialiseTransPro(int numberOfLLH){
		double initialPro = 1.0/(numberOfLLH);
		for(int i = 0; i < numberOfLLH; i++){
/*			if(i != this.index_){
				this.transPro_.set(i, initialPro);
			}*/
			this.getTransPro_().set(i, initialPro);	
			
		}
	}
	
	public void intialiseSuccessTimes(){
		this.setSuccessTimes(0);
		this.setCalledTimes(0);
		this.setSuccessProb(1.0);
	}
	
	public void intialiseExecutedNo(){
		this.setExecutedNo(0);
	}
	public void intialistransTimes(int numberOfLLH){
		for(int i = 0; i < numberOfLLH; i++){
			this.getTransTimes().set(i, 0.0);
		}
	}
	
	public void intialisetransferReward(int numberOfLLH){
		for(int i = 0; i < numberOfLLH; i++){
			this.getTransferReward().set(i, 0.0);
		}
	}
	
	public void intialiseReward(){
		this.setReward(0.0);
	}
	
	public void initialiseUtility(){
		this.setUtility(0.0);
	}
	
	public void intialiseQvalue(int numberOfLLH){
		for(int j = 0; j < numberOfLLH; j++){
			this.getQvalue().set(j, 0.0);
		}	
	}
	
	/*updating this heuristic's transition probabilities to all the heuristics, based on the reward of one of the successors of this heuristic executed
	 *  in the last step.
	*/
	public void updatingLLHTransPro(int rewardSignal, int chosenHeuristic){
		this.LAUpdatingModel_.updateProbabilityPositiveSignal(chosenHeuristic, getTransPro_());
	}
	
	//selection of the next heruistic
	public int selectionNextLLH(){
		int nextLLH = this.selectionModel_.selectionLLHs(getTransPro_());
		return nextLLH;
	}
	
	//get double array of this heuristic's transition probabilities to all the heuristics.
	public double[] getAllTransProDbl(){
		int size = this.getTransPro_().size();
		double[] transProDul = new double[size];
		int index = 0;
		
		while(TransProIter.hasNext()){
			transProDul[index] = this.getTransPro_().get(index);
			index++;
		}
		return transProDul;
		
	}

	public ArrayList<Double> getTransPro_() {
		return transPro_;
	}

	public void setTransPro_(ArrayList<Double> transPro_) {
		this.transPro_ = transPro_;
	}

	public ArrayList<Double> getTransTimes() {
		return transTimes;
	}

	public void setTransTimes(ArrayList<Double> transTimes) {
		this.transTimes = transTimes;
	}

	public int getCalledTimes() {
		return calledTimes;
	}

	public void setCalledTimes(int calledTimes) {
		this.calledTimes = calledTimes;
	}

	public ArrayList<Double> getTransferReward() {
		return transferReward;
	}

	public void setTransferReward(ArrayList<Double> transferReward) {
		this.transferReward = transferReward;
	}

	public int getExecutedNo() {
		return executedNo;
	}

	public void setExecutedNo(int executedNo) {
		this.executedNo = executedNo;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

	public ArrayList<Double> getQvalue() {
		return Qvalue;
	}

	public void setQvalue(ArrayList<Double> qvalue) {
		Qvalue = qvalue;
	}

	public double getUtility() {
		return utility;
	}

	public void setUtility(double utility) {
		this.utility = utility;
	}

	public int getSuccessTimes() {
		return successTimes;
	}

	public void setSuccessTimes(int successTimes) {
		this.successTimes = successTimes;
	}

	public double getSuccessProb() {
		return successProb;
	}

	public void setSuccessProb(double successProb) {
		this.successProb = successProb;
	}
}
