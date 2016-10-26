package core;

import java.util.HashMap;

public class MatrixDataString {


public String[] getColumnHeaders() {
	return columnHeaders;
}
public void setColumnHeaders(String[] columnHeaders) {
	this.columnHeaders = columnHeaders;
}
	
HashMap<Integer, String> name = new HashMap<Integer, String>();
public HashMap<Integer, String> getName() {
	return name;
}
public void setName(HashMap<Integer, String> name) {
	this.name = name;
}
public HashMap<Integer, String> getReview() {
	return review;
}
public void setReview(HashMap<Integer, String> review) {
	this.review = review;
}
public HashMap<Integer, Integer> getRating() {
	return rating;
}
public void setRating(HashMap<Integer, Integer> rating) {
	this.rating = rating;
}
public HashMap<Integer, String> getReviewClean() {
	return reviewClean;
}
public void setReviewClean(HashMap<Integer, String> reviewClean) {
	this.reviewClean = reviewClean;
}
public HashMap<Integer, Integer> getSentiment() {
	return sentiment;
}
public void setSentiment(HashMap<Integer, Integer> sentiment) {
	this.sentiment = sentiment;
}
public HashMap<Integer, HashMap<String, Integer>> getWordCount() {
	return wordCount;
}
public void setWordCount(HashMap<Integer, HashMap<String, Integer>> wordCount) {
	this.wordCount = wordCount;
}

HashMap<Integer, String> review = new HashMap<Integer,String>();
HashMap<Integer, Integer> rating = new HashMap<Integer, Integer>();
HashMap<Integer, String> reviewClean = new HashMap<Integer, String>();
HashMap<Integer, Integer> sentiment = new HashMap<Integer, Integer>();
HashMap<Integer, HashMap<String, Integer>> wordCount = new HashMap<Integer, HashMap<String, Integer>>();
	String columnHeaders[];
}
