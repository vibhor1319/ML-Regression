package core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
       HashMap<String, HashMap<Integer,Object>> trainColumns = new HashMap<String,HashMap<Integer, Object>>();
       LinkedList<String[]> data= new LinkedList<String[]>(); 
       LinkedList<String[]> dataTrain = new LinkedList<String[]>();
       LinkedList<String[]> dataTest = new LinkedList<String[]>();
       HashMap<String, MatrixDataString> testTrain = new HashMap<String, MatrixDataString>();
       String[][] csvMatrixTrain =  null, csvMatrixTest = null;
//       HashMap<Integer, String> name = new HashMap<Integer, String>();
//       HashMap<Integer, String> review = new HashMap<Integer,String>();
//       HashMap<Integer, Integer> rating = new HashMap<Integer, Integer>();
//       HashMap<Integer, String> reviewClean = new HashMap<Integer, String>();
//       HashMap<Integer, Integer> sentiment = new HashMap<Integer, Integer>();
//       HashMap<Integer, HashMap<String, Integer>> wordCount = new HashMap<Integer, HashMap<String, Integer>>();
//       
//       HashMap<Integer, String> nameClone = new HashMap<Integer, String>();;
//       HashMap<Integer, String> reviewClone = new HashMap<Integer,String>();;
//       HashMap<Integer, Integer> ratingClone = new HashMap<Integer, Integer>();;
//       HashMap<Integer, String> reviewCleanClone = new HashMap<Integer, String>();;
//       HashMap<Integer, Integer> sentimentClone = new HashMap<Integer, Integer>();;
//       HashMap<Integer, HashMap<String, Integer>> wordCountClone = new HashMap<Integer, HashMap<String, Integer>>();
//       
       
       try {
    	   //BufferedReader br = new BufferedReader("/home/ankita/coursera/ml/classification/week1/module-2-assignment-train-idx.json");  
    	   BufferedReader br1 = new BufferedReader(new FileReader(path));  
    	   BufferedReader br2 = new BufferedReader(new FileReader(path2));  
    	   
    			
    	  //Reader reader = new InputStreamReader(MatrixFormulation.class.getResourceAsStream("/home/ankita/coursera/ml/classification/week1/module-2-assignment-train-idx.json"), "UTF-8");
               Gson gson = new GsonBuilder().create();
               HashSet<Integer> train = gson.fromJson(br1, HashSet.class);
               HashSet<Integer> test= gson.fromJson(br2,  HashSet.class);
    		   
    		   //System.out.println(Arrays.toString(p));
               
           int i = 0;
           br = new BufferedReader(new FileReader("/home/ankita/coursera/ml/classification/week1/amazon_baby.csv"));
           String columnNames = br.readLine();
           String [] headings = columnNames.split(cvsSplitBy);
           String[] newHeadings = new String[headings.length +3];
           System.arraycopy(headings, 0, newHeadings, 0, headings.length);
           //newHeadings = Arrays.copyOfRange(headings, 0, headings.length);
           newHeadings[headings.length] = "review_clean";
           newHeadings[headings.length+1] = "sentiment";
           newHeadings[headings.length+2] = "wordCount";
           matrixTest.setColumnHeaders(newHeadings);
           matrixTrain.setColumnHeaders(newHeadings);
           //headings = Arrays.copyOfRange(headings, 2, headings.length);
           while ((line = br.readLine()) != null) {

               // use comma as separator
        	   
               String[] features = new String[newHeadings.length];
               System.arraycopy(line.split(cvsSplitBy, -1), 0, features, 0,headings.length );
               //name.put(i, features[0]);
               if(features[1].trim() == "" )
               {
            	   features[1]= "N/A";
            	   
            	   
            	   //review.put(i, "N/A");
               }
//               else
//               {
//            	   review.put(i, features[1]);
//               }
//              
              // rating.put(i, Integer.parseInt(features[2]));
               features[headings.length] = remove_punctuation(features[1]);
               int sentimentValue = 0;
               //reviewClean.put(i, cleanReview);
               if(Double.parseDouble(features[2]) >=4)
        	   {
            	   sentimentValue = 1;
        		   
        	   }
        	   else
        	   {
            	   sentimentValue = -1;
        	   }
               
               HashMap<String, Integer> wordCountMap = wordCount(features[headings.length]);
               
               if(train.contains(i))
               {
            	   matrixTrain.name.put(i, features[0]);
            	   matrixTrain.review.put(i, features[1]);
            	   matrixTrain.rating.put(i, Integer.parseInt(features[2]));
            	   matrixTrain.reviewClean.put(i, features[headings.length]);
            	   matrixTrain.sentiment.put(i, sentimentValue);
            	   matrixTrain.wordCount.put(i, wordCountMap);	
            	   
            	   
               }
               else{
            	   matrixTest.name.put(i, features[0]);
            	   matrixTest.review.put(i, features[1]);
            	   matrixTest.rating.put(i, Integer.parseInt(features[2]));
            	   matrixTest.reviewClean.put(i, features[headings.length]);
            	   matrixTest.sentiment.put(i, sentimentValue);
            	   matrixTest.wordCount.put(i, wordCountMap);	
            	   
               }
               
               
              
               i++;
           }
           
            
//           reviewClone.putAll(review);// = review.p
//           nameClone.putAll(name);
//           ratingClone.putAll(rating);
//           reviewCleanClone.putAll(reviewClean);
//           sentimentClone.putAll(sentiment);
//           wordCountClone.putAll(wordCount);
//           
//           review.keySet().retainAll(train);
//           name.keySet().retainAll(train);
//           rating.keySet().retainAll(train);
//           reviewClean.keySet().retainAll(train);
//           sentiment.keySet().retainAll(train);
//           wordCount.keySet().retainAll(train);
//           
//           reviewClone.keySet().retainAll(test);
//           nameClone.keySet().retainAll(test);
//           ratingClone.keySet().retainAll(test);
//           reviewCleanClone.keySet().retainAll(test);
//           sentimentClone.keySet().retainAll(test);
//           wordCountClone.keySet().retainAll(test);
//          
//           
//           matrixTest.setColumnHeaders(newHeadings);
//           matrixTest.setName(nameClone);
//           matrixTest.setRating(ratingClone);
//           matrixTest.setReview(reviewClone);
//           matrixTest.setReviewClean(reviewCleanClone);
//           matrixTest.setSentiment(sentimentClone);
//           matrixTest.setWordCount(wordCountClone);
           
           
//           matrixTest.setColumnHeaders(newHeadings);
//           matrixTrain.setName(name);
//           matrixTrain.setRating(rating);
//           matrixTrain.setReview(review);
//           matrixTrain.setReviewClean(reviewClean);
//           matrixTrain.setSentiment(sentiment);
//           matrixTrain.setWordCount(wordCount);
//           
           
          // trainColumns.put("review", review);
           
//          for (int j = 0; j < train.length; j++) {
//        	  //System.out.println(j);
//			dataTrain.add(data.get(train[j]));
//		}
//          
//          for (int j = 0; j < test.length; j++) {
//        	  //System.out.println(j);
//			dataTest.add(data.get(test[j]));
//		} 
          //csvMatrixTrain = dataTrain.toArray(new String[dataTrain.size()][]);
         // csvMatrixTest = dataTest.toArray(new String[dataTest.size()][]);
          
           
           
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
	
	public HashMap<String, Integer> wordCount(String cleanReview)
	{
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		
		String tokenized[] = cleanReview.split(" ");
		
		for (String string : tokenized) {
			if(string.trim() != "")
			{
				if(count.containsKey(string))
				{
					count.put(string, count.get(string) + 1);
				}
				else{
					count.put(string, 1);
				}
			}
			
		}
		
		return count;
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
