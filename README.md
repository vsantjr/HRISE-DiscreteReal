# HRISE-DiscreteReal

This is the repositoty of the **Hyper-Heuristics based on Reinforcement Learning, Balanced Heuristic Selection and Group Decision Acceptance** set of selection hyper-heuristics: HRISE\_M, HRISE\_R and HRMA. These three algorithms are also regarded as the HRISE family. Moreover, six other optimisation algorithms are also embedded within this software: Choice Function hyper-heuristic (HH-CF), a random choice (meta)heuristic selection hyper-heuristic with All Moves acceptance (HH-ALL), the Learning Automata-based Hyper-Heuristic with a Ranking Scheme Initialisation (HH-RILA), and the metaheuristics Nondominated Sorting Genetic Algorithm-II (NSGAII), Indicator-Based Evolutionary Algorithm (IBEA), and Strength Pareto Evolutionary Algorithm-2 (SPEA2). More information see.

HRISE-DiscreteReal is useful for discrete optimisation and real problems. So, any discrete and real problem/problem instance can be addressed by this software. 

## Article

This repository is the supplemental material of the article below:

**Many-Objective Test Case Generation for Graphical User Interface Applications via Search-Based and Model-Based Testing**.
 
All relevant data obtained via the experiments are in the folder ```Experiment Data```. 

Currently, this software supports two types of problems related to graphical user interface (GUI) software testing: code-driven testing and use case-driven testing. With respect to the code-driven testing, the generation of test cases for 24 problem instances (GUIs) of the [TerraAmazon](http://www.terraamazon.dpi.inpe.br/) software product is addressed. Regarding the use case-driven testing, there are eight problem instances where four are use cases for the LibreOffice Writer, three are for WhatsApp, and one is for YouTube. 



## Prerequisites and Running

The best way to run this software is to download the entire code and create a Java project under Eclipse. 

In order to run the GUI software testing problem instances, it is required to add all *.jar* which are in the *lib* directory of the [jGraphT](https://jgrapht.org/), a Java library of graph theory, into the project. After that, follow the steps below: 

1. Go right to ```HRISERunnerDiscreteReal.java``` in *src/main/java/br/inpe/cocte/labac/hrise/main/*;

2. Change the ```baseSrcDir``` and ```baseDestDir``` according to your platform/computer. The ```baseSrcDir``` is in fact the base directory of the Eclipse project, and the ```baseDestDir``` is the output directory where all the results are stored;

3. To run the GUI problem instances, note that the code-driven ones are identified as strings of the form: "gu1", "gu2", ..., "gu24". The use case-driven problem instances are identified as: "lofx" (LibreOffice), "wupx" (WhatsApp), and "youx" (YouTube). Moreover, they should be addressed in groups and they are case sensitive (strings). This is due to the implementation of HH-RILA. If HH-RILA is not considered, hence it is possible to run each problem instance individually. Uncomment the groups for a different sequence of problem instances. 

4. Run ```HRISERunnerDiscreteReal.java```.

**Important: This software runs under macOS and Linux. In order to run it in Windows, it is required to change the directory delimiter (\\) in Windows in order to properly handle the directories.**

## Underlying Frameworks and Libraries

HRISE-DiscreteReal was developed based on the following frameworks and libraries:

* [jMetal](https://github.com/jMetal/jMetal) - Version 5.6
* [jMetalHyperHeuristicHelper](https://github.com/vinixnan/jMetalHyperHeuristicHelper) - Version 1.0
* [jGraphT](https://jgrapht.org/) - Version 1.3.0

## Version

Current version is 1.01.

## Author

* **Valdivino Alexandre de Santiago J&uacute;nior** - [Personal Web Page](http://www.lac.inpe.br/~valdivino/)

## Licence

This project is licensed under the GNU GENERAL PUBLIC LICENSE, Version 3 (GPLv3) - see the [LICENSE.md](LICENSE) file for details.

## Reference

V. A. Santiago J&uacute;nior, E. Ozcan, J. M. Balera. Many-Objective Test Case Generation for Graphical User Interface
Applications via Search-Based and Model-Based Testing. Subjects: cs.SE (Software Engineering), cs.AI (Artificial Intelligence).




