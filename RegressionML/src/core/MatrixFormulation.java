package core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class MatrixFormulation {

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
	
	public double[] doubleTypeArray(String a[]){
		double[] mark = new double[a.length];
		for(int i = 0;i <a.length ;i++)
		{
			mark[i] = Double.parseDouble(a[i].replaceAll("^\"|\"$", ""));
			
		}
		
		return mark;
	}
	
	
}
