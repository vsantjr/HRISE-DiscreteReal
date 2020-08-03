package jmetal.qualityIndicator;

import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

public class HyperVolumeMinimizationProblem<S extends Solution<?>> {

    jmetal.qualityIndicator.util.MetricsUtilPlus utils_;
    private List<S> selectedSols = null;

    /* Constructor */
    public HyperVolumeMinimizationProblem() {
        utils_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();
    }

    /*
	 * return true if 'point 1' dominates 'point 2' with respect to the
	 * first'nObjectives' objectives Note: it is for minimization problem so the
	 * domination condition is all the first 'nObjectives' of point1 <= the
	 * corresponding objective values of point2
     */
    public boolean dominates(double[] point1, double[] point2, int nObjectives) {
        int i;
        int betterInAnyObjective = 0;
        boolean point1DominatesPoint2 = false;

        for (i = 0; i < nObjectives && point1[i] <= point2[i]; i++) {// Nice
            // trick
            // here!!
            if (point1[i] < point2[i]) {
                betterInAnyObjective = 1;
            }
        }
        point1DominatesPoint2 = (i >= nObjectives)
                && (betterInAnyObjective > 0);
        return point1DominatesPoint2;
    }// dominates

    /* swap the i-th values of the front with the j-th values of this front */
    public void swap(double[][] front, int i, int j) {
        double[] temp;

        temp = front[i];
        front[i] = front[j];
        front[j] = temp;
    }

    /*
	 * find all non-dominated points regarding the first 'nObjectives'
	 * dimentions; all the points on the front are sorted by swapping dominated
	 * solutions to the end of the front. So that 'front[0, 1...n-1] contains
	 * the nondominated points regarding the first 'nObjectives'
     */
    public int filterNondomianteSet(double[][] front, int nPoints,
            int nObjectives) {
        int i, j;
        int n;

        n = nPoints;
        i = 0;
        while (i < n) {
            j = i + 1;
            while (j < n) {
                if (dominates(front[i], front[j], nObjectives)) {
                    /*
					 * if point i dominates point j, remove point j by swapping
					 * it to the end of the front
                     */
                    n--;
                    swap(front, j, n);
                } else if (dominates(front[j], front[i], nObjectives)) {
                    /*
					 * if point j dominates point i, remove point i by swapping
					 * it to the end of the front; ensure the new point i is
					 * considered in the next outer loop(thus, decrement i first
					 * cuz i will increase in the outer loop)
                     */
                    n--;
                    swap(front, i, n);
                    i--;
                    break;
                } else {
                    j++;// point i and point j are Pareto Optimal
                }
            }
            i++;
        }
        return n;
    }// filterNondomianteSet

