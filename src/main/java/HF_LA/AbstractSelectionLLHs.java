package HF_LA;

import java.util.ArrayList;

/**
 * @author wzl
 *
 */
public interface AbstractSelectionLLHs {
	/**
	 * @param the transition probability list of a heuristic (h_i)
	 * @return the index of the next heuristic that h_i will transit
	 */
	
	public int selectionLLHs(ArrayList<Double> heuristicTransPro);

	int selectionLLHs(ArrayList<Double> heuristicTransPro, double temperature);

}
