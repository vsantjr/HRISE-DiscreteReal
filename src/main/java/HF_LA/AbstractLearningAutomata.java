package HF_LA;

import java.util.ArrayList;

public interface  AbstractLearningAutomata{
	
	public void updateProbabilityPositiveSignal(Object chosenHeuristic, ArrayList<Double> transPro);
	public void updateProbabilityNegativeSignal(Object chosenHeuristic, double lamada2, int rejectTimes, int numberOfLLHs, ArrayList<Double> transPro);
	void updateProbabilityPositiveSignalQ(Object chosenHeuristic,
			ArrayList<Double> transPro, ArrayList<Double> Qvalues);

	void updateProbabilityNegativeSignalQ(Object chosenHeuristic,
			int numberOfLLHs, ArrayList<Double> transPro,
			ArrayList<Double> Qvalues);


}
