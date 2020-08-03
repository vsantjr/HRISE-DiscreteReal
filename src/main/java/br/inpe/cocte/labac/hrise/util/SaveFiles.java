package br.inpe.cocte.labac.hrise.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

public class SaveFiles<S extends Solution<?>> {
	
	public SaveFiles() {
		
	}
	

	public void saveFunVar(String i, String algName, Problem problem, List<S> archive ) {
		
		String dir = "result/" + algName + "/" + problem.getName() + "_" + problem.getNumberOfObjectives();
        new File(dir).mkdirs();
        String funFile = dir + "/FUN" + i + ".tsv";
        String varFile = dir + "/VAR" + i + ".tsv";
        //System.err.println(alg.getClass().getCanonicalName() + " " + funFile);
        new SolutionListOutput(archive)
                .setSeparator("\t")
                .setFunFileOutputContext(new DefaultFileOutputContext(funFile))
                .setVarFileOutputContext(new DefaultFileOutputContext(varFile))
                .print();
	}
	
	
public void saveFunVar(int i, String algName, Problem problem, List<S> archive ) {
		
		String dir = "result/" + algName + "/" + problem.getName() + "_" + problem.getNumberOfObjectives();
        new File(dir).mkdirs();
        String funFile = dir + "/FUN" + i + ".tsv";
        String varFile = dir + "/VAR" + i + ".tsv";
        //System.err.println(alg.getClass().getCanonicalName() + " " + funFile);
        new SolutionListOutput(archive)
                .setSeparator("\t")
                .setFunFileOutputContext(new DefaultFileOutputContext(funFile))
                .setVarFileOutputContext(new DefaultFileOutputContext(varFile))
                .print();
	}

  public void saveFileAnyValues(String fileName, List<S> pop) throws IOException {
	 
	 String dir = "exec";
     new File(dir).mkdirs();
     String popFile = dir + "/" + fileName +".txt";
    
     FileOutputStream f = new FileOutputStream(popFile);
    //ObjectOutputStream out = new ObjectOutputStream(f);
    for (int i = 0; i < pop.size(); i++){
   	  String valueTab = String.valueOf(pop.get(i)) + "\n";
   	  f.write(valueTab.getBytes(), 0, valueTab.length());
    }
    
    f.close();
       
  }
  
  
  public static void saveToCSV(String vers, String prob, String c) throws IOException {
		 
		 String dir = "exec/"+vers;
	     new File(dir).mkdirs();
	     String csvFile = dir + "/" + prob +"-exec.csv";
	    
	     FileOutputStream f = new FileOutputStream(csvFile);
	    //ObjectOutputStream out = new ObjectOutputStream(f);
	    //for (int i = 0; i < pop.size(); i++){
	   	  //String valueTab = String.valueOf(pop.get(i)) + "\n";
	   	  f.write(c.getBytes(), 0, c.length());
	    //}
	    
	    f.close();
  }
  
  
  
  public void savePFKnown(String popFile, List<S> pop) throws IOException {
		 
		 String dir = "pareto_fronts_known";
	     new File(dir).mkdirs();
	     //String popFile = dir + "/" + fileName +".pf";
	    
	     new SolutionListOutput(pop)
            .setSeparator("\t")
            .setFunFileOutputContext(new DefaultFileOutputContext(popFile))
            .print();
            //.setVarFileOutputContext(new DefaultFileOutputContext(varFile))
            //.print();
	     //FileOutputStream f = new FileOutputStream(popFile);
	    //ObjectOutputStream out = new ObjectOutputStream(f);
	    //for (int i = 0; i < pop.size(); i++){
	   	  //String valueTab = String.valueOf(pop.get(i)) + "\n";
	   	  //f.write(valueTab.getBytes(), 0, valueTab.length());
	   // }
	    
	   // f.close();
	       
	  }
  
  public void savePopAllInfo(String fileName, List<S> pop) throws IOException {
		 
		 String dir = "pop_all_info";
	     new File(dir).mkdirs();
	     String popFile = dir + "/" + fileName +".txt";
	    
	     FileOutputStream f = new FileOutputStream(popFile);
	    //ObjectOutputStream out = new ObjectOutputStream(f);
	    for (int i = 0; i < pop.size(); i++){
	   	  String valueTab = String.valueOf(pop.get(i)) + "\n";
	   	  f.write(valueTab.getBytes(), 0, valueTab.length());
	    }
	    
	    f.close();
	       
	  }
  
  public void saveStatGraph(String fileName, String info) throws IOException {
		 
		 String dir = "graph";
	     new File(dir).mkdirs();
	     String graphFile = dir + "/" + fileName +".txt";
	    
	     FileOutputStream f = new FileOutputStream(graphFile);
	    //ObjectOutputStream out = new ObjectOutputStream(f);
	   // for (int i = 0; i < pop.size(); i++){
	   	 // String valueTab = String.valueOf(pop.get(i)) + "\n";
	   	  f.write(info.getBytes(), 0, info.length());
	   // }
	    
	    f.close();
	       
	  }
  
  
  public void savePFTrueKnown(String popFile, List<S> pop, String dir) throws IOException {
		 
		 //String dir = "pareto_fronts_true";
	     new File(dir).mkdirs();
	     //String popFile = dir + "/" + fileName +".pf";
	    
	     new SolutionListOutput(pop)
         .setSeparator("\t")
         .setFunFileOutputContext(new DefaultFileOutputContext(popFile))
         .print();
         //.setVarFileOutputContext(new DefaultFileOutputContext(varFile))
         //.print();
	     //FileOutputStream f = new FileOutputStream(popFile);
	    //ObjectOutputStream out = new ObjectOutputStream(f);
	    //for (int i = 0; i < pop.size(); i++){
	   	  //String valueTab = String.valueOf(pop.get(i)) + "\n";
	   	  //f.write(valueTab.getBytes(), 0, valueTab.length());
	   // }
	    
	   // f.close();
	       
	  }
  
}
