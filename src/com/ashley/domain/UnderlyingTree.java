package com.ashley.domain;

import java.util.Arrays;

public class UnderlyingTree {

	private double[][] underlyingTree;
	
	private int n;
	private int maturity;
	private double PoC_0;
	private double sigma;
	
	
	public UnderlyingTree(int n, int t, double poC_0, double sigma) {
		super();
		this.underlyingTree = new double[n+1][n+1];
		this.n = n;
		this.maturity = t;
		PoC_0 = poC_0;
		this.sigma = sigma;
		initTree();
	}
	
	public double[][] getTree() {
		return underlyingTree;
	}
	
	public int getTsize() {
		return underlyingTree.length;
	}
	
	public double[] getColumn(int index) {
		return underlyingTree[index];
	} 

	private void initTree() {
		double dt = maturity/n;
		double up = Math.exp(sigma * Math.sqrt(dt));
		double down = 1/up; 
		
		for (int t = 0; t < n+1; t++) {
			for (int i = 0; i < t+1; i++) {
				underlyingTree[t][i] = PoC_0 * (Math.pow(down, i)) * (Math.pow(up, (t-i)));
				
			}
		}
	}
	
	public double getUnderlyingPrice(int i, int t) {
		return underlyingTree [i] [t];
	}
	
}


