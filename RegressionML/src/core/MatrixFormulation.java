package core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
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
	
	public MatrixData readCSVClassification(String path)
	{
		String csvFile = path;
		 MatrixData obj = new MatrixData();
       BufferedReader br = null;
       String line = "";
       String cvsSplitBy = ",";
       //String[][] data = new String[][];
       LinkedList<String[]> data= new LinkedList<String[]>(); 
       double[][] csvMatrix =  null;
       try {

    	   try(Reader reader = new InputStreamReader(MatrixFormulation.class.getResourceAsStream("/Server1.json"), "UTF-8")){
               Gson gson = new GsonBuilder().create();
//               Person p = gson.fromJson(reader, Person.class);
              
    		   
    		   //System.out.println(p);
               
           }
           br = new BufferedReader(new FileReader(csvFile));
           String columnNames = br.readLine();
           String [] headings = columnNames.split(cvsSplitBy);
           String[] newHeadings = new String[headings.length +2];
           newHeadings = Arrays.copyOfRange(headings, 0, headings.length);
           newHeadings[headings.length] = "review_clean";
           newHeadings[headings.length+1] = "sentiment";
           //headings = Arrays.copyOfRange(headings, 2, headings.length);
           while ((line = br.readLine()) != null) {

               // use comma as separator
               String[] features = new String[newHeadings.length];
               features = line.split(cvsSplitBy);
               if(Double.parseDouble(features[2]) != 3)
               {
            	   features[features.length] = remove_punctuation(features[1]);
            	   if(Double.parseDouble(features[2]) >=4)
            	   {
            		   features[features.length+1] = "1";
            	   }
            	   else
            	   {
            		   features[features.length+1] = "-1";
            		   
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
	
	public double[] doubleTypeArray(String a[]){
		double[] mark = new double[a.length];
		for(int i = 0;i <a.length ;i++)
		{
			mark[i] = Double.parseDouble(a[i].replaceAll("^\"|\"$", ""));
			
		}
		
		return mark;
	}
	
	
}
