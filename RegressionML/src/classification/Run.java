package classification;

import java.util.HashMap;

import core.MatrixData;
import core.MatrixDataString;
import core.MatrixFormulation;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runClasification();
	}

	public static void runClasification() {
		MatrixFormulation mfTrainTest = new MatrixFormulation();
		String pathTest = "/home/ankita/coursera/ml/classification/week1/module-2-assignment-test-idx.json";
		String pathTrain = "/home/ankita/coursera/ml/classification/week1/module-2-assignment-train-idx.json";
		HashMap<String,MatrixDataString> trainTest = mfTrainTest
				.readCSVClassification("","");
		MatrixDataString dataTrain = trainTest.get("train");
		//MatrixDataString dataTest = trainTest.get("test");
		
		int perfect_count = 0;
		for (HashMap<String,Integer> iterable_element : dataTrain.getWordCount().values()) {
			if(iterable_element.containsKey("perfect"))
			{
			perfect_count++;	
			}
		}
		
		
		System.out.println(perfect_count);
		
		
		
		
		
	}
}
