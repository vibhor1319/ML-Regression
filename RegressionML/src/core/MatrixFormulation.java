package core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MatrixFormulation {

	public String remove_punctuation(String text)
	{
		String cleanText = text.replaceAll("[':,();\".!?\\-]", "");
		
		return cleanText;
	}
	
	public MatrixData readCSV(String path)
	{
		String csvFile = path;
		 MatrixData obj = new MatrixData();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        //String[][] data = new String[][];
        LinkedList<double[]> data= new LinkedList<double[]>(); 
        double[][] csvMatrix =  null;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            String columnNames = br.readLine();
            String [] headings = columnNames.split(cvsSplitBy); 
            headings = Arrays.copyOfRange(headings, 2, headings.length);
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] features = line.split(cvsSplitBy);
                String[] feas = Arrays.copyOfRange(features, 2, features.length);
                //data.add(features);
                double[] feature_double = doubleTypeArray(feas);
                //System.out.println(feature_double);
                data.add(feature_double);
                //System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }
             csvMatrix = data.toArray(new double[data.size()][]);
            
             obj.setColumnHeaders(headings);
             obj.setMatrix(csvMatrix);
             

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return obj;
	}
	
	public HashMap<String, MatrixDataString> readCSVClassification(String path,String path2)
	{
		
		//home/ankita/coursera/ml/classification/week1
		
		String csvFile = path;
		 MatrixDataString matrixTest = new MatrixDataString();
		 MatrixDataString matrixTrain = new MatrixDataString();
       BufferedReader br = null;
       String line = "";
       String cvsSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
       //String[][] data = new String[][];
       LinkedList<String[]> data= new LinkedList<String[]>(); 
       LinkedList<String[]> dataTrain = new LinkedList<String[]>();
       LinkedList<String[]> dataTest = new LinkedList<String[]>();
       HashMap<String, MatrixDataString> testTrain = new HashMap<String, MatrixDataString>();
       String[][] csvMatrixTrain =  null, csvMatrixTest = null;
       try {
    	   //BufferedReader br = new BufferedReader("/home/ankita/coursera/ml/classification/week1/module-2-assignment-train-idx.json");  
    	   BufferedReader br1 = new BufferedReader(new FileReader(path));  
    	   BufferedReader br2 = new BufferedReader(new FileReader(path2));  
    	   
    			
    	  //Reader reader = new InputStreamReader(MatrixFormulation.class.getResourceAsStream("/home/ankita/coursera/ml/classification/week1/module-2-assignment-train-idx.json"), "UTF-8");
               Gson gson = new GsonBuilder().create();
               int train[] = gson.fromJson(br1, int[].class);
               int test[] = gson.fromJson(br2, int[].class);
    		   
    		   //System.out.println(Arrays.toString(p));
               
           int i = 0;
           br = new BufferedReader(new FileReader("/home/ankita/coursera/ml/classification/week1/amazon_baby.csv"));
           String columnNames = br.readLine();
           String [] headings = columnNames.split(cvsSplitBy);
           String[] newHeadings = new String[headings.length +2];
           System.arraycopy(headings, 0, newHeadings, 0, headings.length);
           //newHeadings = Arrays.copyOfRange(headings, 0, headings.length);
           newHeadings[headings.length] = "review_clean";
           newHeadings[headings.length+1] = "sentiment";
           //headings = Arrays.copyOfRange(headings, 2, headings.length);
           while ((line = br.readLine()) != null) {

               // use comma as separator
               String[] features = new String[newHeadings.length];
               
               System.arraycopy(line.split(cvsSplitBy, -1), 0, features, 0,headings.length );;
               
               //System.out.println(i);
               if(Double.parseDouble(features[2]) != 3)
               {
            	   features[headings.length] = remove_punctuation(features[1]);
            	   if(Double.parseDouble(features[2]) >=4)
            	   {
            		   features[headings.length+1] = "1";
            	   }
            	   else
            	   {
            		   features[headings.length+1] = "-1";
            		   
            	   }
            	   
            	   if(features[1].trim() == "")
            	   {
            		   features[1] = "N/A";
            	   }
            	   
            	   
                   //data.add(features);
                   //double[] feature_double = doubleTypeArray(feas);
                   //System.out.println(feature_double);
                   
                   data.add(features);
               }
             
               //System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
               i++;
           }
          for (int j = 0; j < train.length; j++) {
        	  //System.out.println(j);
			dataTrain.add(data.get(train[j]));
		}
          
          for (int j = 0; j < test.length; j++) {
        	  //System.out.println(j);
			dataTest.add(data.get(test[j]));
		} 
          csvMatrixTrain = dataTrain.toArray(new String[dataTrain.size()][]);
          csvMatrixTest = dataTest.toArray(new String[dataTest.size()][]);
          
            matrixTest.setColumnHeaders(newHeadings);
            matrixTest.setMatrix(csvMatrixTest);
            
            matrixTrain.setColumnHeaders(newHeadings);
            matrixTrain.setMatrix(csvMatrixTrain);
           
            testTrain.put("train", matrixTrain);
            testTrain.put("test", matrixTest);

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (br != null) {
               try {
                   br.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       
       return testTrain;

		
		
	}
	
	public double[] doubleTypeArray(String a[]){
		double[] mark = new double[a.length];
		for(int i = 0;i <a.length ;i++)
		{
			mark[i] = Double.parseDouble(a[i].replaceAll("^\"|\"$", ""));
			
		}
		
		return mark;
	}
	
	
}
