package main.course.oop.tictactoe.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.course.oop.tictactoe.util.TwoDArray;

public class TwoDArrayDriver {

	public static void main(String[] args) {

	        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.print("Select an int <= 5 : ");
                String size = br.readLine();
                int intVal = Integer.parseInt(size);
                TwoDArray twoDArr = new TwoDArray(intVal,intVal,-1);
                int count = 0;
        		System.out.println(twoDArr.getArrayDetails());


	        	while (count < (intVal * intVal)) {
	        		
	                System.out.print("Enter a row location < "+size+": ");
	        		String input = br.readLine();
	        		int row = Integer.parseInt(input);
	                System.out.println("");
	                
	                //TODO: 1. Check that the input is valid

	                System.out.print("Enter a column location < "+size+": ");
	        		input = br.readLine();
	        		int col = Integer.parseInt(input);
	                System.out.println("");

	        		twoDArr.insertInt(row, col, 8);

	                //TODO: 2. Display the current 2D array in the console

	        		count++;
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	    }	    
}
