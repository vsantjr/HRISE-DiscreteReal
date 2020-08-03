package HF_utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class separateStatsTestFiles {

	
	public separateStatsTestFiles(){
		super();
	}
	public double[][] readColumnValuesFromFile(String path){
		
		try{
			// Open the file
		      FileInputStream fis   = new FileInputStream(path)     ;
		      InputStreamReader isr = new InputStreamReader(fis)    ;
		      BufferedReader br      = new BufferedReader(isr)      ;
		      
		      List<double []> list = new ArrayList<double []>();
		      
		      int columns = 0;
		      String aux = br.readLine();
		      while (aux!= null) {
		        StringTokenizer st = new StringTokenizer(aux);
		        int i = 0;
		        columns = st.countTokens();
		        double [] vector = new double[st.countTokens()];
		        while (st.hasMoreTokens()) {
		          double value = (new Double(st.nextToken())).doubleValue();
		          vector[i] = value;
		          i++;
		        }
		        list.add(vector);
		        aux = br.readLine();
		      }
		            
		      br.close();
		      
		      double [][] result = new double[list.size()][columns];
		      for (int i = 0; i < list.size(); i++) {
		    	  result[i] = list.get(i);
		      }
		     
		      return result;
		      
			
		}catch (Exception e) {
		      System.out.println("InputFacilities crashed reading for file: "+path);
		      e.printStackTrace();
		    }
		return null;
		
	}
	
	public String[] readColumnNamesFromFile(String path){
		try{
			// Open the file
		      FileInputStream fis   = new FileInputStream(path)     ;
		      InputStreamReader isr = new InputStreamReader(fis)    ;
		      BufferedReader br      = new BufferedReader(isr)      ;
		      
		      List<String> list = new ArrayList<String>();
		 
		      String aux = br.readLine();
		      while (aux!= null) {
		        StringTokenizer st = new StringTokenizer(aux);
		    
		        list.add(st.nextToken());
		        aux = br.readLine();
		      }
		            
		      br.close();
		      
		      String[] result = new String[list.size()];
		      for (int i = 0; i < list.size(); i++) {
		    	  result[i] = list.get(i);
		      }
		     
		      return result;
		      
			
		}catch (Exception e) {
		      System.out.println("InputFacilities crashed reading for file: "+path);
		      e.printStackTrace();
		    }
		return null;
		
	}
	
	
	void printSeparateStatsTestFiles(double[][] columnValues, String[] columnNames, String path, int decisionPoints){
		
		for(int k = 0; k < columnNames.length - 1; k++){
			
			for(int l = k+1; l < columnNames.length; l++){
				String filename = path + columnNames[k] + "VS" + columnNames[l]+"_"+decisionPoints+"DP.txt";
				BufferedWriter outputWriter = null;
				try {
					outputWriter = new BufferedWriter(new FileWriter(filename));
						
						for(int j = 0; j < columnValues.length;j++){
							outputWriter.write(columnNames[k] + " " + columnValues[j][k]);
							outputWriter.newLine();	
						}	
						
						for(int j = 0; j < columnValues.length;j++){
							outputWriter.write(columnNames[l] + " " + columnValues[j][l]);
							outputWriter.newLine();	
						}						
					
					  outputWriter.close();  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("OnputFacilities crashed writing for file: "+filename);
					e.printStackTrace();
				}
			}			
		}		
	}
	
	void writeSASfileList(String[] columnNames, String path, int decisionPoints, String sasDataPath, String sasOutputPath){
		
		String fileName = path;
		BufferedWriter outputWriter = null;
		try {
			outputWriter = new BufferedWriter(new FileWriter(fileName));
			
		
		for(int k = 0; k < columnNames.length - 1; k++){
			
			for(int l = k+1; l < columnNames.length; l++){
					String name = sasDataPath + columnNames[k] + "VS" + columnNames[l]+"_"+decisionPoints+"DP.txt";	
					outputWriter.write(name);
					outputWriter.newLine();
					
					String outputName = sasOutputPath + columnNames[k] + "VS" + columnNames[l]+"_"+decisionPoints+"DP.pdf";	
					outputWriter.write(outputName);
					outputWriter.newLine();
					outputWriter.newLine();
				}		
			}
		  outputWriter.close();  
		}catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("OnputFacilities crashed writing for file: "+fileName);
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		separateStatsTestFiles sa = new separateStatsTestFiles();
		String columnValuesFile = "utilityData/inputData/columnValues.txt";
		String columnnamesFile = "utilityData/inputData/columnNames.txt";
		int decisionPoints = 10;
		double[][] columnValues = sa.readColumnValuesFromFile(columnValuesFile);
		String[] columnNames = sa.readColumnNamesFromFile(columnnamesFile);
		
		String outputFolder = "utilityData/outputResults/";
		
		sa.printSeparateStatsTestFiles(columnValues, columnNames, outputFolder,decisionPoints);
		
		String sasDataPath = "/folders/myfolders/hypervolumeStatisticsTest/10decisionPoints/dataFiles/";
		String sasOutputPath = "/folders/myfolders/hypervolumeStatisticsTest/10decisionPoints/outputFiles/";
		
		String sasDataListFile = "utilityData/sasFileNameList.txt";
		sa.writeSASfileList(columnNames, sasDataListFile, decisionPoints, sasDataPath, sasOutputPath);
	}

}
