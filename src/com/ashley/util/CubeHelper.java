package com.ashley.util;

import java.util.Arrays;

import com.ashley.domain.UnderlyingTree;
import com.ashley.domain.UserParameters;

public class CubeHelper {

	public CubeHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public static double calculateUnitProfit(UserParameters userParams, UnderlyingTree tree, int i, int t ) {
		
		//double unitProfit = PoP_0 - CoO - AoC * UnderlyingTree[i] [t];
		return 2.4;
	}
	
	public static void print2DArray(double[][] array)  { 
	    for (double[] row : array) {
	    		System.out.println(Arrays.toString(row)); 
	    }
    } 
	
	public static void nicePrint2DArray(String arrayName, double[][] array)  {
		System.out.println("Display Array " + arrayName);
	    for (double[] row : array) {
	    		int[] intArray = new int[row.length];
	    		for (int i=0; i<intArray.length; ++i) {
	    			intArray[i] = (int) row[i];
	    		}
	    		System.out.println(Arrays.toString(intArray)); 
	    }
    }

}
