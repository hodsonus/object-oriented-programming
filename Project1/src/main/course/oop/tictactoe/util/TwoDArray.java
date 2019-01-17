package main.course.oop.tictactoe.util;
import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Map;
import java.lang.IllegalArgumentException;

/**
 * You must implement the following methods to accept the expected 
 * parameters and return accurate results.
 * You are allowed and expected to add any class attributes and methods 
 * needed to complete this assignment. 
 * 
 *
 */
public class TwoDArray {
	
	private int[][] arr;
	private int defaultVal;
	
	public TwoDArray(int rows, int cols, int defaultVal){
		this.arr = new int[rows][cols];
		this.defaultVal = defaultVal;
		initArray(defaultVal);
	}
	
	public void initArray(int defaultVal) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = defaultVal;
			}
		}
	}
	
	public String insertInt(int row, int col, int val) {
		if (row < 0 || row >= arr.length || col < 0 || col >= arr[row].length) {
			throw new IllegalArgumentException();
		}
		
		String returnString = "";
		
		// 1. The location [row][col] is still set to the default value
		// 		-return "Success! (val) was inserted.
		if (arr[row][col] == this.defaultVal) {
			 arr[row][col] = val;
			 returnString = "Success! " + val + " was inserted.";
		 }
		// 2. The location [row][col] is no longer the default value
		// 		-return "Failure: (row), (col) is not empty.
		 else if (arr[row][col] != this.defaultVal) {
			 returnString = "Failure: " + row + ", "+ col+ " is not empty.";
		 }
		// 3. val is not the default value; 
		// 		-return "Failure: (val) is not allowed."
		else if (val != this.defaultVal) {
			returnString = "Failure: " + val + " is not allowed.";
		}
		 
		 return returnString;
	}
	
	public int getInt(int row, int col) {
		if (row < 0 || row >= arr.length || col < 0 || col >= arr[row].length) {
			throw new IllegalArgumentException();
		}
		return arr[row][col];
	}
	
	public String getArrayDisplay() {
		StringBuilder representation = new StringBuilder();
		StringBuilder line;
		for (int i = 0; i < arr.length; i++) {
			line = new StringBuilder();
			for (int j = 0; j < arr[i].length; j++) {
				line.append(arr[i][j] + " ");
			}
			//remove the last space from the line
			line.delete(line.length()-1,line.length());
			representation.append(line + System.lineSeparator());
		}
		//remove the last newline from the representation of the array
		int seperatorLength = System.lineSeparator().length();
		representation.delete(representation.length()-seperatorLength,representation.length());
		return representation.toString();
	}
	
	public String getArrayDetails() {
		StringBuilder details = new StringBuilder();
		details.append(arr.length + " rows" + System.lineSeparator());
		details.append(arr[0].length + " columns" + System.lineSeparator());
		
		Map<Integer, Integer> values = new HashMap<Integer, Integer>(); 
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				int count = values.containsKey(arr[i][j]) ? values.get(arr[i][j]) : 0;
				values.put(arr[i][j], count + 1);
			}
		}
		
		for (Map.Entry<Integer, Integer> entry : values.entrySet()) { 
			details.append("value:" + entry.getKey() + " count:" + entry.getValue() + System.lineSeparator());
		}
		
		int seperatorLength = System.lineSeparator().length();
		details.delete(details.length()-seperatorLength,details.length());
		return details.toString();
	}
}
