package com.ashley.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ashley.util.CubeHelper;

public class Cube {
	
	private int tSize,iSize,oSize; //Cube size x, y, z
	private UnderlyingTree assetPrices;
	private static UserParameters userParams;
	private Slice[] cube;
	private int optionNum;
	private double dt;
	private double up;
	private double down; 
	private double rf;
	private double Rf;
	private double p;
	
	public Cube(UserParameters userParams, UnderlyingTree assetPrices) {
		super();
		this.assetPrices = assetPrices;
		this.userParams = userParams;
		tSize = userParams.getN()+1;
		iSize = tSize;
		optionNum = userParams.getRealOptions().length - 1;
		oSize = 1 + 2 * optionNum;
		this.dt = userParams.getT()/userParams.getN();
		this.up = Math.exp(userParams.getSigma() * Math.sqrt(dt));
		this.down = 1/up; 
		this.rf = userParams.getRf();
		this.Rf = Math.exp(rf * dt);
		this.p = (Rf - down)/ (up - down);
		cube = new Slice[tSize];
		initCube();
	}

	
	private void initCube() {
		for(int i=0; i< tSize; i++) {
			Slice slice = new Slice(oSize,iSize);
			cube[i] = slice;
		}
	}
	
	
	private Slice getSlice(int sliceIndex) {
		return cube[sliceIndex];
		
	}


	public void startProcessing() {
		populateLastSlice();
		processSlices();
		populateFirstSlice();
	}
	
	private void populateLastSlice() {	
		// We will populate the last slice column by column 
		double[][] lastSlice = cube[tSize-1].getSlice();
		
		double[] lastColumn = assetPrices.getColumn(assetPrices.getTree().length-1); 
		for(int i = 0; i < lastColumn.length; i++) {
			//System.out.println("assetPrices - lastColumn - i = " + i + " - value = " + lastColumn[i]);
			for (int o = 0; o < oSize; o++) {
				lastSlice[o][i] = lastColumn[i];
				double lastSliceProfit = computeProfit(o,i, lastSlice);
				lastSlice[o][i] = lastSliceProfit;
				// System.out.println("Last Layer Profit " + lastLayerProfit);
			}
		}
		
		CubeHelper.nicePrint2DArray("lastSlice", lastSlice);		
		
	}
	
	public double computeProfit (int o, int i, double [] [] slice) {
		double unitProfit = userParams.getPoP_0() - userParams.getCoO() - slice[o][i] * userParams.getAoC();
		//int optionNum = userParams.getOptions();
		double totalProfit = 0;
		
		if (o <= optionNum) {
			totalProfit = unitProfit * userParams.getRealOptions()[o].getSalesLevel() + 
				userParams.getRealOptions()[o].getGainOrLoss();
		}
		else {
			totalProfit = unitProfit * userParams.getRealOptions()[o - optionNum].getSalesLevel();
		}
		return totalProfit;
		
	}
	private void populateFirstSlice() { // The first slice cannot have the options with assumptions of previous actions, so it's the only slice where oSize = 1 + optionNum;
		
		double[][] prevSlice = cube[1].getSlice();
		double PoC_0 = userParams.getPoC_0();
		double[] firstRow = new double [optionNum+1];
			for (int o = 0; o < optionNum + 1; o++) {
				double sliceProfit = computeProfit (o, 0, prevSlice);
				double sliceDCF = computeDCF (o, 0, prevSlice);
				prevSlice [o][0] = sliceProfit + sliceDCF;
				firstRow[o] = prevSlice [o][0];
			}
		double result = Arrays.stream(firstRow).max().getAsDouble();
		System.out.println("The result is " + result);
	}
		
	private void populateSlice(int tIndex) {
		double[][] prevSlice = cube[tIndex+1].getSlice(); // index+1 is giving us the previous slice that has been populated
		double[] column = assetPrices.getColumn(tIndex); 
		for (int i = 0; i < column.length - tIndex ; i++) {
			for (int o = 0; o < oSize; o++) {
				double sliceProfit = computeProfit (o, i, prevSlice); // computeProfit for different options
				double sliceDCF = computeDCF (o, i, prevSlice);
				prevSlice [o][i] = sliceProfit + sliceDCF;
			}
		}	
		CubeHelper.nicePrint2DArray("The slice at o now is ", prevSlice);
	}
	
	public double computeDCF (int o, int i, double [] [] slice) {
		double DCF;
		if (o == 0) { // if we stay as current sales level, we need to choose the best options of the future cash flows in both up and down situations
			double[] rowUp = new double [optionNum+1];
			double[] rowDown = new double [optionNum+1];
			for (int m = 0; m <= optionNum; m++) { 
				rowUp[m]= slice[m][i]; // for layer i, the first cash flow from the next period till the optionNum'th cash flow will be compared for the best option
				rowDown[m]= slice[m][i+1]; // same thing for layer i + 1, which is the down case
			}			
			
			double maxUp = Arrays.stream(rowUp).max().getAsDouble();
			double maxDown = Arrays.stream(rowDown).max().getAsDouble();
			System.out.println(" maxUp is " + maxUp + " and maxDown is " + maxDown);
				
			DCF = Math.pow(Rf, -1) * (p * maxUp + (1-p) * maxDown); // Depending on the probabilities of Up or Down cases, we discount back the cash flow 
			System.out.println(" DCF if stay current is " + DCF);
			
		}
		else if (o <= optionNum){ // these options are assuming no previous actions
			
			DCF = Math.pow(Rf, -1) * (p * slice [o + optionNum][i] + (1-p) * slice [o + optionNum][i+1]);
			System.out.println(" DCF assuming no prior actions is " + DCF + " when option is " + o); 
			
		}else { // these options are assuming already taken actions 
			
			DCF = Math.pow(Rf, -1) * (p * slice [o][i] + (1-p) * slice [o] [i+1]);
			System.out.println(" DCF assuming prior actions is " + DCF + " when option is " + o); 
			
		}
		return DCF;
	}
	
	//recursive
	private void processSlices() {
		for (int t=tSize-2; t > 0; t--) {
			populateSlice(t);
		}
	}

}