    public double surfaceUnchagedTo(double[][] front, int nPoints, int Objective) {
        int i;
        double maxValue, value;

        if (nPoints < 1) {
            System.out
                    .println("Hv-surfaceUnchagedTo-poins number < 1-run-time error");
            /*
			 * Scanner in = new Scanner(System.in); String temp = in.nextLine();
             */
        }

        maxValue = front[0][Objective];
        for (i = 1; i < nPoints; i++) {
            value = front[i][Objective];
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }// surfaceUnchagedTo

    /*
	 * remove all points whose objective regarding the dimension 'objective'
	 * values >= threshold on the front, the first nPoints are considered, not
	 * all points on this front; front is then resorted by swapping the point
	 * which violates the threshold to the end of the considered nPoints of this
	 * front
     */
    public int reduceNondominatedSet(double[][] front, int nPoints,
            int Objective, double threshold) {
        int n, i;

        n = nPoints;
        for (i = 0; i < n; i++) {
            if (front[i][Objective] >= threshold) {
                n--;
                swap(front, i, n);
            }
        }
        return n;
    }

    public int reduceNondominatedSetNoReferencePoint(double[][] front, int nPoints,
            int Objective, double threshold) {
        int n, i;

        n = nPoints;
        for (i = 0; i < n; i++) {
            if (front[i][Objective] <= threshold) {
                n--;
                swap(front, i, n);
            }
        }
        return n;
    }

    public double surfaceUnchagedToNoReferencePoint(double[][] front, int nPoints, int Objective) {
        int i;
        double minValue, value;

        if (nPoints < 1) {
            System.out
                    .println("Hv-surfaceUnchagedTo-poins number < 1-run-time error");
            /*
			 * Scanner in = new Scanner(System.in); String temp = in.nextLine();
             */
        }

        minValue = front[0][Objective];
        for (i = 1; i < nPoints; i++) {
            value = front[i][Objective];
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }

    public double calculatehypervolumeNoReferencePoint(double[][] front, int noPoints,
            int noObjectives) {
        int n;
        double volume, distance;

        volume = 0;
        distance = 0;
        n = noPoints;
        while (n > 0) {
            int noNondominatedPoints;
            double tempVolume, tempDistance;

            noNondominatedPoints = filterNondomianteSet(front, n, noObjectives - 1);
            tempVolume = 0;
            if (noObjectives < 3) {
                if (noNondominatedPoints < 1) {
                    System.err.println("run-time error");
                }
                tempVolume = front[0][0];
            } else {
                tempVolume = calculatehypervolumeNoReferencePoint(front, noNondominatedPoints,
                        noObjectives - 1);
            }
            tempDistance = surfaceUnchagedToNoReferencePoint(front, n, noObjectives - 1);
            volume += tempVolume * (tempDistance - distance);
            distance = tempDistance;
            n = reduceNondominatedSetNoReferencePoint(front, n, noObjectives - 1, distance);
        }
        return volume;
    }

    public double calculatehypervolume(double[][] front, int nPoints,
            int nObjectives, double[] referencePoint) {

        int n;
        double volume, distance, previousSurfaceunchangedValue;

        volume = 0;
        distance = 0;
        n = nPoints;
        previousSurfaceunchangedValue = referencePoint[nObjectives - 1];

        while (n > 0) {
            int nNondominatedPoints;
            double tempVolume, tempDistance, tempSurfaceunchangedValue;

            nNondominatedPoints = filterNondomianteSet(front, n,
                    nObjectives - 1);
            tempVolume = 0;
            if (nObjectives < 3) {
                if (nNondominatedPoints < 1) {
                    System.out
                            .println("Hv-calculatehypervolume-nNodominatedPoints < 1");

                } else {
                    tempVolume = referencePoint[0] - front[0][0];

                    if (tempVolume < 0) {
                        // In this case, the reference Point wasn't chosen
                        // properly
                        tempVolume = 0;
                        /*						System.out
								.println("Hv-calculatehypervolume-point outside reference point-tempVolume < 0");
						System.out.println("referencePoint[0]: "
								+ referencePoint[0] + "; front[0][0] "
								+ front[0][0]);
                         */
 /*
						 * Scanner in = new Scanner(System.in); String temp =
						 * in.nextLine();
                         */
                    }

                }
            } else {
                tempVolume = calculatehypervolume(front, nNondominatedPoints,
                        nObjectives - 1, referencePoint);
            }

            tempSurfaceunchangedValue = surfaceUnchagedTo(front, n,
                    nObjectives - 1);

            tempDistance = previousSurfaceunchangedValue
                    - tempSurfaceunchangedValue;
            previousSurfaceunchangedValue = tempSurfaceunchangedValue;

            if (tempDistance < 0) {
                tempDistance = 0;
                previousSurfaceunchangedValue = referencePoint[nObjectives - 1];
                /*				System.out
						.println("Hv-calculatehypervolume-point outside reference point-tempDistance < 0 ");
				System.out.println("previousSurfaceunchangedValue: "
						+ previousSurfaceunchangedValue
						+ "; tempSurfaceunchangedValue"
						+ tempSurfaceunchangedValue);*/
 /*
				 * Scanner in = new Scanner(System.in); String temp =
				 * in.nextLine();
                 */
            }

            volume += tempVolume * tempDistance;

            distance = tempSurfaceunchangedValue;

            n = reduceNondominatedSet(front, n, nObjectives - 1, distance);
        }
        return volume;
    }// calculatehypervolume

    public double hypervolume(double[][] solutionFront, int numberOfObjectives,
            double[] referencePoint) {
        // setp 1. remove the duplicate points in a front/population
        double[][] newFront = utils_
                .removeDuplicatePointsonFront(solutionFront);

        double hypervolume = 0;

        hypervolume = this.calculatehypervolume(newFront, newFront.length,
                numberOfObjectives, referencePoint);
        return hypervolume;
    }// hypervolume

    //test calculating hypervolume without reference point.
    public double hypervolumeWithoutReference(double[][] solutionFront, int numberOfObjectives) {
        double[][] newFront = utils_
                .removeDuplicatePointsonFront(solutionFront);

        double hypervolume = 0;

        hypervolume = this.calculatehypervolumeNoReferencePoint(newFront, newFront.length,
                numberOfObjectives);
        return hypervolume;
    }

    public double hypervolumeAfterScaled(double[][] solutionFront) {
        // double [][] newFront =
        // utils_.removeDuplicatePointsonFront(solutionFront);
        double hypervolume = 0;
        int numberOfObjectives = solutionFront[0].length;
        double[][] normalizedFront = new double[solutionFront.length][numberOfObjectives];

        double[] maximumValues = new double[numberOfObjectives];
        double[] minimumValues = new double[numberOfObjectives];

        maximumValues = utils_.getMaximumValues(solutionFront,
                numberOfObjectives);
        minimumValues = utils_.getMinimumValues(solutionFront,
                numberOfObjectives);

        normalizedFront = utils_.getNormalizedFront(solutionFront,
                maximumValues, minimumValues);

        double[] referencePoint = new double[numberOfObjectives];
        for (int i = 0; i < numberOfObjectives; i++) {
            referencePoint[i] = 1;
        }
        hypervolume = this.hypervolume(normalizedFront, numberOfObjectives,
                referencePoint);
        return hypervolume;
    }

    public double hypervolumeForSolutionSet(List<S> population,
            double[] referencePoint) {
        // step1 get nondominated solution set
        List<S> front = SolutionListUtils.getNondominatedSolutions(population);

        // Step2. write solutions in a front/population to an array.
        double[][] frontMatrix = SolutionListUtils.writeObjectivesToMatrix(front);

        // step 3. remove the duplicate points in a front/population
        double[][] newFront = utils_.removeDuplicatePointsonFront(frontMatrix);

        // step4. calculate the hypervolume
        double hyperVolume = 0;
        int numberOfObjectives = newFront[0].length;

        hyperVolume = calculatehypervolume(newFront, newFront.length,
                numberOfObjectives, referencePoint);
        return hyperVolume;
    }

    public double hypervolumeForSolutionSetAfterScaled(List<S> population) {
        // step1 get nondominated solution set
        List<S> front = SolutionListUtils.getNondominatedSolutions(population);

        // Step2. write solutions in a front/population to an array.
        double[][] frontMatrix = SolutionListUtils.writeObjectivesToMatrix(front);

        int numberOfObjectives = frontMatrix[0].length;
        double[][] normalizedFront = new double[frontMatrix.length][numberOfObjectives];

        double[] maximumValues = new double[numberOfObjectives];
        double[] minimumValues = new double[numberOfObjectives];

        maximumValues = utils_
                .getMaximumValues(frontMatrix, numberOfObjectives);
        minimumValues = utils_
                .getMinimumValues(frontMatrix, numberOfObjectives);

        normalizedFront = utils_.getNormalizedFront(frontMatrix, maximumValues,
                minimumValues);

        double[] referencePoint = new double[numberOfObjectives];
        for (int i = 0; i < numberOfObjectives; i++) {
            referencePoint[i] = 1;
        }
        double hypervolume = this.hypervolume(normalizedFront,
                numberOfObjectives, referencePoint);
        return hypervolume;
    }

    public static double mean(double[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }

    // the array double[] m MUST BE SORTED
    public double median(double[] m) {
        Arrays.sort(m);
        int middle = m.length / 2;
        if (m.length % 2 == 1) {
            return m[middle];
        } else {
            return (m[middle - 1] + m[middle]) / 2.0;
        }
    }

    // sample variance
    double getVariance(double[] m) {
        double mean = mean(m);
        double temp = 0;
        for (int i = 0; i < m.length; i++) {
            temp += (mean - m[i]) * (mean - m[i]);
        }
        return temp / (m.length - 1);
    }

    // sample standard deviation
    double getStdDev(double[] m) {
        return Math.sqrt(getVariance(m));
    }

    /**
     * calculates the hypervolume of that portion of the objective space that is
     * dominated by individual a but not by individual b
     */
    /**
     * @param p_ind_a
     * @param p_ind_b
     * @param d
     * @param maximumValues
     * @param minimumValues
     * @return
     */
    double calcHypervolumeIndicatorDifference(double[] p_ind_a, double[] p_ind_b,
            int d, double maximumValues[], double minimumValues[]) {
        double a, b, r, max;
        double volume = 0;
        double rho = 2.0;

        //r = rho * (maximumValues[d - 1] - minimumValues[d - 1]);
        r = 1.0;
        //max = minimumValues[d - 1] + r;
        max = maximumValues[d - 1];

        // need to be removed
        /*
		 * r = 1.0; max = 3.0;
         */
        //a = p_ind_a.getObjective(d - 1);
        a = p_ind_a[d - 1];
        if (p_ind_b == null) {
            b = max;
        } else //b = p_ind_b.getObjective(d - 1);
        {
            b = p_ind_b[d - 1];
        }

        if (d == 1) {
            if (a < b) {
                volume = (b - a) / r;
            } else {
                volume = 0;
            }
        } else {
            if (a < b) {
                volume = calcHypervolumeIndicatorDifference(p_ind_a, null, d - 1,
                        maximumValues, minimumValues) * (b - a) / r;
                volume += calcHypervolumeIndicatorDifference(p_ind_a, p_ind_b, d - 1,
                        maximumValues, minimumValues) * (max - b) / r;
            } else {
                volume = calcHypervolumeIndicatorDifference(p_ind_a, p_ind_b, d - 1,
                        maximumValues, minimumValues) * (max - a/* b */) / r;
            }
        }

        return (volume);
    }

    
    //only deal with non-dominated solutions!!

    /*
    
    double[] crowdingDistance(double[][] front, Problem problem) {
        List<S> SolSet = new ArrayList<>(front.length);
        SolSet = vector2SolutionSet(front, problem);
        Distance distance = new Distance();
        distance.crowdingDistanceAssignment(SolSet, front[0].length);
        double[] crowdingDistance = new double[front.length];
        for (int i = 0; i < crowdingDistance.length; i++) {
            crowdingDistance[i] = SolSet.get(i).getCrowdingDistance();
        }
        selectedSols = new ArrayList<>((int) (front.length / 2));
        SolSet.sort(new CrowdingComparator());

        for (int i = 0; i < (int) (front.length / 2); i++) {
            selectedSols.add(SolSet.get(i));
        }
        return crowdingDistance;
    }

    double[] IBEAfitness(double[][] front, Problem problem) {
        List<S> SolSet = new ArrayList<>(front.length);
        SolSet = vector2SolutionSet(front, problem);

        IBEAFitness ibeaFitness = new IBEAFitness();
        ibeaFitness.calculateFitness(SolSet, front[0].length);
        double[] fitness = new double[front.length];
        for (int i = 0; i < fitness.length; i++) {
            fitness[i] = SolSet.get(i).getFitness();
        }
        selectedSols = new ArrayList<>((int) (front.length / 2));
        while (SolSet.size() > (int) (front.length / 2)) {
            ibeaFitness.removeWorst(SolSet);
        }
        selectedSols = SolSet;
        return fitness;
    }

    double[] SPEA2Fitness(double[][] front, Problem problem) {
        List<S> SolSet = new ArrayList<>();
        SolSet = vector2SolutionSet(front, problem);
        Spea2Fitness spea2Fitness = new Spea2Fitness(SolSet);

        double[] fitness = new double[front.length];
        double[] kthDistance = new double[front.length];
        spea2Fitness.fitnessAssign();
        for (int i = 0; i < fitness.length; i++) {
            fitness[i] = SolSet.get(i).getFitness();
            kthDistance[i] = SolSet.get(i).getkDistance_();
        }
        selectedSols = new ArrayList<>((int) (front.length / 2));
        selectedSols = spea2Fitness.environmentalSelection((int) (front.length / 2));
        return fitness;
    }

    public static void main(String[] args) throws IOException {

        MetricsUtil utils_ = new jmetal.qualityIndicator.util.MetricsUtil();
        HyperVolumeMinimizationProblem hv = new HyperVolumeMinimizationProblem();

        double[][] fronta = {{2, 2, 4}};
        double[][] frontb = {{1, 2, 4}};
        double[][] frontc = {{3, 4, 1}};
        double[][] frontd = {{7, 1, 1}};

        double[] referencePointac = {4, 5, 5};
        double[] referencePointcd = {8, 5, 2};
        double[] referencePointbd = {8, 3, 5};

        double contributionofainsetac = hv.calculatehypervolume(fronta, fronta.length, fronta[0].length, referencePointac);

        System.out.println("contributionofainsetac: " + contributionofainsetac);

        double contributionofcinsetac = hv.calculatehypervolume(frontc, frontc.length, frontc[0].length, referencePointac);

        System.out.println("contributionofcinsetac: " + contributionofcinsetac);

        double contributionofcinsetcd = hv.calculatehypervolume(frontc, frontc.length, frontc[0].length, referencePointcd);

        System.out.println("contributionofcinsetcd: " + contributionofcinsetcd);

        double contributionofdinsetcd = hv.calculatehypervolume(frontd, frontd.length, frontd[0].length, referencePointcd);

        System.out.println("contributionofdinsetcd: " + contributionofdinsetcd);

        double contributionofbinsetbd = hv.calculatehypervolume(frontb, frontb.length, frontb[0].length, referencePointbd);

        System.out.println("contributionofbinsetbd: " + contributionofbinsetbd);

        double contributionofdinsetbd = hv.calculatehypervolume(frontd, frontd.length, frontd[0].length, referencePointbd);

        System.out.println("contributionofdinsetbd: " + contributionofdinsetbd);

        System.out.println("Finish.");

        double[][] front1 = {{1, 12}, {3, 10}, {5, 8}, {7, 6}, {11, 4}};
        double[][] front2 = {{2, 13}, {4, 11}, {6, 9}, {8, 7}, {10, 5}, {11, 4}};
        double[] referencePoint = {20, 20};
        double[] minimValues = {0, 0};
        double[] normalisedReferencePoint = {1, 1};

        //double[][] front1 = {{0.5,2.5},{1,2},{2,1}};
	//	double[][] front2 = {{1,3},{2,2},{3,1}};
	//	double[] referencePoint = {3,3};
	//	double[] minimValues = {0,0};
	//	double[] normalisedReferencePoint = {1,1};
	    //maximumValues = utils_.getMaximumValues(paretoTrueFront,numberOfObjectives);
	  //double[][]  minimumValues = utils_.getMinimumValues(paretoTrueFront,numberOfObjectives);
         
        double front1hvwitRef = hv.calculatehypervolume(front1, front1.length, front1[0].length, referencePoint);
        double front2hvwithRef = hv.calculatehypervolume(front2, front2.length, front2[0].length, referencePoint);
        System.out.println("Original front with reference point::");
        System.out.println("front1 hypervolume without reference point: " + front1hvwitRef);
        System.out.println("front1 hypervolume without reference point: " + front2hvwithRef);

        double front1hvwithoutRef = hv.calculatehypervolumeNoReferencePoint(front1, front1.length, front1[0].length);
        double front2hvwithoutRef = hv.calculatehypervolumeNoReferencePoint(front2, front2.length, front2[0].length);
        System.out.println("Original front without reference point::");
        System.out.println("front1 hypervolume without reference point: " + front1hvwithoutRef);
        System.out.println("front1 hypervolume without reference point: " + front2hvwithoutRef);

        double[][] front1Norm = utils_.getNormalizedFront(front1, referencePoint, minimValues);
        double[][] front2Norm = utils_.getNormalizedFront(front2, referencePoint, minimValues);
        //The calculatehypervolume functioni in this file is already for minisation problem, so no need to revert.
        front1hvwitRef = hv.calculatehypervolume(front1Norm, front1.length, front1[0].length, normalisedReferencePoint);
        front2hvwithRef = hv.calculatehypervolume(front2Norm, front2.length, front2[0].length, normalisedReferencePoint);
        System.out.println("Normalised front with reference point:");
        System.out.println("front1 hypervolume without reference point: " + front1hvwitRef);
        System.out.println("front1 hypervolume without reference point: " + front2hvwithRef);

        double[][] front1NormRevert = utils_.invertedFront(front1Norm);
        double[][] front2NormRevert = utils_.invertedFront(front2Norm);
        front1hvwithoutRef = hv.calculatehypervolumeNoReferencePoint(front1NormRevert, front1.length, front1[0].length);
        front2hvwithoutRef = hv.calculatehypervolumeNoReferencePoint(front2NormRevert, front2.length, front2[0].length);
        System.out.println("Normalised front without reference point:");
        System.out.println("front1 hypervolume without reference point: " + front1hvwithoutRef);
        System.out.println("front1 hypervolume without reference point: " + front2hvwithoutRef);

        /*
		// TODO Auto-generated method stub
		HyperVolumeMinimizationProblem qualityIndicator = new HyperVolumeMinimizationProblem();

		String datafile = "DataTest/2DHypervolumeTest.txt";
		double[][] front = qualityIndicator.utils_.readFront(datafile);
		ArrayList<double[]> frontList = new ArrayList<double[]>(front.length);

		for (int i = 0; i < front.length; i++) {
			frontList.add(front[i]);
		}
		double[] reference = { 3.0, 3.0 };
		double[] minimum = {0.0,0.0};
		
		double fullHypervolume = qualityIndicator.hypervolume(front,
				reference.length, reference);
		double[] loss = new double[front.length];
		double[] hypervolume = new double[front.length];
		double[] hypervolumefitness = new double[front.length];
		double[] SPEA2fitness = new double[front.length];
		double[] crowdingDistance = new double[front.length];
		double[] IBEAfitness = new double[front.length];
		SolutionSet IBEASelected = new SolutionSet((int)(front.length/2));
		SolutionSet SPEA2Selected = new SolutionSet((int)(front.length/2));
		SolutionSet NSGAIISelected = new SolutionSet((int)(front.length/2));
		
		//IBEAfitness the smaller the better;
		IBEAfitness = qualityIndicator.IBEAfitness(front);
		IBEASelected = qualityIndicator.selectedSols;
		//SPEA2fitness the smaller the better 
		SPEA2fitness = qualityIndicator.SPEA2Fitness(front);
		SPEA2Selected = qualityIndicator.selectedSols;
		//NSGAII crowdingDistance the higher the better.
		crowdingDistance = qualityIndicator.crowdingDistance(front);
		NSGAIISelected = qualityIndicator.selectedSols;		
		
		double[][] IBEASelectedDbl = IBEASelected.writeObjectivesToMatrix();
		double[][] SPEA2SelectedDbl = SPEA2Selected.writeObjectivesToMatrix();
		double[][] NSGAIISelectedDbl = NSGAIISelected.writeObjectivesToMatrix();
		
		for(int i = 0; i < front.length; i++){
			for(int j = 0; j < front.length; j++){
				hypervolumefitness[i] += qualityIndicator.calcHypervolumeIndicatorDifference(front[i], front[j],reference.length,reference,minimum);
			}
			
			
		}
		String fitnessOutput = "DataTest/2DHypervolumeFitness.txt";
		qualityIndicator.utils_.printArray(hypervolumefitness, fitnessOutput);
		
		for (int i = 0; i < front.length; i++) {
			
			
			ArrayList<double[]> tempArrayList = (ArrayList<double[]>) frontList
					.clone();
			tempArrayList.remove(i);
			double[][] tempFront = new double[front.length - 1][front[0].length];

			for (int j = 0; j < tempFront.length; j++) {

				tempFront[j] = tempArrayList.get(j);

			}

			hypervolume[i] = qualityIndicator.hypervolume(tempFront,
					reference.length, reference);
			loss[i] = fullHypervolume - hypervolume[i];

			System.out.println("Point: " + front[i][0] + " " + front[i][1]
					+ " hypervolume: " + hypervolume[i] + "; loss " + loss[i] + "; simplifiedIBEA fitness: "+hypervolumefitness[i]);

		}
		
		//append the file with the parameter 'true'
		FileWriter fw = new FileWriter("DataTest/2DHypervolumeFitness.csv", true);
		//fw.flush();
		
		StringBuilder sb = new StringBuilder();
		
		for(int k = 0; k < front[0].length; k++){
			sb.append("Obj "+ k);
			sb.append(',');
		}
		sb.append("Remaining hypervolume");
		sb.append(',');
		sb.append("Hypervolume loss");
		sb.append(',');
		sb.append("SimpleIBEAFitness");
		sb.append(',');
		sb.append("IBEAFitness");
		sb.append(',');
		sb.append("SPEA2Fitness");
		sb.append(',');
		sb.append("CrowdingDistance");
		sb.append('\n');
		
		for(int i = 0; i < front.length; i++){
			for(int k = 0; k < front[0].length; k++){
				sb.append(front[i][k]);
				sb.append(',');
			}
			sb.append(Double.toString(hypervolume[i]));
			sb.append(',');
			sb.append(Double.toString(loss[i]));
			sb.append(',');
			sb.append(Double.toString(hypervolumefitness[i]));
			sb.append(',');
			sb.append(Double.toString(IBEAfitness[i]));
			sb.append(',');
			sb.append(Double.toString(SPEA2fitness[i]));
			sb.append(',');
			sb.append(Double.toString(crowdingDistance[i]));
			sb.append('\n');	
			
		}
		sb.append("IBEA Selected Points");
		sb.append('\n');
		for(int i = 0; i < IBEASelectedDbl.length; i++){
			for(int k = 0; k < IBEASelectedDbl[0].length; k++){
				sb.append(Double.toString(IBEASelectedDbl[i][k]));
				sb.append(',');
				
			}
			sb.append('\n');
		}
			sb.append('\n');
			sb.append("SPEA2 Selected Points");
			sb.append('\n');
			for(int i1 = 0; i1 < SPEA2SelectedDbl.length; i1++){
				for(int k = 0; k < SPEA2SelectedDbl[0].length; k++){
					sb.append(Double.toString(SPEA2SelectedDbl[i1][k]));
					sb.append(',');
					
				}
				sb.append('\n');				
			
		}
			
			sb.append('\n');
			sb.append("NSGAII Selected Points");
			sb.append('\n');
			for(int i1 = 0; i1 < NSGAIISelectedDbl.length; i1++){
				for(int k = 0; k < NSGAIISelectedDbl[0].length; k++){
					sb.append(Double.toString(NSGAIISelectedDbl[i1][k]));
					sb.append(',');
					
				}
				sb.append('\n');				
			
		}
			
		
		sb.append('\n');	
        fw.write(sb.toString());
        fw.close();
	

		
		 * String datafile =
		 * "HF_Config_Benchmark/Results/IBEA/run_1/ParetoFront_WFG2_PF.txt";
		 * String datafile1 = "HF_Config_Benchmark/WFG.3D/WFG2.3D.pf";
		 * double[][] front = qualityIndicator.utils_.readFront(datafile);-
		 * double[][] front1 = qualityIndicator.utils_.readFront(datafile1);
		 * double[] reference = {3,5,7}; double result =
		 * qualityIndicator.hypervolume(front, reference.length, reference);
		 * double result1 = qualityIndicator.hypervolume(front1,
		 * reference.length, reference); System.out.println(result);
		 * System.out.println(result1);
		 
		// String file = "DataTest/InputData.txt";
		// String file = "DataTest/InputData1.txt";
		// int numberOfFiles = 21;

		
		 * double[][] front = {{0.3,0.5}, {0.5,0.4}, {0.7,0.3}}; double[] refer
		 * = {1,1}; double volume = 0; volume =
		 * qualityIndicator.calculatehypervolume(front, 3, 2, refer);
		 * System.out.println("Volume: " + volume);
		 
		
		 * int numberOfFiles = 40;
		 * 
		 * //double[] referencePoint = {0.5,57,0.25}; //double[] referencePoint
		 * = {8,13,1.7};//20turbines double[] referencePoint =
		 * {9,9.5,1.7};//30turbines //double[] referencePoint =
		 * {9.0,7.0,2.5};//50turbines //double[] referencePoint =
		 * {9.0,7.0,3.2};//70turbines int numberOfObjectives =
		 * referencePoint.length; int iterNO = 10; double hypervolume[][] = new
		 * double[iterNO][numberOfFiles]; // double hypervolume[] = new
		 * double[numberOfFiles]; String algorithm = "null"; String turbineConf
		 * = "2_70_50";
		 * 
		 * 
		 * for(int i = 0; i < iterNO; i++){
		 * 
		 * if(Math.floorMod(i, 2) == 0){ algorithm = "NSGAII"; }else
		 * if(Math.floorMod(i,2) == 1){ algorithm = "IBEA"; }else
		 * if(Math.floorMod(i, 3) == 2){ algorithm = "IBEA"; } for(int j = 0; j<
		 * numberOfFiles; j++){ String file
		 * ="HF_Config_Power_Area_EMST/run_0"+"/"
		 * +algorithm+"_Iter_"+i+"_Evl"+(50+50*j)+".pf"; double [][]
		 * solutionFront = qualityIndicator.utils_.readFront(file);
		 * hypervolume[i][j] = qualityIndicator.hypervolume(solutionFront,
		 * numberOfObjectives, referencePoint); } try { BufferedWriter bw = new
		 * BufferedWriter(new
		 * FileWriter("HF_Config_Power_Area_EMST/GenerationalHypervolume.txt"));
		 * for(int m = 0; m < hypervolume.length; m++){ for(int n = 0; n <
		 * hypervolume[m].length; n++){ bw.append(new
		 * Double(hypervolume[m][n]).toString()); bw.newLine(); } }bw.close();
		 * 
		 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } }
		 
		
		 * for(int i = 0; i < numberOfFiles; i++){ //String file =
		 * "DataTest/HyperVolumeCompute/HH/run_"
		 * +i+"/HF_ObtainedParetoFront_Final_"+i+".txt";
		 * 
		 * //String file =
		 * "DataTest/HyperVolumeCompute/HH/"+"SPEA2_Iter_9_Evl"+(
		 * 100+50*i)+".pf"; //String file =
		 * "HF_Config_Power_Area_EMST/run_"+i+"/HF_ObtainedParetoFront_Final_"
		 * +i+".txt"; //String file = "DataTest/IBEA_2_30_50_PRI_2000new.pf";
		 * String file =
		 * "HF_Config_Power_Area_EMST/run_"+i+"/HF_ObtainedParetoFront_Final_"
		 * +i+".txt"; //String file =
		 * "DataTest/HyperVolumeCompute/HH/run_"+i+"/HF_ObtainedParetoFront_Final_"
		 * +i+".txt"; //String file =
		 * "DataTest/HyperVolumeCompute/"+algorithm+"/"
		 * +algorithm+"_"+turbineConf+"/run_"+ i +"/"+
		 * algorithm+"_"+turbineConf+"_Run_"+i+"_final_Pareto_front.fun"; double
		 * [][] solutionFront = qualityIndicator.utils_.readFront(file);
		 * hypervolume[i] = qualityIndicator.hypervolume(solutionFront,
		 * numberOfObjectives, referencePoint);
		 * System.out.println(hypervolume[i]); hypervolume[i] =
		 * qualityIndicator.hypervolumeAfterScaled(solutionFront); }
		 * 
		 * 
		 * try {
		 * 
		 * //BufferedWriter bw = new BufferedWriter(new
		 * FileWriter("DataTest/HyperVolumeCompute/"
		 * +algorithm+"/Hypervolumm_"+algorithm+"_"+turbineConf+".txt"));
		 * //BufferedWriter bw = new BufferedWriter(new
		 * FileWriter("DataTest/HyperVolumeCompute/HH/HyperVolume.txt"));
		 * BufferedWriter bw = new BufferedWriter(new
		 * FileWriter("HF_Config_Power_Area_EMST/HyperVolume.txt")); // MST
		 * lower bound // TODO print to file for(int i = 0; i < numberOfFiles;
		 * i++){ bw.append(new Double(hypervolume[i]).toString()); bw.newLine();
		 * } bw.close();
		 * 
		 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } System.out.println("Median: " +
		 * qualityIndicator.median(hypervolume) + " Mean: " +
		 * qualityIndicator.mean(hypervolume) + " STD: " +
		 * qualityIndicator.getStdDev(hypervolume));
		 
		// System.out.println(hypervolume);
         
    }
    */
}
