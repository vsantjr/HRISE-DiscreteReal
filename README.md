# HRISE-DiscreteReal

This is the **Hyper-Heuristics based on Reinforcement Learning, Balanced Heuristic Selection and Group Decision Acceptance** set of selection hyper-heuristics: HRISE\_M, HRISE\_R and HRMA [1]. These three algorithms are also regarded as the HRISE family. Moreover, six other optimisation algorithms are also embedded within this software: Choice Function hyper-heuristic (HH-CF) [2], a random choice (meta)heuristic selection hyper-heuristic with All Moves acceptance (HH-ALL), the Learning Automata-based Hyper-Heuristic with a Ranking Scheme Initialisation (HH-RILA) [3], and the metaheuristics Nondominated Sorting Genetic Algorithm-II (NSGAII), Indicator-Based Evolutionary Algorithm (IBEA), and Strength Pareto Evolutionary Algorithm-2 (SPEA2). More information see [1].

HRISE-DiscreteReal is useful for Discrete Optimisation and Real problems. So, any discrete and real problem/problem instance can be addressed by this software. 
 
Currently, this software supports two types of problems related to Graphical User Interface (GUI) Software Testing: code-driven testing and use case-driven testing. With respect to the code-driven testing, the generation of test cases for 24 problem instances (GUIs) of the [TerraAmazon](http://www.terraamazon.dpi.inpe.br/) software product is addressed. Regarding the use case-driven testing, there are 8 problem instances where 4 are use cases for the LibreOffice Writer, 3 are for WhatsApp, and 1 is for YouTube. 

## Prerequisites and Running

The best way to run this software is to download the entire code and create a Java project under Eclipse. 

In order to run the GUI Software Testing problem instances, it is required to add all *.jar* which are in the *lib* directory of the [jGraphT](https://jgrapht.org/), a Java library of graph theory, into the project. After that, follow the steps below: 

1. Go right to 'HRISERunnerDiscreteReal.java' in *src/main/java/br/inpe/cocte/labac/hrise/main/*.

2. Change the 'baseSrcDir' and 'baseDestDir' according to your platform/computer. The 'baseSrcDir' is in fact the base directory of the Eclipse project, and the 'baseDestDir' is the output directory where all the results are stored.

3. To run the GUI problem instances, note that the code-driven ones are identified as strings of the form: "gu1", "gu2", ..., "gu24". The use case-driven problem 
instances are identified as: "lofx" (LibreOffice), "wupx" (WhatsApp), and "youx" (YouTube). Moreover, they should be addressed in groups and they are case sensitive (strings). This is due to the implementation of HH-RILA. If HH-RILA is not considered, hence it is possible to run each problem instance individually. Uncomment the groups for a different sequence of problem instances. 

4. Run 'HRISERunnerDiscreteReal.java'.

**Important: This software runs under macOS and Linux. In order to run it in Windows, it is required to change the directory delimiter (\\) in Windows in order to properly handle the directories.**

## Underlying Frameworks and Libraries

HRISE-DiscreteReal was developed based on the following frameworks and libraries:

* [jMetal](https://github.com/jMetal/jMetal) - Version 5.6
* [jMetalHyperHeuristicHelper](https://github.com/vinixnan/jMetalHyperHeuristicHelper) - Version 1.0
* [jGraphT](https://jgrapht.org/) - Version 1.3.0

## Version

Current version is 1.0.

## Author

* **Valdivino Alexandre de Santiago J&uacute;nior** - [Personal Web Page](http://www.lac.inpe.br/~valdivino/)

## Licence

This project is licensed under the GNU GENERAL PUBLIC LICENSE, Version 3 (GPLv3) - see the [LICENSE.md](LICENSE) file for details.

## References

[1] V. A. Santiago J&uacute;nior, E. Ozcan, V. R. de Carvalho. Hyper-Heuristics based on Reinforcement Learning, Balanced Heuristic Selection and Group Decision Acceptance. APPLIED SOFT COMPUTING, p. 1-48, 2020. Submitted (Pre-print, Revision 1).

[2] M. Maashi, E. Ozcan, G. Kendall, A multi-objective hyper-heuristic based on choice function, Expert Systems with Applications 41 (9) (2014) 4475 - 4493. doi:https://doi.org/10.1016/j.eswa.2013.12.050. 

[3] W. Li, E. Ozcan, R. John, A learning automata-based multiobjective hyper-heuristic, IEEE Transactions on Evolutionary Computation 23 (1) (2019) 59 - 73. doi:10.1109/TEVC.2017.2785346.


